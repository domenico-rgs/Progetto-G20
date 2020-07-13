package server.domain.cinema;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class identifies a ticket to show a movie.
 */
public class Ticket {
	private String code;
	private String movie;
	private String seat;
	private LocalDateTime date;
	private double totalPrice;

	public Ticket(String code, String movie, String seat, LocalDateTime date, double totalPrice) {
		this.code = code;
		this.movie=movie;
		this.date=date;
		this.seat=seat;
		this.totalPrice=totalPrice;
	}

	public String getMovie() {
		return movie;
	}

	public String getSeat() {
		return seat;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	@Override
	public String toString() {
		return "Ticket id: " + code + "\nDate: " + date.format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")) + "\nSeat: " + seat +"\nMovie: " + movie + "\nPrice: � "
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
