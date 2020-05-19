package server.domain.movie;

import java.util.Date;

import javax.persistence.*;

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
    @OneToOne(mappedBy = "availability",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Availability availability;
    @OneToOne(mappedBy = "theatre",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Theatre theatre;
	@Column(name="price")
	private double price;
	
	public MovieShowing() {}

	public MovieShowing(Date date, Theatre theatre, double price) {
		intId++;
		id="P"+intId;
		this.date=date;
		this.theatre=theatre;
		this.price=price;
		availability = new Availability(theatre.getSeatsList());
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public double getPrice() {
		return price;
	}

	public Availability getAvailability() {
		return availability;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return getId() + " - " + date.toLocaleString() + " - " + theatre.getTheatreName();
	}
}
