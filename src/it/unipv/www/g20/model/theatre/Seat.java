package it.unipv.www.g20.model.theatre;

/**
 * This class is referred to a seat in a cinema theatre.
 *
 * @see Theatre
 */
public class Seat {
	private final String id;
	private boolean available;

	public Seat(String seatId) {
		available = true;
		id = seatId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Seat other = (Seat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailability(boolean availability) {
		available = availability;
	}

	@Override
	public String toString() {
		final String s = available == true ? "available" : "not available";
		return "Seat: " + id + ", " + s;
	}

}
