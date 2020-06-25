package server.domain.showing;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import server.domain.cinema.Movie;
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
	@Column(name="dateShow")
	private LocalDateTime date;
	@Transient //da correggere ?
	private Availability availability;
	@Column(name="price")
	private double price;
	@OneToOne( fetch= FetchType.EAGER )
	@JoinColumn( name="Theatre")
	private Theatre theatre;

	public MovieShowing() {}

	public MovieShowing(LocalDateTime date, Theatre theatre, double price) {
		intId++;
		id="P"+intId;
		this.date=date;
		this.price=price;
		this.theatre=theatre;
		availability = new Availability(theatre.getSeatsList());
	}

	public void editShowing(Theatre theatre, double price) {
		this.theatre=theatre;
		this.price=price;
	}

	protected boolean searchAvailability(String seat) throws SeatException {
		return availability.searchAvailability(seat);
	}

	protected void changeAvailability(String[] seats, boolean value) throws SeatException {
		availability.changeAvailability(seats, value);
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}


	public String getTheatreName() {
		return theatre.getTheatreName();
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return id + " - " + date.toString() + ", theatre: " + theatre;
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
