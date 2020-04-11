package it.unipv.po.controllers;
import it.unipv.po.application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class RootControll {

    @FXML
    private MenuItem menuSale;

    @FXML
    private MenuItem menuFilm;

    @FXML
    void menuFilmClick(ActionEvent event) {
    	Main.rootView.setCenter(Main.filmView);
    }

    @FXML
    void menuSaleClick(ActionEvent event) {
    	Main.rootView.setCenter(Main.saleView);
    }

}

