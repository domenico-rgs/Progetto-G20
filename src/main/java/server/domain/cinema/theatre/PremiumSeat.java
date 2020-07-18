package server.domain.cinema.theatre;

/**
 * This class represents a seat more confortable than the others
 * @see Seat
 */
public class PremiumSeat extends Seat{
	public PremiumSeat(String position) {
		super(position);
	}

	@Override
	public double getAddition() {
		return 1.2; //20% addition for the premium seat to the price of the showing
	}

	@Override
	public TypeSeat getType() {
		return TypeSeat.PREMIUM;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Premium";
	}
}
