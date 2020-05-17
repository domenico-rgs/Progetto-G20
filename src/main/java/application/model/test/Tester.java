package application.model.test;

import java.util.Date;
import java.util.Scanner;

import application.model.cinema.Cinema;
import application.model.exception.SearchException;
import application.model.movie.Movie;
import application.model.movie.MovieShowing;
import application.model.ticket.Ticket;

public class Tester {

	@SuppressWarnings({ "deprecation"})
	public static void main(String[] args) throws Exception {
		try {
			Cinema cin1 = new Cinema("Uci");

			cin1.addMovie("Harry Potter and the order of fenix", 120);
			cin1.addMovie("The big short", 95);
			cin1.addMovie("The great Gatsby", 115);
			cin1.addMovie("The place", 200);

			cin1.createTheatre("theatre1", "theatre1");
			cin1.createTheatre("theatre2", "theatre2");
			cin1.createTheatre("theatre3", "theatre3");
			cin1.createTheatre("theatre4", "theatre4");

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

		/* da qui in giu test jetty */
		/*
		DriverManager.registerDriver((Driver) Class.forName("com.mysql.cy.jdbc.Driver").newInstance());

		String url = "jdbc:mysql://localhosy:3306?user=root&password=root";

		try(Connection conn = DriverManager.getConnection(url);
				Statement stat = conn.createStatement();
				ResultSet rs = stat.executeQuery("SELECT 1") {
			while(rs.next()){
				syso("result = " +rs.getInt(1))
			}
		}

		altro test
		 */
		/* fine test jetty */
	}
}
