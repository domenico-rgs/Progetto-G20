package server.test;

import java.io.IOException;
import java.util.Date;

//import client.TextUI.AdminTextUI;
import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;



/* CLASSE TEST DA RISCRIVERE */


public class TesterUI {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//Cinema cin1 = new Cinema("Uci Cinema");

		/*try {
			
			
			cin1.addMovie("The big short", 95);
			cin1.addMovie("Harry Potter and the order of fenix", 120);
			cin1.addMovie("The great Gatsby", 115);
			cin1.addMovie("The place", 200);
			
			try {
				
				cin1.createTheatre("theatre1", "fileTest/configTheatre/theatre1");
				cin1.createTheatre("theatre2", "fileTest/configTheatre/theatre2");
				cin1.createTheatre("theatre3", "fileTest/configTheatre/theatre3");
				cin1.createTheatre("theatre4", "fileTest/configTheatre/theatre4");
				
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
		//AdminTextUI testUI = new AdminTextUI(cin1);

		//testUI.start();  
		 */
	}
}
