package it.unipv.www.g20.model.movie;

import java.util.Date;
import java.util.HashMap;

import it.unipv.www.g20.model.theatre.Seat;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * This class is referred to a movie projection in the cinema.
 */
public class MovieShowing {
	private String id;
	private static int intId=0;
	private Date date;
	private Theatre theatre;
	private double price;
	private HashMap <Seat,Boolean> availability;

	public MovieShowing(Date date, Theatre theatre, double price) {
		availability=new HashMap<>();
		intId++;
		id="P"+intId;
		this.date=date;
		this.theatre=theatre;
		this.price=price;
		genAvailabilityList();
	}

	private void genAvailabilityList() {
		HashMap<String,Seat> seats = theatre.getSeatsList();
		for(String s : theatre.getKeySeats()) {
			availability.put(seats.get(s), true);
		}
	}

	public String printSeats() {
		String string="";
		string+="There are "+theatre.getRow()+" lines of "+theatre.getCol()+" seats\n";
		for(Seat s : availability.keySet()) {
			string+=s.toString()+" - "+availability.get(s)+"\n";
		}
		return string;
	}

	public boolean searchAvailability(String seat) {
		return availability.get(theatre.searchSeat(seat));
	}

	public boolean changeAvailability(String seat, boolean value) {
		return !availability.replace(theatre.searchSeat(seat), value);
	}

	public String getId() {
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

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return getId() + " - " + date.toLocaleString() + " - " + theatre.getName() + " - €" + price;
	}


}
