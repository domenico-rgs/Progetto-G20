package server.domain.theatre;

//import javax.persistence.*;			//importazione non richiesta

/**
 * This class is referred to a seat in a cinema theatre
 * @see Theatre
 */
//@Entity(name="seat")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="typeOfSeat", discriminatorType = DiscriminatorType.INTEGER)
public class Seat {
	//@Id
	//@Column(name="pos")
	private String position;

	public Seat(String seatId) {
		position = seatId;
	}

	public Seat() {}

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

	public String getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "Seat: " + position;
	}

}
