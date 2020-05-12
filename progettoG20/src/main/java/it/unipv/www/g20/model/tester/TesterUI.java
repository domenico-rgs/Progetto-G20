package it.unipv.www.g20.model.tester;

import java.util.Date;

import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.view.TextUI.AdminTextUI;

public class TesterUI {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Cinema cin1 = new Cinema("Uci Cinema");

		try {
			cin1.addMovie("The big short", 95);
			cin1.addMovie("Harry Potter and the order of fenix", 120);
			cin1.addMovie("The great Gatsby", 115);
			cin1.addMovie("The place", 200);

			cin1.createTheatre("theatre1", 10, 10);
			cin1.createTheatre("theatre2", 5, 4);
			cin1.createTheatre("theatre3", 3, 30);
			cin1.createTheatre("theatre4", 15, 5);

			cin1.searchMovie("The big short").addMovieShowing(new Date(120, 3, 27, 18,00), cin1.searchTheatre("theatre1"), 4.6);
			cin1.searchMovie("The big short").addMovieShowing(new Date(120, 4, 01, 21,35),cin1.searchTheatre("theatre2"), 4.6);
			cin1.searchMovie("The big short").addMovieShowing(new Date(120, 4, 05,19,00), cin1.searchTheatre("theatre4"), 5.8);
			cin1.searchMovie("The great Gatsby").addMovieShowing(new Date(120,9,10,17,30), cin1.searchTheatre("theatre3"), 6.6);
		} catch (SearchException e) {
			e.printStackTrace();
		}
		AdminTextUI testUI = new AdminTextUI(cin1);

		testUI.start();
	}
}
