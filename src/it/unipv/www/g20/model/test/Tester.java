package it.unipv.www.g20.model.test;

import java.text.ParseException;

import javax.swing.plaf.TextUI;

import it.unipv.www.g20.model.TextUI.AdminTextUI;
import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.exception.SearchException;

public class Tester {

	public static void main(String[] args) {
		Cinema test = new Cinema("prova");
		AdminTextUI c = new AdminTextUI(test);
		
		c.start();
	}

}
