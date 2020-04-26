package it.unipv.www.g20.model.cinema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import it.unipv.www.g20.model.movie.MovieShowing;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * This class identifies a bought ticket to show a movie.
 */
public class Ticket {
	private String code;
	private String movie;
	private String seat;
	private MovieShowing showing;

	public Ticket(String movie, String seat, MovieShowing showing) {
		this.code = UUID.randomUUID().toString();
		this.movie=movie;
		this.showing=showing;
		this.seat=seat;
	}

	@Override
	public String toString() {
		return "Ticket id: " + code + "\nDate: " + showing.getDate() + "\nTheatre: " + showing.getTheatre().getTheatreName() + " - Movie: " + movie + "\nPrice: € "
				+ showing.getPrice();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public String getCode() {
		return code;
	}

	public String getMovie() {
		return movie;
	}

	public String getSeat() {
		return seat;
	}

	public MovieShowing getShowing() {
		return showing;
	}
}
