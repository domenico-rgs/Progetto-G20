package it.unipv.www.g20.model.test;

import java.io.IOException;
import java.util.Date;

import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.exception.SeatException;
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

			try {
				cin1.createTheatre("theatre1", "theatre1.txt");
				cin1.createTheatre("theatre2", "theatre2.txt");
				cin1.createTheatre("theatre3", "theatre3.txt");
				cin1.createTheatre("theatre4", "theatre4.txt");
			} catch (IOException | SeatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

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
