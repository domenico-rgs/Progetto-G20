package it.unipv.www.g20.model.tester;

import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.view.TextUI.AdminTextUI;

public class TesterUI {

	public static void main(String[] args) {
		AdminTextUI prova = new AdminTextUI(new Cinema("prova"));

		prova.start();
	}
}
