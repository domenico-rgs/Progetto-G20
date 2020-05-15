package application.model.cinema;

import java.io.IOException;
import java.util.HashMap;

import application.model.exception.PaymentException;
import application.model.exception.SearchException;
import application.model.exception.SeatException;
import application.model.movie.Movie;
import application.model.movie.MovieShowing;
import application.model.payment.SimPaymentAdapter;
import application.model.theatre.Theatre;
import application.model.ticket.Ticket;
import application.model.ticket.TicketLedger;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private String name;
	private HashMap <String,Theatre> theatreList;
	private HashMap <String, Movie> movieCatalog;
	private SimPaymentAdapter payment;


	public Cinema(String name) {
		this.name = name;
		theatreList=new HashMap<>();
		movieCatalog=new HashMap<>();
		payment= new SimPaymentAdapter();
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
		if(TicketLedger.getTicketLedger().addTicketSale(tmp)!=null)
			return tmp;
		return null;
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
