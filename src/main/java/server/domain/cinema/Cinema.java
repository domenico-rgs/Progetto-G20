package server.domain.cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.SimPaymentAdapter;
import server.domain.showing.MovieShowing;
import server.domain.showing.Scheduling;
import server.domain.theatre.Theatre;
import server.services.DB.DBConnection;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private HashMap <String,Theatre> theatreList;
	private HashMap <String, Movie> movieCatalog;
	private HashMap <Movie, Scheduling> scheduler;

	private SimPaymentAdapter payment;
	private Quotes quotes = new Quotes();

	private DBConnection db;
	private static Cinema istance = null;

	private Cinema() {
		theatreList=new HashMap<>();
		movieCatalog=new HashMap<>();
		scheduler = new HashMap<>();
		payment= new SimPaymentAdapter();
		//db = new DBConnection();
	}

	synchronized public boolean createTheatre(String name, String file) throws SearchException, IOException, SeatException {
		if (theatreList.containsKey(name))
			throw new SearchException(name+" already exists.");
		else {
			theatreList.put(name, new Theatre(name, file));
			return true;
		}
	}

	//OCCORRE CONTROLLARE CHE NON SIA USATO
	synchronized public boolean deleteTheatre(String name) throws SearchException{
		if (!(theatreList.containsKey(name)))
			throw new SearchException(name+"'s not found.");
		else {
			theatreList.remove(name);
			return true;
		}
	}

	synchronized public Theatre searchTheatre(String thName) throws SearchException{
		if(!(theatreList.containsKey(thName)))
			throw new SearchException(thName+"'s not found");
		else
			return theatreList.get(thName);
	}

	synchronized public boolean createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException{
		if (movieCatalog.containsKey(title))
			throw new SearchException(title+" already exists.");
		else {
			Movie m = new Movie(title, duration, plot, pathCover, category);
			movieCatalog.put(title, m);
			scheduler.put(m, new Scheduling());
			return true;
		}

	}

	//OCCORRE CONTROLLARE CHE NON SIA USATO
	synchronized public boolean deleteMovie(String title) throws SearchException{
		if (!(movieCatalog.containsKey(title)))
			throw new SearchException(title+"'s not found.");
		else {
			movieCatalog.remove(title);
			return true;
		}
	}

	synchronized public Movie searchMovie(String title) throws SearchException{
		if(!(movieCatalog.containsKey(title)))
			throw new SearchException(title+"'s not found");
		else
			return movieCatalog.get(title);
	}

	public List<MovieShowing> getSchedule(Movie movie) throws SearchException{
		return scheduler.get(movie).getShowingList();
	}

	synchronized public void createMovieShowing(String movie, Date date, String theatre, double price) {
		//PROBABILMENTE OCCORRE AGGIUNGERE QUALCOSA PER CONTROLLARE CHE LE DATE NON SI ACCAVALLINO
		scheduler.get(movieCatalog.get(movie)).createMovieShowing(date, theatreList.get(theatre), price);
	}

	//OCCORRE CONTROLLARE CHE NON SIANO STATE FATTE PRENOTAZIONI PER QUESTA PROIEZIONE
	synchronized public void deleteMovieShowing(String movie, String idShowing) throws SearchException {
		scheduler.get(movieCatalog.get(movie)).deleteMovieShowing(idShowing);
	}

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

	public List<String> getTitleMovieList() {
		List<String> titleList = new ArrayList<>();
		titleList.addAll(movieCatalog.keySet());

		return titleList;
	}

	public List<String> getQuotes() {
		return quotes.getQuotes();
	}

	public static Cinema getCinema() {
		if (istance == null)
			istance = new Cinema();
		return istance;
	}
}
