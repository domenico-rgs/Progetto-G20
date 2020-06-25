package server.domain.cinema;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.SimPaymentAdapter;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;
import server.services.DB.DBConnection;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private SimPaymentAdapter payment;
	private Quotes quotes = new Quotes();

	private DBConnection db;
	private static Cinema istance = null;

	private Cinema() {
		payment= new SimPaymentAdapter();
		db = new DBConnection();
	}
	
	synchronized public boolean createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException{
			db.addMovie(title, duration, plot, pathCover, category);
			return true;
	}

	synchronized public boolean createTheatre(String name, String config) throws IOException, SeatException {
			Theatre t = new Theatre(name, config);
			db.addTheatre(t);
			return true;
	}

	public void editShowing(String showing, String theatre, double price) throws SearchException {
			db.editShowing(showing, searchTheatre(theatre), price);
	}

	//OCCORRE CONTROLLARE CHE NON SIA USATO
	/*synchronized public boolean deleteTheatre(String name) throws SearchException{
		if (!(theatreList.containsKey(name)))
			throw new SearchException(name+"'s not found.");
		else {
			theatreList.remove(name);
			return true;
		}
	}*/

	synchronized public Theatre searchTheatre(String theatreName){
		return db.searchTheatre(theatreName);
	}

	//OCCORRE CONTROLLARE CHE NON SIA USATO
	/*synchronized public boolean deleteMovie(String title) throws SearchException{
		if (!(movieCatalog.containsKey(title)))
			throw new SearchException(title+"'s not found.");
		else {
			movieCatalog.remove(title);
			return true;
		}
	}*/

	synchronized public Movie searchMovie(String title){
		return db.searchMovie(title);
	}

	public MovieShowing searchShowing(String id){
		return db.searchShowing(id);
	}

	synchronized public void createMovieShowing(String movie, LocalDateTime localDateTime, String theatre, double price) {
		//PROBABILMENTE OCCORRE AGGIUNGERE QUALCOSA PER CONTROLLARE CHE LE DATE NON SI ACCAVALLINO
		MovieShowing show = new MovieShowing(localDateTime, searchTheatre(theatre), price);
		db.addMovieShowing(show);
	}

	//OCCORRE CONTROLLARE CHE NON SIANO STATE FATTE PRENOTAZIONI PER QUESTA PROIEZIONE
	/* synchronized public void deleteMovieShowing(String movie, String idShowing) throws SearchException {
		scheduler.get(movieCatalog.get(movie)).deleteMovieShowing(idShowing);
	}*/

	//Se lancia l'eccezione ne cancella solo una parte, rivedere dopo aver deciso cosa fare del ledger
	synchronized public boolean deleteBooking(String string) throws SearchException{
		TicketLedger.getTicketLedger().removeTicketSale(string);
		return true;
	}

	//AGGIUNGERE LA VENDITA DEI TICKET

	public boolean pay(double money, String seat, String movieShowing) {
		return false;
		//TO-DO
	}

	public List<String> getQuotes() {
		return quotes.getQuotes();
	}

	//metodi per amministrazione applicazione web
	public ArrayList<MovieShowing> getShowingList(Movie movie) {
		return db.getShowingList(movie);
	}


	public List<String> getTitleMovieList() {
		return db.movieList();
	}

	public static Cinema getCinema() {
		if (istance == null)
			istance = new Cinema();
		return istance;
	}
}
