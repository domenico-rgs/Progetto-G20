package server.domain.theatre;

public class DisabledSeat extends Seat {
	private final double addition = 0.5; //riduzione in percentuale

	public DisabledSeat(String position) {
		super(position);
	}

	@Override
	public double getAddition() {
		return addition;
	}

	@Override
	public TypeSeat getType() {
		return TypeSeat.DISABLED;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Disabled";
	}
}
