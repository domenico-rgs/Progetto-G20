package it.unipv.www.g20.model.movie;

import java.util.Date;
import java.util.HashMap;

import it.unipv.www.g20.model.theatre.Seat;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * This class is referred to a movie projection in the cinema.
 */
public class MovieShowing {
	private static String id;
	private static int intId=0;
	private Date date;
	private Theatre theatre;
	private double price;
	private HashMap <Seat,Boolean> availability;

	public MovieShowing(Date date, Theatre theatre, double price) {
		availability=new HashMap<>();
		genAvailabilityList();
		intId++;
		id="P"+intId;
		this.date=date;
		this.theatre=theatre;
		this.price=price;
	}

	private void genAvailabilityList() {
		HashMap<String,Seat> seats = theatre.getSeatsList();
		for(String s : theatre.getKeySeats()) {
			availability.put(seats.get(s), true);
		}
	}

	public String printSeats() {
		String string="";
		for(Seat s : availability.keySet()) {
			string+=s.toString()+availability.get(s);
		}
		return string;
	}

	public boolean searchAvailability(Seat seat) {
		return availability.get(seat);
	}

	public boolean changeAvailability(Seat seat, boolean value) {
		return !availability.replace(seat, value);
	}

	public static String getId() {
		return id;
	}

	public static int getIntId() {
		return intId;
	}

	public Date getDate() {
		return date;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public double getPrice() {
		return price;
	}
}
