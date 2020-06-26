package server.domain.showing;

import java.time.LocalDateTime;

import server.domain.exception.SeatException;
import server.domain.theatre.Theatre;

/**
 * This class is referred to a movie projection in the cinema.
 */

public class MovieShowing {
	private String id;
	private LocalDateTime date;
	private Availability availability;
	private double price;
	private String theatreName;

	public MovieShowing(String id, LocalDateTime date, Theatre theatre, double price) {
		this.id=id;
		this.date=date;
		this.price=price;
		availability = new Availability(theatre.getSeatsList());
		theatreName=theatre.getTheatreName();
	}

	public void editShowing(String theatre, double price) {
		theatreName=theatre;
		this.price=price;
	}

	protected boolean searchAvailability(String seat) throws SeatException {
		return availability.searchAvailability(seat);
	}

	protected void changeAvailability(String[] seats, boolean value) throws SeatException {
		availability.changeAvailability(seats, value);
	}

	public LocalDateTime getDate() {
		return date;
	}


	public String getTheatreName() {
		return theatreName;
	}

	public double getPrice() {
		return price;
	}
	
	

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return  date.toString() + ", theatre: " + theatreName;
	}


}
