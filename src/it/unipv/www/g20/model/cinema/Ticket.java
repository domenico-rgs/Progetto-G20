package it.unipv.www.g20.model.cinema;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.unipv.www.g20.model.movie.MovieShowing;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * This class identifies a bought ticket to show a movie.
 */
public class Ticket {
	private final String code;
	private final String idMovie;
	private final double price;
	private final Date date;
	private final String theatre;

	public Ticket(String code, Theatre theatre, MovieShowing projection) {
		this.code = code;
		idMovie = projection.getMovie().getTitle();
		price = projection.getStandardPrice();
		date = projection.getDate();
		this.theatre = theatre.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Ticket other = (Ticket) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Id: " + code + "\nDate: " + getDate() + "\nTheatre: " + theatre + " - Film: " + idMovie + "\nPrice: € "
				+ price;
	}
	
	/*
	 * Used to get a Date given a String in the form dd/MM/yyyy HH:mm
	 */
	private String getDate() {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ' - ' HH:mm");
		return simpleDateFormat.format(date).toString();
	}

}
