package durry.philipp;

/**
 * @author Philipp Durry
 * 
 */
public class Vigenere {
	
	// alphabet length is 95 since there are 95 printable characters in ASCII Code
	// the first printable character is at position Decimal 32
	private static final int ALPHABET_LENGTH = 95;
	
	public static String encrypt(String plaintext, String keyword) {	
		// extend the keyword so that keyword length >= plaintext length
		String longKeyword = keyword;
		while (longKeyword.length() < plaintext.length()) {
			longKeyword += keyword;
		}

		String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i) != '\n') {
                int plaintextChar = plaintext.charAt(i) - 32;
                int shift = longKeyword.charAt(i) - 32;
                int ciphertextChar = (plaintextChar + shift) % ALPHABET_LENGTH;
                ciphertext += (char) (ciphertextChar + 32);
            }
            else {
                ciphertext += '\n';
            }
        }
		return ciphertext;
	}
	
	
	public static String decrypt(String ciphertext, String keyword) {
		// extend the keyword so that keyword length >= ciphertext length
		String longKeyword = keyword;
		while (longKeyword.length() < ciphertext.length()) {
			longKeyword += keyword;
		}

		String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            if (ciphertext.charAt(i) != '\n') {
                int ciphertextChar = ciphertext.charAt(i) - 32;
                int shift = longKeyword.charAt(i) - 32;
                // add ALPHABET_LENGTH, so that the integer of the character doesn't get negative
                int plaintextChar = (ciphertextChar - shift + ALPHABET_LENGTH) % ALPHABET_LENGTH;
                plaintext += (char) (plaintextChar + 32);
            }
            else {
            	plaintext += '\n';
            }
        }
		return plaintext;
	}

}
