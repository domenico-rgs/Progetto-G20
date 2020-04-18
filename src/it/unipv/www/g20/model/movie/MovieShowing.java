package it.unipv.www.g20.model.movie;

import java.util.Date;
import java.util.HashMap;

import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.theatre.Seat;

/**
 * This class is referred to a movie projection in the cinema.
 */
public class MovieShowing {
	private final HashMap<String, Seat> seatsList;
	private final Movie movie;
	private final double standardPrice;
	private final Date date;

	public MovieShowing(Movie movie, Date date, double price, int row, int col) {
		seatsList = new HashMap<>();
		this.movie = movie;
		this.date = date;
		standardPrice = price;
		createSeats(row, col);
	}

	public void bookSeat(String seat) throws NotAvailableException {
		if (!(seatsList.get(seat).isAvailable()))
			throw new NotAvailableException("Sedile già prenotato");

		seatsList.get(seat).setAvailability(false);
	}

	public void deleteReservation(String seat) {
		seatsList.get(seat).setAvailability(true);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MovieShowing other = (MovieShowing) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (Double.doubleToLongBits(standardPrice) != Double.doubleToLongBits(other.standardPrice))
			return false;
		return true;
	}

	public Date getDate() {
		return date;
	}

	public Movie getMovie() {
		return movie;
	}

	public double getStandardPrice() {
		return standardPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((date == null) ? 0 : date.hashCode());
		result = (prime * result) + ((movie == null) ? 0 : movie.hashCode());
		long temp;
		temp = Double.doubleToLongBits(standardPrice);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public String printAvailableSeats() {
		final StringBuilder string = new StringBuilder();
		for (final String s : seatsList.keySet())
			string.append(seatsList.get(s).toString() + " - ");
		return string.toString();
	}

	@Override
	public String toString() {
		return "Movie showing: " + movie + "\n";
	}

	/*
	 * Create the seats in the room by associating them with an id consisting of a
	 * letter that identifies the row and a number that identifies the column
	 */
	private void createSeats(int row, int col) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				seatsList.put(Character.toString(65 + i) + j, new Seat(Character.toString(65 + i) + j));
			}
		}
	}
}
