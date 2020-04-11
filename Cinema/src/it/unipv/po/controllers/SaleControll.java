package it.unipv.po.controllers;

import it.unipv.po.application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class SaleControll {
	
	@FXML
	private ChoiceBox<String> theaterList;
	
	@FXML
	private Button openAddButton, updateButton;
	
	@FXML
	private Label capienzaLabel, openLabel, closeLabel, freeSeatsLabel, salaName;
	
	
	@FXML
	public void openAddSalaList(ActionEvent event) {
		Main.addSalaStage.show();
	}
	
	
	@FXML
	public void updateTheaterList() {
		String[] theaterNameList = Main.myCinema.getNameTheaterList();
	    	
	    theaterList.getItems().clear();
	    theaterList.getItems().addAll(theaterNameList);
	}
	
	@FXML
	public void updateInformation() {
		String name = theaterList.getSelectionModel().getSelectedItem();
		
		if (name == null) return;
		
		capienzaLabel.setText("Capienza: " + Main.myCinema.getTheater(name).getCapacity());
		freeSeatsLabel.setText("Posti liberi: " + Main.myCinema.getTheater(name).getAvailabilitySeats());
		openLabel.setText("Orario apertura: " + Main.myCinema.getTheater(name).getTheaterOpeningHours());
		closeLabel.setText("Orario chiusura: " + Main.myCinema.getTheater(name).getTheaterClosingHours());
		salaName.setText(Main.myCinema.getTheater(name).getTheaterName());
	}
}
