package durry.philipp;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Philipp Durry
 * 
 */
public class Controller {
	
	// the following integer constants define the order of the encryption algorithms in panelCipherArea
	private static final int CAESAR_CIPHER = 0;
	private static final int VIGENERE_CIPHER = 1;
	private static final int ONE_TIME_PAD = 2;
	private static final int AES_ = 3;
	private static final int RSA_ = 4;
	
	public static void encrypt(JPanel panelCipherArea, JTextArea plaintextArea, JTextArea ciphertextArea, JTextField caesarShiftField, JTextArea vigenereKeyField, JTextArea oneTimePadBitStreamField, JTextArea nField, JTextArea eField, JTextField keyLengthField, JTextArea AESKeyField) {
		String plaintext = plaintextArea.getText();
		String ciphertext = null;
		
		Component[] components = panelCipherArea.getComponents();
		if (components[CAESAR_CIPHER].isShowing()) {
		    Integer caesarShift = Integer.parseInt(caesarShiftField.getText());
		    ciphertext = Caesar.encrypt(plaintext, caesarShift);
		}
		else if (components[VIGENERE_CIPHER].isShowing()) {
			String keyword = vigenereKeyField.getText();
			ciphertext = Vigenere.encrypt(plaintext, keyword);
		}
		else if (components[ONE_TIME_PAD].isShowing()) {
			String bitStream = oneTimePadBitStreamField.getText();
			ciphertext = OneTimePad.encrypt(plaintext, bitStream);
		}
		else if (components[AES_].isShowing()) {
			String AESkey = AESKeyField.getText();
			ciphertext = AES.encrypt(plaintext, AESkey);
		}
		else if (components[RSA_].isShowing()) {
			String n = nField.getText();
			String e = eField.getText();
			int keyLength = Integer.parseInt(keyLengthField.getText());
		    ciphertext = RSA.encrypt(plaintext, n, e, keyLength);
		}
		ciphertextArea.setText(ciphertext);
	}
	
	
	public static void decrypt(JPanel panelCipherArea, JTextArea plaintextArea, JTextArea ciphertextArea, JTextField caesarShiftField, JTextArea vigenereKeyField, JTextArea oneTimePadBitStreamField, JTextArea nField, JTextArea dField, JTextArea AESKeyField) {
		String ciphertext = ciphertextArea.getText();
		String plaintext = null;
		
		Component[] components = panelCipherArea.getComponents();
		if (components[CAESAR_CIPHER].isShowing()) {
			Integer caesarShift = Integer.parseInt(caesarShiftField.getText());
			plaintext = Caesar.decrypt(ciphertext, caesarShift);
		}
		else if (components[VIGENERE_CIPHER].isShowing()) {
			String keyword = vigenereKeyField.getText();
			plaintext = Vigenere.decrypt(ciphertext, keyword);
		}
		else if (components[ONE_TIME_PAD].isShowing()) {
			String bitStream = oneTimePadBitStreamField.getText();
			plaintext = OneTimePad.decrypt(ciphertext, bitStream);
		}
		else if (components[AES_].isShowing()) {
			String AESkey = AESKeyField.getText();
			plaintext = AES.decrypt(ciphertext, AESkey);
		}
		else if (components[RSA_].isShowing()) {
			String n = nField.getText();
			String d = dField.getText();
			plaintext = RSA.decrypt(ciphertext, n, d);
		}
		plaintextArea.setText(plaintext);
	}
	
}
