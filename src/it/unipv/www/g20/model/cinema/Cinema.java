/**This class identifies a cinema.*/
package it.unipv.www.g20.model.cinema;

import java.util.Calendar;
import java.util.HashMap;

import it.unipv.www.g20.model.booking.Booking;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.TypeCategory;
import it.unipv.www.g20.model.operator.Cashier;
import it.unipv.www.g20.model.operator.Manager;
import it.unipv.www.g20.model.operator.Operator;
import it.unipv.www.g20.model.operator.TypeOperator;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema implements Manageable {
	private final String name;
	private final String address;

	private final HashMap<String, Theatre> theatreList;
	private final HashMap<String, Movie> movieList;
	private final HashMap<String, Booking> bookingList;
	private final HashMap<Integer, Operator> operatorList;

	public Cinema(String name, String address) {
		this.address = address;
		this.name = name;

		theatreList = new HashMap<String, Theatre>();
		movieList = new HashMap<String, Movie>();
		operatorList = new HashMap<Integer, Operator>();
		bookingList = new HashMap<String, Booking>();
	}

	@Override
	public boolean addTheatre(String name, int row, int column) {
		if (theatreList.containsKey(name))
			return false;

		theatreList.put(name, new Theatre(name, row, column));
		return true;
	}

	@Override
	public boolean deleteTheatre(String name) {

		if (!(theatreList.containsKey(name)))
			return false;

		theatreList.remove(name);
		return true;
	}

	@Override
	public boolean addMovie(String title, int duration, TypeCategory category, Double ticketPrice) {

		if (movieList.containsKey(title))
			return false;

		movieList.put(title, new Movie(title, duration, category, ticketPrice));
		return true;

	}

	@Override
	public boolean deleteMovie(String title) {

		if (!(movieList.containsKey(title)))
			return false;

		movieList.remove(title);
		return true;
	}

	@Override
	public Booking addBooking(Calendar date) {

		final Booking tmp = new Booking(date);

		bookingList.put(tmp.getIdBooking(), tmp);
		return tmp;
	}

	@Override
	public boolean deleteBooking(String id) {

		if (!(bookingList.containsKey(id)))
			return false;

		bookingList.remove(id);
		return true;
	}

	@Override
	public Operator addOperator(TypeOperator type) {

		Operator tmp;

		switch (type) {
		case CASHIER:
			tmp = new Cashier();
			break;
		case MANAGER:
			tmp = new Manager();
			break;
		default:
			return null;
		}

		operatorList.put(tmp.getId(), tmp);
		return tmp;

	}

	@Override
	public boolean deleteOperator(int id) {

		if (!(operatorList.containsKey(id)))
			return false;

		operatorList.remove(id);
		return true;

	}

	/**
	 * Search for the operator in the list of all operators added to the cinema
	 * @param id operator's id
	 * @return the operator otherwise null if the operator is not present in the list
	 */
	public Operator searchOperator(int id) {
		if (!(operatorList.containsKey(id)))
			return null;

		return operatorList.get(id);
	}

	/**
	 * Search the cinema hall in the list of all theaters added to the cinema
	 * @param name
	 * @return
	 */
	public Theatre searchTheatre(String name) {
		if (!(theatreList.containsKey(name)))
			return null;

		return theatreList.get(name);
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Cinema: " + name + ", address=" + address +"\n";
	}

}
