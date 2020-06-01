package server.domain.cinema;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.movie.MovieShowing;
import server.domain.movie.Scheduling;
import server.domain.movie.TypeCategory;
import server.domain.payment.SimPaymentAdapter;
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
    private DBConnection db;


	public Cinema() {
		theatreList=new HashMap<>();
		movieCatalog=new HashMap<>();
		scheduler = new HashMap<>();
		payment= new SimPaymentAdapter();
        db = new DBConnection();
	}
	
	public boolean createTheatre(String name, String file) throws SearchException, IOException, SeatException {
		if (theatreList.containsKey(name))
			throw new SearchException(name+" already exists.");
		else {
			theatreList.put(name, new Theatre(name, file));
			return true;
		}
	}
	
	//OCCORRE CONTROLLARE CHE NON SIA USATO
	public boolean deleteTheatre(String name) throws SearchException{
		if (!(theatreList.containsKey(name)))
			throw new SearchException(name+"'s not found.");
		else {
			theatreList.remove(name);
			return true;
		}
	}
	
	public String printTheatreConfig(String thName) throws IOException {
		return theatreList.get(thName).printConfiguration();
	}
	
	public Theatre searchTheatre(String thName) throws SearchException{
		if(!(theatreList.containsKey(thName)))
			throw new SearchException(thName+"'s not found");
		else
			return theatreList.get(thName);
	}

	public boolean createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException{
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
	public boolean deleteMovie(String title) throws SearchException{
		if (!(movieCatalog.containsKey(title)))
			throw new SearchException(title+"'s not found.");
		else {
			movieCatalog.remove(title);
			return true;
		}
	}

	public Movie searchMovie(String title) throws SearchException{
		if(!(movieCatalog.containsKey(title)))
			throw new SearchException(title+"'s not found");
		else
			return movieCatalog.get(title);
	}
	
	public void createMovieShowing(String movie, Date date, String theatre, double price) {
		//PROBABILMENTE OCCORRE AGGIUNGERE QUALCOSA PER CONTROLLARE CHE LE DATE NON SI ACCAVALLINO
		scheduler.get(movieCatalog.get(movie)).createMovieShowing(date, theatreList.get(theatre), price);
	}
	
	//OCCORRE CONTROLLARE CHE NON SIANO STATE FATTE PRENOTAZIONI PER QUESTA PROIEZIONE
	public void deleteMovieShowing(String movie, String idShowing) throws SearchException {
		scheduler.get(movieCatalog.get(movie)).deleteMovieShowing(idShowing);
	}


	public boolean bookSeat(String movie, String initSeat, String finalSeat, String movieShowing) throws SeatException{
		return scheduler.get(movieCatalog.get(movie)).changeAvailability(movieShowing, initSeat, finalSeat, false);
	}
	
	public boolean bookSeat(String movie, String seat, String movieShowing) throws SeatException{
		return scheduler.get(movieCatalog.get(movie)).changeAvailability(movieShowing, seat, seat, false);
	}
	
	public boolean pay(double money, String seat, String movieShowing) {
		return false;
		//TO-DO
	}

	public List<Ticket> createTicket(Movie movie, MovieShowing movieShowing, String discount, String...seat) throws SearchException {
		List<Ticket> t = new ArrayList<>();
		for(String s : seat) {
			double price = totalPrice(movieShowing, discount, s);
			Ticket ticket = new Ticket(movie.getTitle(), s, movieShowing.toString(), price);
			t.add(ticket);
			TicketLedger.getTicketLedger().addTicketSale(ticket);
			//addAuctionDB(movie, seat, movieShowing, price);
		}
		return t;
	}
	
	public double totalPrice(MovieShowing movieShowing, String discount, String...seat) {
		double price = movieShowing.getPrice();
		//TO-DO
		return price;
	}
	
	//Se lancia l'eccezione ne cancella solo una parte
	public boolean deleteBooking(String... code) throws SearchException{
		for(String c : code) {
			TicketLedger.getTicketLedger().removeTicketSale(c);
		}				
		return true;
	}
	
	/* Test DB Ticket
	public void addAuctionDB(Movie movie, String seat, MovieShowing movieShowing, double price) {
	        db.addTicket(movie, seat, movieShowing, price);
	}
	*/

}
