package rsa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import rsa.rsa_impl.RSA;

public class NumGeneratorTab extends JPanel implements ActionListener
{
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Window components.
	 */
	private JButton clear;
	private JButton numbers;
	private JComboBox<String> box;
	private JLabel size;
	private JLabel p;
	private JLabel q;
	private JLabel n;
	private JLabel e;
	private JLabel d;
	private JTextArea p_text;
	private JScrollPane p_scroll;
	private JTextArea q_text;
	private JScrollPane q_scroll;
	private JTextArea n_text;
	private JScrollPane n_scroll;
	private JTextArea e_text;
	private JScrollPane e_scroll;
	private JTextArea d_text;
	private JScrollPane d_scroll;
	
	/**
	 * The RSA implementation.
	 */
	private RSA rsa;
	
	
	
	
	/**
	 * Constructor
	 * 
	 * @param rsa, An RSA implementation object.
	 */
	public NumGeneratorTab(RSA rsa)
	{
		this.setLayout(null);
		this.setBounds(0, 0, 500, 600);
		this.setVisible(true);
		
		this.rsa = rsa;
		
		size = new JLabel("NUMBER SIZE : ");
		this.add(size);
		size.setBounds(50,10,120,50);
		
		p = new JLabel("p : ");
		this.add(p);
		p.setBounds(50,80,100,50);
		
		q = new JLabel("q : ");
		this.add(q);
		q.setBounds(50,150,100,50);
		
		n = new JLabel("n : ");
		this.add(n);
		n.setBounds(50,220,100,50);
		
		e = new JLabel("e : ");
		this.add(e);
		e.setBounds(50,290,100,50);
		
		d = new JLabel("d : ");
		this.add(d);
		d.setBounds(50,360,100,50);
		
		numbers = new JButton("Generate Numbers");
		this.add(numbers);
		numbers.setBounds(50,480,170,30);
		numbers.addActionListener(this);
		
		clear = new JButton("CLEAR");
		this.add(clear);
		clear.setBounds(250,480,150,30);
		clear.addActionListener(this);
		
		box = new JComboBox<String>();
		this.add(box);
		box.addItem("8 bits");
		box.addItem("16 bits");
		box.addItem("32 bits");
		box.addItem("64 bits");
		box.addItem("128 bits");
		box.addItem("256 bits");
		box.addItem("512 bits");
		box.addItem("1024 bits");
		box.addItem("2048 bits");
		box.setBounds(200,30,100,20);
		
		p_text = new JTextArea();
		this.add(p_text);
		p_text.setEditable(false);
		p_text.setLineWrap(true);
		p_scroll = new JScrollPane(p_text);
		this.add(p_scroll);
		p_scroll.setBounds(200,80,250,50);
		p_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		q_text = new JTextArea();
		this.add(q_text);
		q_text.setEditable(false);
		q_text.setLineWrap(true);
		q_scroll = new JScrollPane(q_text);
		this.add(q_scroll);
		q_scroll.setBounds(200,150,250,50);
		q_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		n_text = new JTextArea();
		this.add(n_text);
		n_text.setEditable(false);
		n_text.setLineWrap(true);
		n_scroll = new JScrollPane(n_text);
		this.add(n_scroll);
		n_scroll.setBounds(200,220,250,50);
		n_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		e_text = new JTextArea();
		this.add(e_text);
		e_text.setEditable(false);
		e_text.setLineWrap(true);
		e_scroll = new JScrollPane(e_text);
		this.add(e_scroll);
		e_scroll.setBounds(200,290,250,50);
		e_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		d_text = new JTextArea();
		this.add(d_text);
		d_text.setEditable(false);
		d_text.setLineWrap(true);
		d_scroll = new JScrollPane(d_text);
		this.add(d_scroll);
		d_scroll.setBounds(200,360,250,50);
		d_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == numbers)
		{
			int size = 0;
			
			if(box.getSelectedIndex() == 0) size = 8;
			else if(box.getSelectedIndex() == 1) size = 16;
			else if(box.getSelectedIndex() == 2) size = 32;
			else if(box.getSelectedIndex() == 3) size = 64;
			else if(box.getSelectedIndex() == 4) size = 128;
			else if(box.getSelectedIndex() == 5) size = 256;
			else if(box.getSelectedIndex() == 6) size = 512;
			else if(box.getSelectedIndex() == 7) size = 1024;
			else if(box.getSelectedIndex() == 8) size = 2048;
			
			BigInteger p_number;
			BigInteger q_number;
			BigInteger n_number;
			BigInteger e_number;
			BigInteger d_number;
			
			do
			{
				p_number = rsa.GetProbablePrime(size);
				q_number = rsa.GetProbablePrime(size);
			}while(!(p.equals(q)) && !(rsa.IsPrime(p_number)) && !(rsa.IsPrime(q_number)) );
			
			n_number = p_number.multiply(q_number);
			e_number = rsa.GetPublicKey(p_number,q_number)[1];
			d_number = rsa.GetPrivateKey(p_number,q_number,e_number)[1];
			
			p_text.setText(p_number.toString());
			q_text.setText(q_number.toString());
			n_text.setText(n_number.toString());
			e_text.setText(e_number.toString());
			d_text.setText(d_number.toString());
		}
		
		else if(event.getSource() == clear)
		{
			p_text.setText("");
			q_text.setText("");
			n_text.setText("");
			e_text.setText("");
			d_text.setText("");
		}
	}
}