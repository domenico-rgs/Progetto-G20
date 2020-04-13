package it.unipv.www.g20.model.theatre;

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
		return "Seat:\n[id=" + id + "\n available=" + available + "]";
	}

}
