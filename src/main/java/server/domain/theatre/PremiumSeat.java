package server.domain.theatre;

public class PremiumSeat extends Seat{
	private final double addition = 1.2; //aggiunta in percentuale


	public PremiumSeat(String position) {
		super(position);
	}

	@Override
	public double getAddition() {
		return addition;
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
