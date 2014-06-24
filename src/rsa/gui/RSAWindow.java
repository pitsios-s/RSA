package rsa.gui;

import javax.swing.*;

import rsa.rsa_impl.RSAImpl;


public class RSAWindow extends JFrame 
{
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The tab pane.
	 */
	private JTabbedPane tabs;
	
	
	
	
	/**
	 * Default Constructor.
	 */
	public RSAWindow()
	{
		super("RSA PROGRAM");
		this.setLayout(null);
		this.setBounds(100, 100, 500, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
				
		tabs = new JTabbedPane();
		tabs.setBounds(0,0,495,570);
		tabs.add("NUMBER GENERATION" , new NumGeneratorTab(new RSAImpl()));
		tabs.addTab("ENCRYPT" , new EncryptionTab(new RSAImpl()));
		tabs.add("DECRYPT", new DecryptionTab(new RSAImpl()));		
		this.add(tabs);
	}
	
	
	
	
	public static void main(String[] args)
	{
		RSAWindow rsaWin = new RSAWindow();
		rsaWin.setVisible(true);
	}
}