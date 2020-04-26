package it.unipv.www.g20.model.movie;

import java.util.Date;
import java.util.HashMap;

import it.unipv.www.g20.model.exception.NotAvailableException;
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
		intId++;
		this.id="P"+intId;
		this.date=date;
		this.theatre=theatre;
		this.price=price;
		availability=new HashMap<>();
	}

	/*
	 * Create the seats in the room by associating them with an id consisting of a
	 * letter that identifies the row and a number that identifies the column
	 */

}
