package rsa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import rsa.rsa_impl.RSA;

public class EncryptionTab extends JPanel implements ActionListener
{
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Window Components.
	 */
	private JLabel plaintext;
	private JLabel e_key;
	private JLabel n_key;
	private JLabel cipher;
	private JButton encrypt;
	private JTextArea plain_text;
	private JScrollPane plain_scroll;
	private JTextArea cipher_text;
	private JScrollPane cipher_scroll;
	private JTextArea e_key_text;
	private JScrollPane e_key_scroll;
	private JTextArea n_key_text;
	private JScrollPane n_key_scroll;
	
	/**
	 * The RSA implementation.
	 */
	private RSA rsa;
	
	
	
	
	/**
	 * Constructor
	 * 
	 * @param rsa, An RSA implementation object.
	 */
	public EncryptionTab(RSA rsa)
	{
		this.setLayout(null);
		this.setBounds(0, 0, 500, 600);
		this.setVisible(true);
		
		this.rsa = rsa;
		
		plaintext = new JLabel("PLAINTEXT : ");
		this.add(plaintext);
		plaintext.setBounds(50,20,100,20);
		
		n_key = new JLabel("PUBLIC KEY - n : ");
		this.add(n_key);
		n_key.setBounds(50,150,120,20);
		
		e_key = new JLabel("PUBLIC KEY - e : ");
		this.add(e_key);
		e_key.setBounds(50,280,120,20);
		
		cipher = new JLabel("CIPHERTEXT : ");
		this.add(cipher);
		cipher.setBounds(50,410,120,20);
		
		encrypt = new JButton("ENCRYPT");
		this.add(encrypt);
		encrypt.setBounds(170,490,150,30);
		encrypt.addActionListener(this);
		
		plain_text = new JTextArea();
		this.add(plain_text);
		plain_text.setEnabled(true);
		plain_text.setLineWrap(true);
		plain_scroll = new JScrollPane(plain_text);
		this.add(plain_scroll);
		plain_scroll.setBounds(200,10,250,70);
		plain_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		n_key_text = new JTextArea();
		this.add(n_key_text);
		n_key_text.setLineWrap(true);
		n_key_scroll = new JScrollPane(n_key_text);
		this.add(n_key_scroll);
		n_key_scroll.setBounds(200,130,250,70);
		n_key_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		e_key_text = new JTextArea();
		this.add(e_key_text);
		e_key_text.setLineWrap(true);
		e_key_scroll = new JScrollPane(e_key_text);
		this.add(e_key_scroll);
		e_key_scroll.setBounds(200,260,250,70);
		e_key_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		cipher_text = new JTextArea();
		this.add(cipher_text);
		cipher_text.setEditable(false);
		cipher_text.setLineWrap(true);
		cipher_scroll = new JScrollPane(cipher_text);
		this.add(cipher_scroll);
		cipher_scroll.setBounds(200,390,250,70);
		cipher_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == encrypt)
		{
			try
			{
				String plaintxt = plain_text.getText();
				BigInteger e = new BigInteger(e_key_text.getText().trim());
				BigInteger n = new BigInteger(n_key_text.getText().trim());
				String ciphertxt = rsa.GetCiphertext(plaintxt,n,e);
				cipher_text.setText(ciphertxt);
			}
			
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , "ERROR : " + e.getMessage() , "ERROR OCCURED" , JOptionPane.ERROR_MESSAGE);
			}
		}	
	}
}