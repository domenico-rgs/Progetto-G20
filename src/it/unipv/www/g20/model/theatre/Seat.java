package it.unipv.www.g20.model.theatre;

/**
 * This class is referred to a seat in a cinema theatre. 
 * @see Theatre
 */
public class Seat {
	private final String id;
	private boolean available;

	public Seat(String seatId) {
		available=true;
		id = seatId;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvalaible(boolean isAvalaible) {
		available = isAvalaible;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Seat: " + id + ", " + available + "\n";
	}

}
