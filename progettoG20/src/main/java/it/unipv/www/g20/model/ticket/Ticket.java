package it.unipv.www.g20.model.ticket;

import java.util.UUID;

/**
 * This class identifies a bought ticket to show a movie.
 */
public class Ticket {
	private String code;
	private String movie;
	private String seat;
	private String showing;
	private double totalPrice;

	public Ticket(String movie, String seat, String showing, double totalPrice) {
		code = UUID.randomUUID().toString();
		this.movie=movie;
		this.showing=showing;
		this.seat=seat;
		this.totalPrice=totalPrice;
	}

	@Override
	public String toString() {
		return "Ticket id: " + code + "\nDate: " + showing + "- Seat: " + seat +"\nMovie: " + movie + "\nPrice: € "
				+ totalPrice;
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

	public String getShowing() {
		return showing;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
}
