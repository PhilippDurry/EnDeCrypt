package durry.philipp;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * @author Philipp Durry
 * 
 */
public class RSA {

	public static String encrypt(String plaintext, String n_str, String e_str, int keyLength) {
		String ciphertext;
		
		int char_counter = 0;
		for (int i = 0; i < plaintext.length(); i++) {
			char_counter += 1;
		}
		if (char_counter * 8 > keyLength) {
			return    "\n\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n"
					+ "                               Plaintext is too long! The plaintext must not contain more characters than key length divided by 8"
					+ "\n\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
		}
		
		String textToBigInt = "";
		for (int i = 0; i < plaintext.length(); i++) {
			int character = plaintext.charAt(i) - 32;

			// line feed extra behandeln
			if (character == -22) {
				textToBigInt += 95;		// line feed als 95 codiert
			}
			// make sure every character is represented by two decimal digits
			else if (character < 10) {
				textToBigInt += 0;
				textToBigInt += character;
			}
			else {
				textToBigInt += character;
			}
		}
		BigInteger m = new BigInteger(textToBigInt);
		BigInteger n = new BigInteger(n_str);
		BigInteger e = new BigInteger(e_str);
		
		BigInteger c = m.modPow(e, n);
		ciphertext = c.toString();
		
		return ciphertext;
	}
	
	
	public static String decrypt(String ciphertext, String n_str, String d_str) {
		String plaintext = "";
		
		BigInteger c = new BigInteger(ciphertext);
		BigInteger n = new BigInteger(n_str);
		BigInteger d = new BigInteger(d_str);
		
		BigInteger m = c.modPow(d, n);
		
		String m_str = m.toString();
		for (int i = 0; i < m_str.length(); i += 2) {
			String char_decimal = m_str.substring(i, i + 2);
			int character = Integer.parseInt(char_decimal) + 32;
			// line feed extra behandeln
			if (character == 127) {
				plaintext += (char) 10;				// wieder line feed einsetzen
			}
			else {
				plaintext += (char) character;
			}
		}
		return plaintext;
	}

	
	public static List<Object> generatePublicPrivateKeys(int keyLength) {
		SecureRandom rnd = new SecureRandom();
		BigInteger p;
		BigInteger q;
		BigInteger n;
		do {
			p = BigInteger.probablePrime(keyLength / 2, rnd);
			q = BigInteger.probablePrime(keyLength / 2, rnd);
			n = p.multiply(q);
		} while (p == q || n.bitLength() != keyLength);
		
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger e = new BigInteger("65537");		
		BigInteger d = e.modInverse(phi);
		
		return Arrays.asList(n, d);
	}

}
