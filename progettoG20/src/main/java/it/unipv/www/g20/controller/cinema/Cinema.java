package it.unipv.www.g20.controller.cinema;

import java.util.HashMap;

import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;
import it.unipv.www.g20.model.theatre.Theatre;
import it.unipv.www.g20.model.ticket.Ticket;
import it.unipv.www.g20.model.ticket.TicketLedger;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private String name;
	private HashMap <String,Theatre> theatreList;
	private HashMap <String, Movie> movieCatalog;


	public Cinema(String name) {
		this.name = name;
		theatreList=new HashMap<String, Theatre>();
		movieCatalog=new HashMap<String, Movie>();
	}

	public boolean addMovie(String title, int duration) throws SearchException{

		if (movieCatalog.containsKey(title))
			throw new SearchException(title+" already exists.");
		movieCatalog.put(title, new Movie(title, duration));
		return true;

	}

	public boolean createTheatre(String name, int row, int col) throws SearchException{
		if (theatreList.containsKey(name))
			throw new SearchException(name+" already exists.");

		theatreList.put(name, new Theatre(name, row, col));
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

	public Ticket bookSeat(Movie movie, String seat, String idShowing) throws SearchException {
		MovieShowing ms=movie.searchShowing(idShowing);
		if(ms.getAvailability().searchAvailability(seat)) {
			ms.getAvailability().changeAvailability(seat, false);
			return buyTicket(movie,seat, ms);
		}
		return null;
	}

	private Ticket buyTicket(Movie movie,String seat, MovieShowing movieShowing) throws SearchException {
		Ticket tmp = new Ticket(movie.getTitle(), seat, movieShowing);
		if(TicketLedger.getTicketLedger().addTicketSale(tmp)!=null)
			return tmp;
		return null;
	}

	public String printMovie() {
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
