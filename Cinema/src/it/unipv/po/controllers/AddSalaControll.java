package it.unipv.po.controllers;

import it.unipv.po.application.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddSalaControll {

    @FXML
    private TextField nameLabel, openLabel, closeLabel, capacityLabel;

    @FXML
    private Label InfText;

    @FXML
    private Button confirmButton, backButton;
    
    
    /* Al click del bottone aggiungi, tramite blocco try catch si verifica la 
     * correttezza dei dati aggiunti, ed in caso contrario ritorna un errore in label.
     * Se tutto va a buon fine si aggiunge la sala al cinema ritornando
     * un messaggio di conferma
     */
    @FXML
    public void addTheaterClick(ActionEvent event) {
    	
    	String name;
    	int openHour;
    	int closeHour;
    	int capienza;
    	
    	try {
    		
    		name = nameLabel.getText();
    		openHour = Integer.parseInt(openLabel.getText());
    		closeHour = Integer.parseInt(closeLabel.getText());
    		capienza = Integer.parseInt(capacityLabel.getText());	
    	}
    	catch (Exception e) {
    		InfText.setText("Argomenti inseriti non corretti");
    		InfText.setTextFill(Color.RED);
    		return;
    	}
    	
    	/* Il controllo if serve per controolare se il nome della sala che si vuole
    	 * aggiungere sia gia presente nella lista, senno continua semplicemente con l'aggiunta
    	 * della sala e l'aggiornamento della checkbox
    	 */
    	if(!(Main.myCinema.addTheater(name, openHour, closeHour, capienza))) {
    			
    		InfText.setText("Sala già presente");
    		InfText.setTextFill(Color.RED);
    		return;
    	}
    	
    	//funzione che richiama metodo statico in un altro metodo
    	InfText.setText("Aggiunto con successo");
		InfText.setTextFill(Color.BLACK);
		return;
    	
    }
    
    
    @FXML
    public void backClick() {
    	Main.addSalaStage.close();
    }
    
}

