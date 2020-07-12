package server.domain.cinema.theatre;

public class PremiumSeat extends Seat{
	public PremiumSeat(String position) {
		super(position);
	}

	@Override
	public double getAddition() {
		return 1.2;
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
