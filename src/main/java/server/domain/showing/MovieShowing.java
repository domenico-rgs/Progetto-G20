package server.domain.showing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private String movie;
	

	public MovieShowing(String id, String movie, LocalDateTime date, Theatre theatre, double price) {
		this.id=id;
		this.date=date;
		this.price=price;
		this.movie=movie;
		this.theatreName = theatre.getTheatreName();
		availability = new Availability(theatre.getSeatsList()); //da rivedere per persistenza
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
	
	public String getDateToString() {
		return date.format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm"));
	}

	public String getMovie() {
		return movie;
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

	public Availability getAvailability() {
		return availability;
	}

	@Override
	public String toString() {
		return  getDateToString() + ", theatre: " + theatreName;
	}


}
