package server.domain.cinema.theatre;

/**
 * This class is referred to a seat in a cinema's theatre
 * @see Theatre
 */
public class Seat {
	private String position;
	
	public Seat(String seatId) {
		position = seatId;
	}

	public String getPosition() {
		return position;
	}

	public double getAddition() {
		return 1.0;
	}
	
	@Override
	public String toString() {
		return "Seat: " + position;
	}


	public TypeSeat getType() {
		return TypeSeat.NORMAL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((position == null) ? 0 : position.hashCode());
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
		Seat other = (Seat) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

}
