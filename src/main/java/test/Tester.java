package test;

import java.util.Date;
import java.util.Scanner;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.movie.Movie;
import server.domain.movie.MovieShowing;
import server.domain.ticket.Ticket;

public class Tester {

	@SuppressWarnings({ "deprecation"})
	public static void main(String[] args) throws Exception {
		try {
			Cinema cin1 = new Cinema("Uci");

			cin1.addMovie("Harry Potter and the order of fenix", 120);
			cin1.addMovie("The big short", 95);
			cin1.addMovie("The great Gatsby", 115);
			cin1.addMovie("The place", 200);

			cin1.createTheatre("theatre1", "fileTest/configTheatre/theatre1");
			cin1.createTheatre("theatre2", "fileTest/configTheatre/theatre2");
			cin1.createTheatre("theatre3", "fileTest/configTheatre/theatre3");
			cin1.createTheatre("theatre4", "fileTest/configTheatre/theatre4");

			cin1.searchMovie("The big short").addMovieShowing(new Date(120, 3, 27, 18,00), cin1.searchTheatre("theatre1"), 4.6);
			cin1.searchMovie("The big short").addMovieShowing(new Date(120, 4, 01, 21,35),cin1.searchTheatre("theatre2"), 4.6);
			cin1.searchMovie("The big short").addMovieShowing(new Date(120, 4, 05,19,00), cin1.searchTheatre("theatre4"), 5.8);
			cin1.searchMovie("The great Gatsby").addMovieShowing(new Date(120,9,10,17,30), cin1.searchTheatre("theatre3"), 6.6);

			System.out.println(cin1.printMovies());

			Movie m = cin1.searchMovie("The big short");
			System.out.println(m.printMovieShowing());
			MovieShowing ms= m.searchShowing("P1");
			System.out.println(ms.getAvailability().printSeats());
			Ticket t = cin1.bookSeat(m, "A1", "P1",320,"code","pin","cvc");
			System.out.println(t);

			System.out.println("\n");
			Scanner input = new Scanner(System.in);
			System.out.println("Inserisci codice biglietto da eliminare: ");
			String s = input.nextLine();
			System.out.println(cin1.deleteBooking((s)));

			System.out.println(cin1.deleteMovie("The big short"));
			System.out.println(cin1.deleteTheatre("theatre3"));

			System.out.println(cin1.printMovies());
		} catch(SearchException e) {
			System.out.println(e.getMessage());
		}
	}
}
