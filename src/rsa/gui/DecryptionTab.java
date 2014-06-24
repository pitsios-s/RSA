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

public class DecryptionTab extends JPanel implements ActionListener
{
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Window Components
	 */
	private JLabel ciphertext;
	private JLabel n_key2;
	private JLabel d_key;
	private JLabel plaintext2;
	private JButton decrypt;
	private JTextArea plain_text2;
	private JScrollPane plain_scroll2;
	private JTextArea cipher_text2;
	private JScrollPane cipher_scroll2;
	private JTextArea n_key_text2;
	private JScrollPane n_key_scroll2;
	private JTextArea d_key_text;
	private JScrollPane d_key_scroll;
	
	/**
	 * The RSA implementation.
	 */
	private RSA rsa;
	
	
	
	
	/**
	 * Constructor
	 * 
	 * @param rsa, An RSA implementation object.
	 */
	public DecryptionTab(RSA rsa)
	{
		this.setLayout(null);
		this.setBounds(0, 0, 500, 600);
		this.setVisible(true);
		
		this.rsa = rsa;
		
		ciphertext = new JLabel("CIPHERTEXT : ");
		this.add(ciphertext);
		ciphertext.setBounds(50,20,120,20);
		
		n_key2 = new JLabel("PUBLIC KEY - n : ");
		this.add(n_key2);
		n_key2.setBounds(50,150,120,20);
		
		d_key = new JLabel("PRIVATE KEY - d : ");
		this.add(d_key);
		d_key.setBounds(50,280,120,20);
		
		plaintext2 = new JLabel("PLAINTEXT : ");
		this.add(plaintext2);
		plaintext2.setBounds(50,410,100,20);
		
		decrypt = new JButton("DECRYPT");
		this.add(decrypt);
		decrypt.setBounds(170,490,150,30);
		decrypt.addActionListener(this);
		
		cipher_text2 = new JTextArea();
		this.add(cipher_text2);
		cipher_text2.setLineWrap(true);
		cipher_scroll2 = new JScrollPane(cipher_text2);
		this.add(cipher_scroll2);
		cipher_scroll2.setBounds(200,10,250,70);
		cipher_scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		n_key_text2 = new JTextArea();
		this.add(n_key_text2);
		n_key_text2.setLineWrap(true);
		n_key_scroll2 = new JScrollPane(n_key_text2);
		this.add(n_key_scroll2);
		n_key_scroll2.setBounds(200,130,250,70);
		n_key_scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		d_key_text = new JTextArea();
		this.add(d_key_text);
		d_key_text.setLineWrap(true);
		d_key_scroll = new JScrollPane(d_key_text);
		this.add(d_key_scroll);
		d_key_scroll.setBounds(200,260,250,70);
		d_key_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		plain_text2 = new JTextArea();
		this.add(plain_text2);
		plain_text2.setEditable(false);
		plain_text2.setLineWrap(true);
		plain_scroll2 = new JScrollPane(plain_text2);
		this.add(plain_scroll2);
		plain_scroll2.setBounds(200,390,250,70);
		plain_scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == decrypt)
		{
			try
			{
				String ciphertxt = cipher_text2.getText().trim();
				BigInteger d = new BigInteger(d_key_text.getText().trim());
				BigInteger n = new BigInteger(n_key_text2.getText().trim());
				String plaintxt = rsa.GetPlaintext(ciphertxt,n,d);
				plain_text2.setText(plaintxt);
			}
			
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null , "ERROR : " +e.getMessage() , "ERROR OCCURED" , JOptionPane.ERROR_MESSAGE);
			}
		}	
	}
}