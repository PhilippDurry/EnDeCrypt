package durry.philipp;

import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

/**
 * @author Philipp Durry
 * 
 */
public class GUI {

	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
        // try to set the "Nimbus" Design
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        
		
		frame = new JFrame("EnDeCrypt");
		frame.setBounds(100, 100, 1296, 727);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(new CardLayout(0, 0));	 
		
	    final String CAESARPANEL = "Card for Caesar Cipher";
	    final String VIGENEREPANEL = "Card for Vigenere Cipher";
	    final String RSAPANEL = "Card for RSA";
	    final String WELCOMEPANEL = "Card for Welcome Screen";
	    final String ONETIMEPADPANEL = "Card for One Time Pad";
	    final String AESPANEL = "Card for AES";

		
	    ArrayList<Image> iconList = new ArrayList<Image>();
	    ImageIcon icon64 = new ImageIcon(getClass().getResource("/images/icon64.png"));
		ImageIcon icon32 = new ImageIcon(getClass().getResource("/images/icon32.png"));
		ImageIcon icon16 = new ImageIcon(getClass().getResource("/images/icon16.png"));
		iconList.add(icon64.getImage());
	    iconList.add(icon32.getImage());
	    iconList.add(icon16.getImage());
	    frame.setIconImages(iconList);
	    
	    JMenuBar menuBar = new JMenuBar();
	    menuBar.setMargin(new Insets(5, 0, 5, 0));
	    menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	    frame.setJMenuBar(menuBar);
	    
	    JMenu mnEncryptionAlgorithm = new JMenu("  Encryption Algorithm  ");
	    mnEncryptionAlgorithm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    menuBar.add(mnEncryptionAlgorithm);
	    
	    JPanel panelWelcomeScreen = new JPanel();
	    frame.getContentPane().add(panelWelcomeScreen, WELCOMEPANEL);
	    panelWelcomeScreen.setLayout(null);
	    panelWelcomeScreen.setOpaque(false);
	    
	    	    
	    JLabel label = new JLabel("");
	    label.setIcon(new ImageIcon(getClass().getResource("/images/TitleScreen1280x720.png")));
	    label.setBounds(0, 0, 1280, 667);
	    panelWelcomeScreen.add(label);
	    
	    JPanel panelRoot = new JPanel();
	    frame.getContentPane().add(panelRoot, "name_432459992458249");
	    panelRoot.setLayout(null);
	    panelRoot.setBackground(new Color(210, 210, 210));
	    
	    JPanel panelCipherArea = new JPanel();
	    panelCipherArea.setBounds(0, 0, 287, 667);
	    panelRoot.add(panelCipherArea);
	    panelCipherArea.setLayout(new CardLayout(0, 0));
	    panelCipherArea.setOpaque(false);
	    
	    JMenuItem mntmCaesarCipher = new JMenuItem("Caesar Cipher");
	    mntmCaesarCipher.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    mntmCaesarCipher.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	    CardLayout cl = (CardLayout)(panelCipherArea.getLayout());
	    	    cl.show(panelCipherArea, CAESARPANEL);
	    	    frame.getContentPane().remove(panelWelcomeScreen);
	    	}
	    });
	    mnEncryptionAlgorithm.add(mntmCaesarCipher);
	    
	    JMenuItem mntmVigenreCipher = new JMenuItem("Vigen\u00E8re Cipher");
	    mntmVigenreCipher.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    mntmVigenreCipher.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	    CardLayout cl = (CardLayout)(panelCipherArea.getLayout());
	    	    cl.show(panelCipherArea, VIGENEREPANEL);
	    	    frame.getContentPane().remove(panelWelcomeScreen);
	    	}
	    });
	    mnEncryptionAlgorithm.add(mntmVigenreCipher);	    
	    
	    JMenuItem mntmRSA = new JMenuItem("RSA");
	    mntmRSA.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    mntmRSA.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	    CardLayout cl = (CardLayout)(panelCipherArea.getLayout());
	    	    cl.show(panelCipherArea, RSAPANEL);
	    	    frame.getContentPane().remove(panelWelcomeScreen);
	    	}
	    });
	    
	    JMenuItem mntmOneTimePad = new JMenuItem("One Time Pad");
	    mntmOneTimePad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    mntmOneTimePad.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	    CardLayout cl = (CardLayout)(panelCipherArea.getLayout());
	    	    cl.show(panelCipherArea, ONETIMEPADPANEL);
	    	    frame.getContentPane().remove(panelWelcomeScreen);
	    	}
	    });
	    mnEncryptionAlgorithm.add(mntmOneTimePad);
	    
	    JMenuItem mntmAES = new JMenuItem("AES (Rijndael)");
	    mntmAES.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	    CardLayout cl = (CardLayout)(panelCipherArea.getLayout());
	    	    cl.show(panelCipherArea, AESPANEL);
	    	    frame.getContentPane().remove(panelWelcomeScreen);
	    	}
	    });
	    mntmAES.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    mnEncryptionAlgorithm.add(mntmAES);
	    mnEncryptionAlgorithm.add(mntmRSA);

	    JMenu mnHelp = new JMenu("  Help  ");
	    mnHelp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    menuBar.add(mnHelp);
	    
	    JMenuItem mntmAboutEndecrypt = new JMenuItem("About EnDeCrypt");
	    mntmAboutEndecrypt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	    mntmAboutEndecrypt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JOptionPane.showMessageDialog(frame,
	    			    "written by Philipp A. Durry, 2017",
	    			    "About EnDeCrypt",
	    			    JOptionPane.INFORMATION_MESSAGE, icon64);
	    	}
	    });
	    mntmAboutEndecrypt.setIcon(icon16);
	    mnHelp.add(mntmAboutEndecrypt);
	       
	    

	    
	    JPanel panelCaesarCipher = new JPanel();
	    panelCipherArea.add(panelCaesarCipher, CAESARPANEL);
	    panelCaesarCipher.setLayout(null);
	    panelCaesarCipher.setOpaque(false);
	    
	    JLabel lblCaesarCipher = new JLabel("Caesar Cipher");
	    lblCaesarCipher.setFont(new Font("Arial", Font.BOLD, 25));
	    lblCaesarCipher.setBounds(50, 11, 215, 49);
	    panelCaesarCipher.add(lblCaesarCipher);
	    
	    JLabel lblCaesarShift = new JLabel("Caesar Shift:");
	    lblCaesarShift.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblCaesarShift.setBounds(30, 130, 107, 49);
	    panelCaesarCipher.add(lblCaesarShift);
	    
	    JTextField caesarShiftField;
	    caesarShiftField = new JTextField();
	    caesarShiftField.setText("0");
	    caesarShiftField.setFont(new Font("Arial", Font.PLAIN, 15));
	    caesarShiftField.setBounds(135, 140, 86, 29);
	    panelCaesarCipher.add(caesarShiftField);
	    caesarShiftField.setColumns(10);
	    
	    JSeparator separator1 = new JSeparator();
	    separator1.setOrientation(SwingConstants.VERTICAL);
	    separator1.setBounds(275, 0, 2, 667);
	    panelCaesarCipher.add(separator1);
	    
	    JPanel panelVigenere = new JPanel();
	    panelVigenere.setLayout(null);
	    panelVigenere.setOpaque(false);
	    panelCipherArea.add(panelVigenere, VIGENEREPANEL);
	    
	    JLabel lblVigenreCipher = new JLabel("Vigen\u00E8re Cipher");
	    lblVigenreCipher.setFont(new Font("Arial", Font.BOLD, 25));
	    lblVigenreCipher.setBounds(49, 11, 209, 49);
	    panelVigenere.add(lblVigenreCipher);
	    
	    JSeparator separator = new JSeparator();
	    separator.setOrientation(SwingConstants.VERTICAL);
	    separator.setBounds(275, 0, 2, 667);
	    panelVigenere.add(separator);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane.setBounds(10, 186, 255, 49);
	    panelVigenere.add(scrollPane);
	    
	    JTextArea vigenereKeyField = new JTextArea();
	    vigenereKeyField.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane.setViewportView(vigenereKeyField);
	    
	    JLabel lblKeyword = new JLabel("Keyword:");
	    lblKeyword.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblKeyword.setBounds(10, 132, 107, 49);
	    panelVigenere.add(lblKeyword);
	    
	    JPanel panelOneTimePad = new JPanel();
	    panelOneTimePad.setLayout(null);
	    panelOneTimePad.setOpaque(false);
	    panelCipherArea.add(panelOneTimePad, ONETIMEPADPANEL);
	    
	    JLabel lblOneTimePad = new JLabel("One Time Pad");
	    lblOneTimePad.setFont(new Font("Arial", Font.BOLD, 25));
	    lblOneTimePad.setBounds(49, 11, 209, 49);
	    panelOneTimePad.add(lblOneTimePad);
	    
	    JSeparator separator_1 = new JSeparator();
	    separator_1.setOrientation(SwingConstants.VERTICAL);
	    separator_1.setBounds(275, 0, 2, 667);
	    panelOneTimePad.add(separator_1);
	    
	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane_1.setBounds(10, 278, 255, 49);
	    panelOneTimePad.add(scrollPane_1);
	    
	    JTextArea oneTimePadBitStreamField = new JTextArea();
	    oneTimePadBitStreamField.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane_1.setViewportView(oneTimePadBitStreamField);
	    
	    JLabel lblBitStream = new JLabel("bit stream:");
	    lblBitStream.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblBitStream.setBounds(10, 236, 107, 49);
	    panelOneTimePad.add(lblBitStream);
	    
	    JPanel panelAES = new JPanel();
	    panelAES.setLayout(null);
	    panelAES.setOpaque(false);
	    panelCipherArea.add(panelAES, AESPANEL);
	    
	    JLabel lblNewLabel = new JLabel("AES (Rijndael)");
	    lblNewLabel.setFont(new Font("Arial", Font.BOLD, 25));
	    lblNewLabel.setBounds(56, 11, 200, 64);
	    panelAES.add(lblNewLabel);
	    
	    JScrollPane scrollPane_6 = new JScrollPane();
	    scrollPane_6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane_6.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane_6.setBounds(10, 277, 255, 52);
	    panelAES.add(scrollPane_6);
	    
	    JTextArea AESKeyField = new JTextArea();
	    AESKeyField.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane_6.setViewportView(AESKeyField);
	    
	    JButton btnGeneratebitsKey = new JButton("generate 128-bit key");
	    btnGeneratebitsKey.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String AESKey = AES.generateCipherKey();
	    		AESKeyField.setText(AESKey);
	    	}
	    });
	    btnGeneratebitsKey.setFont(new Font("Arial", Font.PLAIN, 15));
	    btnGeneratebitsKey.setBounds(28, 147, 221, 70);
	    panelAES.add(btnGeneratebitsKey);
	    
	    JLabel lbl128BitKey = new JLabel("128-bit key:");
	    lbl128BitKey.setFont(new Font("Arial", Font.PLAIN, 16));
	    lbl128BitKey.setBounds(10, 243, 96, 34);
	    panelAES.add(lbl128BitKey);
	    
	    JSeparator separator_2 = new JSeparator();
	    separator_2.setOrientation(SwingConstants.VERTICAL);
	    separator_2.setBounds(275, 0, 2, 667);
	    panelAES.add(separator_2);
	    
	    JPanel panelRSA = new JPanel();
	    panelRSA.setLayout(null);
	    panelRSA.setOpaque(false);
	    panelCipherArea.add(panelRSA, RSAPANEL);
	    
	    JLabel lblRsa = new JLabel("RSA");
	    lblRsa.setFont(new Font("Arial", Font.BOLD, 25));
	    lblRsa.setBounds(112, 11, 72, 49);
	    panelRSA.add(lblRsa);
	    
	    JSeparator separator2 = new JSeparator();
	    separator2.setOrientation(SwingConstants.VERTICAL);
	    separator2.setBounds(275, 0, 2, 667);
	    panelRSA.add(separator2);
	    
	    JLabel lblKeyLength = new JLabel("key length:");
	    lblKeyLength.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblKeyLength.setBounds(10, 137, 81, 34);
	    panelRSA.add(lblKeyLength);
	    
	    JTextField keyLengthField;
	    keyLengthField = new JTextField();
	    keyLengthField.setFont(new Font("Arial", Font.PLAIN, 15));
	    keyLengthField.setText("2048");
	    keyLengthField.setBounds(89, 140, 46, 30);
	    panelRSA.add(keyLengthField);
	    keyLengthField.setColumns(10);
	    
	    JLabel lblBits = new JLabel("bits");
	    lblBits.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblBits.setBounds(138, 147, 46, 14);
	    panelRSA.add(lblBits);
	    
	    JLabel lblPublicKey = new JLabel("public key:");
	    lblPublicKey.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblPublicKey.setBounds(10, 297, 88, 14);
	    panelRSA.add(lblPublicKey);
	    
	    JLabel lblPrivateKey = new JLabel("private key:");
	    lblPrivateKey.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblPrivateKey.setBounds(10, 479, 88, 14);
	    panelRSA.add(lblPrivateKey);
	    
	    JLabel lblE = new JLabel("e =");
	    lblE.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblE.setBounds(10, 345, 46, 14);
	    panelRSA.add(lblE);
	    
	    JLabel lblN = new JLabel("n =");
	    lblN.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblN.setBounds(10, 405, 46, 14);
	    panelRSA.add(lblN);
	    
	    JLabel lblD = new JLabel("d =");
	    lblD.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblD.setBounds(10, 527, 46, 14);
	    panelRSA.add(lblD);
	    
	    JLabel lblN_1 = new JLabel("n =");
	    lblN_1.setFont(new Font("Arial", Font.PLAIN, 16));
	    lblN_1.setBounds(10, 587, 46, 14);
	    panelRSA.add(lblN_1);
	    
	    JScrollPane scrollPane_5 = new JScrollPane();
	    scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane_5.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane_5.setBounds(40, 331, 225, 49);
	    panelRSA.add(scrollPane_5);
	    
	    JTextArea eField = new JTextArea();
	    eField.setFont(new Font("Arial", Font.PLAIN, 15));
	    eField.setText("65537");
	    scrollPane_5.setViewportView(eField);
	    
	    JScrollPane scrollPane_2 = new JScrollPane();
	    scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane_2.setBounds(40, 391, 225, 49);
	    panelRSA.add(scrollPane_2);
	    
	    JTextArea nField1 = new JTextArea();
	    nField1.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane_2.setViewportView(nField1);
	    
	    JScrollPane scrollPane_3 = new JScrollPane();
	    scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane_3.setBounds(40, 513, 225, 49);
	    panelRSA.add(scrollPane_3);
	    
	    JTextArea dField = new JTextArea();
	    dField.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane_3.setViewportView(dField);
	    
	    JScrollPane scrollPane_4 = new JScrollPane();
	    scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollPane_4.setBounds(40, 573, 225, 49);
	    panelRSA.add(scrollPane_4);
	    
	    JTextArea nField2 = new JTextArea();
	    nField2.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane_4.setViewportView(nField2);
	    
	    JButton btnRSAKeys = new JButton("generate public and private keys");
	    btnRSAKeys.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String keyLength = keyLengthField.getText();
	    		List<Object> n_and_d = RSA.generatePublicPrivateKeys(Integer.parseInt(keyLength));
	    		nField1.setText(n_and_d.get(0).toString());
	    		nField2.setText(n_and_d.get(0).toString());
	    		dField.setText(n_and_d.get(1).toString());
	    	}
	    });
	    btnRSAKeys.setFont(new Font("Arial", Font.PLAIN, 15));
	    btnRSAKeys.setBounds(20, 195, 241, 71);
	    panelRSA.add(btnRSAKeys);
	    
	    JPanel panelTextArea = new JPanel();
	    panelTextArea.setBounds(297, 0, 983, 667);
	    panelRoot.add(panelTextArea);
	    panelTextArea.setLayout(null);
	    panelTextArea.setOpaque(false);
	    
	    JScrollPane scrollPane1 = new JScrollPane();
	    scrollPane1.setBounds(10, 66, 950, 204);
	    panelTextArea.add(scrollPane1);
	    
	    JTextArea plaintextArea = new JTextArea();
	    plaintextArea.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane1.setViewportView(plaintextArea);
	    
	    JButton btnBitStream = new JButton("generate random bit stream");
	    btnBitStream.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String plaintext = plaintextArea.getText();
	    		String bitStream = OneTimePad.generateBitStream(plaintext);
	    		oneTimePadBitStreamField.setText(bitStream);
	    	}
	    });
	    btnBitStream.setBounds(21, 150, 230, 67);
	    panelOneTimePad.add(btnBitStream);
	    btnBitStream.setFont(new Font("Arial", Font.PLAIN, 15));
	    
	    JScrollPane scrollPane2 = new JScrollPane();
	    scrollPane2.setBounds(10, 431, 950, 204);
	    panelTextArea.add(scrollPane2);
	    
	    JTextArea ciphertextArea = new JTextArea();
	    ciphertextArea.setFont(new Font("Arial", Font.PLAIN, 15));
	    scrollPane2.setViewportView(ciphertextArea);
	    
	    JButton btnEncrypt = new JButton("Encrypt");
	    btnEncrypt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Controller.encrypt(panelCipherArea, plaintextArea, ciphertextArea, caesarShiftField, vigenereKeyField, oneTimePadBitStreamField, nField1, eField, keyLengthField, AESKeyField);
	    	}
	    });
	    btnEncrypt.setFont(new Font("Arial", Font.PLAIN, 15));
	    btnEncrypt.setBounds(281, 316, 206, 67);
	    panelTextArea.add(btnEncrypt);
	    
	    JButton btnDecrypt = new JButton("Decrypt");
	    btnDecrypt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Controller.decrypt(panelCipherArea, plaintextArea, ciphertextArea, caesarShiftField, vigenereKeyField, oneTimePadBitStreamField, nField2, dField, AESKeyField);
	    	}
	    });
	    btnDecrypt.setFont(new Font("Arial", Font.PLAIN, 15));
	    btnDecrypt.setBounds(620, 316, 206, 67);
	    panelTextArea.add(btnDecrypt);
	    
	    JLabel lblCiphertext = new JLabel("Ciphertext");
	    lblCiphertext.setBounds(10, 376, 120, 44);
	    panelTextArea.add(lblCiphertext);
	    lblCiphertext.setFont(new Font("Arial", Font.PLAIN, 23));
	    
	    JLabel lblPlaintext = new JLabel("Plaintext");
	    lblPlaintext.setBounds(10, 11, 120, 44);
	    panelTextArea.add(lblPlaintext);
	    lblPlaintext.setFont(new Font("Arial", Font.PLAIN, 23));

	}
}
