package durry.philipp;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Philipp Durry
 * 
 * Java implementation of the AES cipher Rijndael with a 128-bit block size and a 128-bit cipher key size.
 * As described in https://csrc.nist.gov/csrc/media/publications/fips/197/final/documents/fips-197.pdf
 */
public class AES {
	
	private static final int N_ROUNDS = 10;		// Number of rounds
	private static final int N_WORDS = 44;		// Number of words in the key schedule array
	private static final int KEY_SIZE = 128;	// 128-bit cipher key
	
	
	public static String generateCipherKey() {
		String hexCipherKey = "";
		
		SecureRandom random = new SecureRandom();
	    byte[] randomBytes = new byte[KEY_SIZE / 8];
	    random.nextBytes(randomBytes);
		
		for (int i = 0; i < KEY_SIZE / 8; i++) {
			hexCipherKey += String.format("%2s", Integer.toHexString(randomBytes[i] & 0xFF)).replace(' ', '0');
		}
		
		return hexCipherKey;
	}
	
	
	// encrypts multiple blocks with simple Electronic Code Book Mode
	public static String encrypt(String plaintext, String cipherKey) {
		String ciphertext = "";
		
		byte[][] wordArray = keyExpansion(cipherKey);
		
		String plaintextHex = "";
		for (int i = 0; i < plaintext.length(); i++) {
			plaintextHex += String.format("%2s", Integer.toHexString(plaintext.charAt(i) & 0xFF)).replace(' ', '0');
		}
		
		int charCount = 0;
		for (int i = 0; i < plaintext.length(); i++) {
			charCount += 1;
		}
		// one 128-bit block can contain 16 ASCII characters
		int n_blocks = charCount / 16;
		// Padding if last block is too short
		int n_paddingChars = 16 - (charCount % 16);
		
		if (n_paddingChars != 16) {
			n_blocks += 1;
			for (int i = 0; i < n_paddingChars; i++) {
				plaintextHex += "00";
			}
		}
		
		// encrypt all blocks
		int indexBlockStart = 0;
		for (int i = 0; i < n_blocks; i++) {
			byte[][] state = hexStringToState(plaintextHex.substring(indexBlockStart, indexBlockStart + 32));
			state = encryptBlock(state, wordArray);
			ciphertext += stateToHexString(state);
			indexBlockStart += 32;
		}

		return ciphertext;
	}
	
	
	public static String decrypt(String ciphertext, String cipherKey) {
		String plaintext = "";
		
		byte[][] wordArray = keyExpansion(cipherKey);
		
		int hexCount = 0;
		for (int i = 0; i < ciphertext.length(); i++) {
			hexCount += 1;
		}
		int n_blocks = hexCount / 32;
		
		// decrypt all blocks
		int indexBlockStart = 0;
		for (int i = 0; i < n_blocks; i++) {
			byte[][] state = hexStringToState(ciphertext.substring(indexBlockStart, indexBlockStart + 32));
			state = decryptBlock(state, wordArray);
			String plaintextHex = stateToHexString(state);
			
			for (int j = 0; j < plaintextHex.length(); j += 2) {
				plaintext += (char) Integer.parseInt(plaintextHex.substring(j, j + 2), 16);
			}
			indexBlockStart += 32;
		}
		
		// remove padding bits
		if (plaintext.contains(String.valueOf((char) 0x00))) {
			int indexPadding = plaintext.indexOf((char) 0x00);
			plaintext = plaintext.substring(0, indexPadding);
		}
		
		return plaintext;
	}


	private static byte[][] encryptBlock(byte[][] state, byte[][] wordArray) {
		// initial round
		int round = 0;
		state = addRoundKey(state, round, wordArray);
		
		// 9 main rounds
		for (round = 1; round < N_ROUNDS; round++) {
			state = subBytes(state);
			state = shiftRows(state);
			state = mixColumns(state);
			state = addRoundKey(state, round, wordArray);
		}
		
		// final round
		state = subBytes(state);
		state = shiftRows(state);
		state = addRoundKey(state, round, wordArray);
		
		return state;
	}
	
	
	private static byte[][] decryptBlock(byte[][] state, byte[][] wordArray) {
		int round = 10;
		state = addRoundKey(state, round, wordArray);
		state = invShiftRows(state);	
		state = invSubBytes(state);
		
		for (round = 9; round > 0; round--) {
			state = addRoundKey(state, round, wordArray);
			state = invMixColumns(state);
			state = invShiftRows(state);
			state = invSubBytes(state);
		}
		
		state = addRoundKey(state, round, wordArray);
		
		return state;
	}
	
	
	private static byte[][] mixColumns(byte[][] state) {
		// calculate each column
		for (int i = 0; i < 4; i++) {
			// copy the column
			byte[] col = Arrays.copyOf(state[i], state[i].length);
			
			state[i][0] = (byte) (galois_multiplication(col[0], (byte) 0x02)
								^ galois_multiplication(col[1], (byte) 0x03)
								^ galois_multiplication(col[2], (byte) 0x01)
								^ galois_multiplication(col[3], (byte) 0x01));
			
			state[i][1] = (byte) (galois_multiplication(col[0], (byte) 0x01)
								^ galois_multiplication(col[1], (byte) 0x02)
								^ galois_multiplication(col[2], (byte) 0x03)
								^ galois_multiplication(col[3], (byte) 0x01));
			
			state[i][2] = (byte) (galois_multiplication(col[0], (byte) 0x01)
								^ galois_multiplication(col[1], (byte) 0x01)
								^ galois_multiplication(col[2], (byte) 0x02)
								^ galois_multiplication(col[3], (byte) 0x03));
			
			state[i][3] = (byte) (galois_multiplication(col[0], (byte) 0x03)
								^ galois_multiplication(col[1], (byte) 0x01)
								^ galois_multiplication(col[2], (byte) 0x01)
								^ galois_multiplication(col[3], (byte) 0x02));
		}
		return state;
	}
	
	
	private static byte[][] invMixColumns(byte[][] state) {
		// calculate each column
		for (int i = 0; i < 4; i++) {
			// copy the column
			byte[] col = Arrays.copyOf(state[i], state[i].length);
			
			state[i][0] = (byte) (galois_multiplication(col[0], (byte) 0x0e)
								^ galois_multiplication(col[1], (byte) 0x0b)
								^ galois_multiplication(col[2], (byte) 0x0d)
								^ galois_multiplication(col[3], (byte) 0x09));
			
			state[i][1] = (byte) (galois_multiplication(col[0], (byte) 0x09)
								^ galois_multiplication(col[1], (byte) 0x0e)
								^ galois_multiplication(col[2], (byte) 0x0b)
								^ galois_multiplication(col[3], (byte) 0x0d));
			
			state[i][2] = (byte) (galois_multiplication(col[0], (byte) 0x0d)
								^ galois_multiplication(col[1], (byte) 0x09)
								^ galois_multiplication(col[2], (byte) 0x0e)
								^ galois_multiplication(col[3], (byte) 0x0b));
			
			state[i][3] = (byte) (galois_multiplication(col[0], (byte) 0x0b)
								^ galois_multiplication(col[1], (byte) 0x0d)
								^ galois_multiplication(col[2], (byte) 0x09)
								^ galois_multiplication(col[3], (byte) 0x0e));
		}
		return state;
	}
	
	
	// multiplies two bytes in the Galois Field GF(2^8)
	private static byte galois_multiplication(byte a, byte b) {
		byte product = 0;
		int msbBitSet;
	 
	    for (int counter = 0; counter < 8; counter++) {
	        if ((b & 1) == 1) {
	        	product ^= a;
	        }
	        msbBitSet = (a & 0x80);
	        a <<= 1;
	        if (msbBitSet == 0x80) { 
	            a ^= 0x1b;
	        }
	        b >>= 1;
	    }
	    return product;
	}
	
	
	private static byte[][] subBytes(byte[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = subByte(state[i][j]);
			}
		}
		return state;
	}
	
	
	private static byte[][] invSubBytes(byte[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = invSubByte(state[i][j]);
			}
		}
		return state;
	}


	private static byte[][] shiftRows(byte[][] state) {
		// rotate second row over 1 byte
		state = rotate1Byte(state, 1);
		
		// rotate third row over 2 bytes
		state = rotate1Byte(state, 2);
		state = rotate1Byte(state, 2);

		// rotate last row over 3 bytes
		state = rotate1Byte(state, 3);
		state = rotate1Byte(state, 3);
		state = rotate1Byte(state, 3);
		
		return state;
	}
	

	private static byte[][] rotate1Byte(byte[][] state, int row) {
		byte tmp = state[0][row];
		state[0][row] = state[1][row];
		state[1][row] = state[2][row];
		state[2][row] = state[3][row];
		state[3][row] = tmp;
		return state;
	}

	
	private static byte[][] invShiftRows(byte[][] state) {
		// rotate second row over 1 byte
		state = invRotate1Byte(state, 1);
		
		// rotate third row over 2 bytes
		state = invRotate1Byte(state, 2);
		state = invRotate1Byte(state, 2);

		// rotate last row over 3 bytes
		state = invRotate1Byte(state, 3);
		state = invRotate1Byte(state, 3);
		state = invRotate1Byte(state, 3);
		
		return state;
	}
	
	
	private static byte[][] invRotate1Byte(byte[][] state, int row) {
		byte tmp = state[3][row];
		state[3][row] = state[2][row];
		state[2][row] = state[1][row];
		state[1][row] = state[0][row];
		state[0][row] = tmp;
		return state;
	}
	

	private static byte[][] addRoundKey(byte[][] state, int round, byte[][] wordArray) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = (byte) (state[i][j] ^ wordArray[4 * round + i][j]);
			}
		}
		return state;
	}


	private static byte[][] keyExpansion(String cipherKey) {
		byte[][] wordArray = new byte[N_WORDS][4];
		
		// fill in first 4 columns of 44
		byte[][] state = hexStringToState(cipherKey);
		wordArray[0] = state[0];
		wordArray[1] = state[1];
		wordArray[2] = state[2];
		wordArray[3] = state[3];

		for (int i = 4; i < N_WORDS; i++) {
			if (i % 4 == 0) {
				
				byte[] word = Arrays.copyOf(wordArray[i - 1], wordArray[i - 1].length);
				word = rotWord(word);
				
				for (int j = 0; j < 4; j++) {
					word[j] = subByte(word[j]);
				}
				
				// XOR with Wi-4
				for (int j = 0; j < 4; j++) {
					word[j] = (byte) (word[j] ^ wordArray[i - 4][j]);
				}
				// XOR with Rcon (round constant)
				int round  = i / 4;
				byte rcon = getRcon(round);
				word[0] = (byte) (word[0] ^ rcon);

				wordArray[i] = word;
			}
			else {
				byte[] word = new byte[4];
				byte[] w1 = wordArray[i - 1];
				byte[] w2 = wordArray[i - 4];
				// XOR every byte
				for (int j = 0; j < 4; j++) {
					word[j] = (byte) (w1[j] ^ w2[j]);
				}
				wordArray[i] = word;
			}
		}
		
		return wordArray;
	}

	
	private static byte[] rotWord(byte[] word) {
		byte tmp = word[0];
		word[0] = word[1];
		word[1] = word[2];
		word[2] = word[3];
		word[3] = tmp;
		return word;
	}
	

	// Rcon for 10 rounds
	private static byte getRcon(int round) {

		final short[] rcon = {
		    0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36
		};
	
		return (byte) rcon[round - 1];
	}
	
	
	// Forward S-Box
	private static byte subByte(byte b) {
	
		final short[] forwSBox = {
		    // 0     1     2     3     4     5     6     7     8     9     A     B     C     D     E     F
		    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76, // 0
		    0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0, // 1
		    0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15, // 2
		    0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75, // 3
		    0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84, // 4
		    0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf, // 5
		    0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8, // 6
		    0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2, // 7
		    0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73, // 8
		    0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb, // 9
		    0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79, // A
		    0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08, // B
		    0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a, // C
		    0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e, // D
		    0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf, // E
		    0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16  // F
		};
		
		return (byte) forwSBox[b & 0xFF];
	}
	
	
	// Reverse S-Box
	private static byte invSubByte(byte b) {
	
		final short[] revSBox = { 
		    // 0     1     2     3     4     5     6     7     8     9     A     B     C     D     E     F
		    0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb, // 0
		    0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb, // 1 
		    0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e, // 2 
		    0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, // 3 
		    0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, // 4 
		    0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, // 5 
		    0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, // 6 
		    0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, // 7 
		    0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73, // 8 
		    0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e, // 9 
		    0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b, // A 
		    0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, // B 
		    0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, // C 
		    0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef, // D 
		    0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, // E 
		    0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d  // F
		};
		
		return (byte) revSBox[b & 0xFF];
	}		
	
	
	private static byte[][] hexStringToState(String state_str) {
		byte[][] state = new byte[4][4];
		
		// fill in 4 columns
		for (int i = 0; i < 4; i++) {
			
			byte[] word = new byte[4];
			// build 4 byte word
			for (int j = 0; j < 4; j++) {
				
				int startIndex = i * 8 + j * 2;
				String b = state_str.substring(startIndex, startIndex + 2);
				// convert String to byte
				word[j] = (byte) ((Character.digit(b.charAt(0), 16) << 4) + Character.digit(b.charAt(1), 16));	
			}
			state[i] = word;
		}
		return state;
	}

	
	private static String stateToHexString(byte[][] state) {
		String hexString = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				hexString += String.format("%2s", Integer.toHexString(state[i][j] & 0xFF)).replace(' ', '0');
			}
		}
		return hexString;
	}
	
	
}
