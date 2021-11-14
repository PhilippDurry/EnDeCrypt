package durry.philipp;

import java.security.SecureRandom;

/**
 * @author Philipp Durry
 * 
 */
public class OneTimePad {
	
	public static String generateBitStream(String plaintext) {
		int charCount = 0;
		for (int i = 0; i < plaintext.length(); i++) {
			charCount += 1;
		}
		
		SecureRandom random = new SecureRandom();
	    byte[] randomBytes = new byte[charCount];
	    random.nextBytes(randomBytes);
		
		String bitStream = "";
		for (int i = 0; i < charCount; i++) {
			bitStream += String.format("%8s", Integer.toBinaryString(randomBytes[i] & 0xFF)).replace(' ', '0');
		}
		return bitStream;
	}
	
	
	public static String encrypt(String plaintext, String bitStream) {
		String plaintextBits = "";
		for (int i = 0; i < plaintext.length(); i++) {
			plaintextBits += String.format("%8s", Integer.toBinaryString(plaintext.charAt(i) & 0xFF)).replace(' ', '0');
		}
		
		String ciphertextBits = "";
		for (int i = 0; i < bitStream.length(); i++) {
			ciphertextBits +=  plaintextBits.charAt(i) ^ bitStream.charAt(i);
		}
		
		// convert to hex
		String ciphertext = "";
		String fourBits = "";
		for (int i = 0; i < ciphertextBits.length(); i++) {
			fourBits += ciphertextBits.charAt(i);
			if (fourBits.length() == 4) {
				int decimal = Integer.parseInt(fourBits, 2);
				String hex = Integer.toHexString(decimal);
				ciphertext += hex;
				fourBits = "";
			}
		}
		return ciphertext;
	}


	public static String decrypt(String ciphertext, String bitStream) {
		String ciphertextBits = "";
		for (int i = 0; i < ciphertext.length(); i++) {
			String c = String.valueOf(ciphertext.charAt(i));
			int hex = Integer.parseInt(c, 16);
			ciphertextBits += String.format("%4s", Integer.toBinaryString(hex)).replace(' ', '0');
		}
		
		String plaintextBits = "";
		for (int i = 0; i < bitStream.length(); i++) {
			plaintextBits +=  ciphertextBits.charAt(i) ^ bitStream.charAt(i);
		}

		String plaintext = "";
		String c = "";
		for (int i = 0; i < plaintextBits.length(); i++) {
			c += plaintextBits.charAt(i);
			if (c.length() == 8) {
				int decimal = Integer.parseInt(c, 2);
				plaintext += (char) decimal;
				c = "";
			}
		}		
		return plaintext;
	}

}