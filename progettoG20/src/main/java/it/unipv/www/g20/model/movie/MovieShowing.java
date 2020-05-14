package it.unipv.www.g20.model.movie;

import java.util.Date;

import it.unipv.www.g20.model.theatre.Theatre;

/**
 * This class is referred to a movie projection in the cinema.
 */
public class MovieShowing {
	private String id;
	private static int intId=0;
	private Date date;
	private Availability availability;
	private Theatre theatre;
	private double price;

	public MovieShowing(Date date, Theatre theatre, double price) {
		intId++;
		id="P"+intId;
		this.date=date;
		this.theatre=theatre;
		this.price=price;
		availability = new Availability(theatre.getSeatsList());
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

	public Availability getAvailability() {
		return availability;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return getId() + " - " + date.toLocaleString() + " - " + theatre.getName() + " - €" + price;
	}


}
