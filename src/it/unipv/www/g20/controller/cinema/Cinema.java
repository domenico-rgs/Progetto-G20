package it.unipv.www.g20.controller.cinema;

import java.util.HashMap;

import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;
import it.unipv.www.g20.model.theatre.Seat;
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
		theatreList=new HashMap<>();
		movieCatalog=new HashMap<>();
	}

	public boolean addMovie(String title, int duration) {

		if (movieCatalog.containsKey(title))
			return false;
		movieCatalog.put(title, new Movie(title, duration));
		return true;

	}

	public boolean createTheatre(String name, int row, int col){
		if (theatreList.containsKey(name))
			return false;

		theatreList.put(name, new Theatre(name, row, col));
		return true;
	}

	public boolean deleteMovie(String title){

		if (!(movieCatalog.containsKey(title)))
			return false;

		movieCatalog.remove(title);
		return true;
	}

	public boolean deleteTheatre(String name){

		if (!(theatreList.containsKey(name)))
			return false;
		theatreList.remove(name);
		return true;
	}

	public Theatre searchTheatre(String name) {
		return theatreList.get(name);
	}

	public Movie searchMovie(String title) {
		return movieCatalog.get(title);
	}

	public String getName() {
		return name;
	}

	public boolean deleteBooking(String code) {
		if(TicketLedger.getTicketLedger().removeTicketSale(code)!=null)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Cinema: " + name;
	}

	public boolean bookSeat(Movie movie, String seat, String idShowing) {
		MovieShowing ms=movie.searchShowing(idShowing);
		if(ms.searchAvailability(seat)) {
			ms.changeAvailability(seat, false);
			buyTicket(movie,seat, ms);
			return true;
		}
		return false;
	}

	private boolean buyTicket(Movie movie,String seat, MovieShowing movieShowing) {
		if(TicketLedger.getTicketLedger().addTicketSale(new Ticket(movie.getTitle(), seat, movieShowing))!=null)
			return false;
		return true;
	}
}
