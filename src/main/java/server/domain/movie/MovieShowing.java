package server.domain.movie;

import java.util.Date;

import javax.persistence.*;

import server.domain.exception.SeatException;
import server.domain.theatre.Theatre;

/**
 * This class is referred to a movie projection in the cinema.
 */
@Entity
@Table(name="movieShowing")
public class MovieShowing {
	@Id
	@Column(name="id")
	private String id;
	@Transient
	private static int intId=0;
	@Column(name="dateMovieShowing")
	private Date date;
	@Transient //da correggere
	private Availability availability;
	@Column(name="price")
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

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return id + " - " + date.toLocaleString() + ", theatre: " + theatreName;
	}
}
