package server.domain.movie;

import java.util.Date;

//import javax.persistence.*;    // importazione non richiesta

import server.domain.exception.SeatException;
import server.domain.theatre.Theatre;

/**
 * This class is referred to a movie projection in the cinema.
 */
//@Entity
//@Table(name="movieShowing")
public class MovieShowing {
	//@Id
	//@Column(name="id")
	private String id;
	//@Transient
	private static int intId=0;
	//@Column(name="dateMovieShowing")
	private Date date;
	//@Transient //da correggere
	private Availability availability;
	//@Column(name="price")
	private double price;
	private String theatreName;
	
	public MovieShowing() {}

	public MovieShowing(Date date, Theatre theatre, double price) {
		intId++;
		id="P"+intId;
		this.date=date;
		this.price=price;
		availability = new Availability(theatre.getSeatsList());
		theatreName=theatre.getTheatreName();
	}
	
	protected boolean searchAvailability(String seat) throws SeatException {
		return availability.searchAvailability(seat);
	}
	
	protected boolean changeAvailability(String initSeat, String finalSeat, boolean value) throws SeatException {
		return availability.changeAvailability(initSeat, finalSeat, value);
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public double getPrice() {
		return price;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return id + " - " + date.toLocaleString() + ", theatre: " + theatreName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MovieShowing other = (MovieShowing) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}