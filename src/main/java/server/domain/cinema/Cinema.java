package server.domain.cinema;

import java.io.IOException;
import java.util.HashMap;

import server.domain.exception.PaymentException;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.movie.Movie;
import server.domain.movie.MovieShowing;
import server.domain.payment.SimPaymentAdapter;
import server.domain.theatre.Theatre;
import server.domain.ticket.Ticket;
import server.domain.ticket.TicketLedger;
import server.services.DB.DBConnection;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private String name;
	private HashMap <String,Theatre> theatreList;
	private HashMap <String, Movie> movieCatalog;
	private SimPaymentAdapter payment;
    private DBConnection db;


	public Cinema(String name) {
		this.name = name;
		theatreList=new HashMap<>();
		movieCatalog=new HashMap<>();
		payment= new SimPaymentAdapter();
        db = new DBConnection();
	}

	public boolean addMovie(String title, int duration) throws SearchException{

		if (movieCatalog.containsKey(title))
			throw new SearchException(title+" already exists.");
		movieCatalog.put(title, new Movie(title, duration));
		return true;

	}

	public boolean createTheatre(String name, String file) throws SearchException, IOException, SeatException{
		if (theatreList.containsKey(name))
			throw new SearchException(name+" already exists.");

		theatreList.put(name, new Theatre(name, file));
		return true;
	}

	public boolean deleteMovie(String title) throws SearchException{

		if (!(movieCatalog.containsKey(title)))
			throw new SearchException(title+"'s not found.");

		movieCatalog.remove(title);
		return true;
	}

	public boolean deleteTheatre(String name) throws SearchException{

		if (!(theatreList.containsKey(name)))
			throw new SearchException(name+"'s not found.");
		theatreList.remove(name);
		return true;
	}

	public Theatre searchTheatre(String name) throws SearchException{
		if(theatreList.get(name)==null)
			throw new SearchException(name+"'s not found");
		return theatreList.get(name);
	}

	public Movie searchMovie(String title) throws SearchException{
		if(movieCatalog.get(title)==null)
			throw new SearchException(title+"'s not found");
		return movieCatalog.get(title);
	}

	public String getName() {
		return name;
	}

	public boolean deleteBooking(String code) throws SearchException {
		if(TicketLedger.getTicketLedger().removeTicketSale(code)!=null)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Cinema: " + name;
	}

	public Ticket bookSeat(Movie movie, String seat, String idShowing, double money, String code, String pin, String cvc) throws SearchException, PaymentException {
		MovieShowing ms=movie.searchShowing(idShowing);
		if(!(payment.pay(money, code, pin, cvc)))
			throw new PaymentException("Pagamento non riuscito");
		if(ms.getAvailability().searchAvailability(seat)) {
			ms.getAvailability().changeAvailability(seat, false);
			return buyTicket(movie,seat, ms,ms.getPrice());
		}
		return null;
	}

	private Ticket buyTicket(Movie movie, String seat, MovieShowing movieShowing, double price) throws SearchException {
		Ticket tmp = new Ticket(movie.getTitle(), seat, movieShowing.toString(), price);
		addAuctionDB(movie, seat, movieShowing, price);
		if(TicketLedger.getTicketLedger().addTicketSale(tmp)!=null)
			return tmp;
		return null;
	}
	
	public void addAuctionDB(Movie movie, String seat, MovieShowing movieShowing, double price) {
	        db.addTicket(movie, seat, movieShowing, price);
	}

	public String printMovies() {
		String s="";
		for(String key : movieCatalog.keySet())
			s+=movieCatalog.get(key).toString()+"\n";
		return s;
	}

	public String printTheatres() {
		String s="";
		for(String key : theatreList.keySet())
			s+=theatreList.get(key).toString()+"\n";
		return s;
	}

}
