package durry.philipp;


/**
 * @author Philipp Durry
 *         on 27.06.2017.
 */
public class Caesar {
	
	// alphabet length is 95 since there are 95 printable characters in ASCII Code
	// the first printable character is at position Decimal 32
	private static final int ALPHABET_LENGTH = 95;

    public static String encrypt(String plaintext, int caesarShift) {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i) != '\n') {
                int plaintextChar = plaintext.charAt(i) - 32;
                int ciphertextChar = (plaintextChar + caesarShift) % ALPHABET_LENGTH;
                ciphertext += (char) (ciphertextChar + 32);
            }
            else {
                ciphertext += '\n';
            }
        }
        return ciphertext;
    }

    public static String decrypt(String ciphertext, int caesarShift) {
        // decrypting is the same as encrypting but with negative shift
        // add ALPHABET_LENGTH, so that the integer of the character doesn't get negative
        String plaintext = encrypt(ciphertext, -caesarShift + ALPHABET_LENGTH);
        return plaintext;
    }

}
