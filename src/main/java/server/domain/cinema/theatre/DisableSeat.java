package server.domain.cinema.theatre;

/**
 * This class represents a seat made available for disabled person.
 * @see Theatre
 **/
public class DisableSeat extends Seat {
	public DisableSeat(String position) {
		super(position);
	}

	@Override
	public double getAddition() {
		return 0.5; //50% reduction for the disabled seat to the price of the showing
	}

	@Override
	public TypeSeat getType() {
		return TypeSeat.DISABLE;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Disable";
	}
}
