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


public class Cinema implements Manageable{
	private final String name;
	private final String address;

	private final HashMap<String, Theatre>theatreList;
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

	/* Ritorna vero se inserisce correttamente il teatro
	 * altrimeti ritorna falso poich� gia esistente
	 * il medesimo ragionemanto vale anche per le classi sottostanti
	 */
	@Override
	public boolean addTheatre(String name, int row, int column) {
		if (theatreList.containsKey(name)) 
			return false;

		theatreList.put(name, new Theatre(name, row, column));
		return true;
	}

	@Override
	public boolean deleteTheatre(String name) {

		if(!(theatreList.containsKey(name))) 
			return false;

		theatreList.remove(name);
		return true;
	}

	@Override
	public boolean addMovie(String title, int duration, TypeCategory category, Double ticketPrice) {

		if(movieList.containsKey(title)) 
			return false;

		movieList.put(title, new Movie(title, duration, category, ticketPrice));
		return true;

	}

	@Override
	public boolean deleteMovie(String title) {

		if(!(movieList.containsKey(title))) 
			return false;

		movieList.remove(title);
		return true;
	}

	@Override
	public Booking addBooking(Calendar date) {

		//lo creo per ricavarne l'id generato automaticamente
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

	public Operator addOperator(TypeOperator type) {

		Operator tmp = null;

		switch(type) {
		case CASHIER:
			tmp = new Cashier();
			break;
		case MANAGER:
			tmp = new Manager();
		}

		operatorList.put(tmp.getId(), tmp);
		return tmp;

	}


	public boolean deleteOperator(int id) {

		if(!(operatorList.containsKey(id))) 
			return false;

		operatorList.remove(id);
		return true;

	}
	
	public Operator searchOperator(int id) {
		if(!(operatorList.containsKey(id))) 
			return null;

		return operatorList.get(id);	
	}
	
	public Theatre searchTheatre(String name) {
		if(!(theatreList.containsKey(name))) 
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
		return "Cinema:\n[name=" + name + "\n address=" + address + "\n theaterList=" + theatreList + "\n movieList="
				+ movieList + "\n bookingList=" + bookingList + "\n operatorList=" + operatorList + "]";
	}

}
