package server.domain.cinema;

import java.util.UUID;

//import javax.persistence.*;   //commentato perchè inutilizzato

/**
 * This class identifies a bought ticket to show a movie.
 */
//@Entity
//@Table(name="ticket")
public class Ticket {
	//@Id
	//@Column(name="ticketCode", updatable = false)
	private String code;
	//@Column(name="movie")
	private String movie;
	//@Column(name="occupiedSeat")
	private String seat;
	//@Column(name="showing")
	private String showing;
	//@Column(name="totalPrice", updatable = false)
	private double totalPrice;

	public Ticket() {}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((code == null) ? 0 : code.hashCode());
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
	
	
}
