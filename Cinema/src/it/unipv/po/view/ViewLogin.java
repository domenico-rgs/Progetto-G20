package it.unipv.po.view;

/*INTERFACCIA BASILARE NON PER IL PROGETTO MA SOLO PER FARE ALCUNI TEST L'INTERFACCIA VERA E' QUELLA CHE STA FACENDO ANDREA CON JAVAFX */


import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javafx.scene.control.PasswordField;

public class ViewLogin extends JFrame{

	
	//attributi per l-interfaccia
	private JTextField username;
	private JPasswordField password;
	private JLabel labelUsername, labelPassword, labelEmpty;
	private JPanel panelCentral;
	private JButton buttonConferma;
	//costanti
	private final static int HEIGHT =400;
	private final static int WIDTH =500;
	
	
	public ViewLogin() {
		//inizializzo la finestra
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setTitle("Login Cinema");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		
		
		// inizializzo componenti per inserire il testo
		username = new JTextField();
		password = new JPasswordField();
		labelPassword = new JLabel("Password");
		labelUsername = new JLabel("Username");
		labelEmpty = new JLabel("");
		buttonConferma = new JButton("Conferma");
		
		//creazione primo pannello
		panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(3,2));
		panelCentral.setBounds(100,100,250,80);
		
		//aggiunta elementi al pannello
		panelCentral.add(labelUsername);
		panelCentral.add(username);
		panelCentral.add(labelPassword);
		panelCentral.add(password);
		panelCentral.add(labelEmpty);
		panelCentral.add(buttonConferma);
		
		//aggiunta pannello alla finestra principale

		
		add(panelCentral, BorderLayout.CENTER);
	
		validate();
		
		
	}
	
	/* getter e setter utili per il controller 
	*/
	
	public JTextField getUsername() {
		return username;
	}


	public void setUsername(JTextField username) {
		this.username = username;
	}


	public JTextField getPassword() {
		return password;
	}



	public JButton getButtonConferma() {
		return buttonConferma;
	}


	public void setButtonConferma(JButton buttonConferma) {
		this.buttonConferma = buttonConferma;
	}
	
	
	
	//metodi per il controller
	
}
