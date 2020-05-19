package server.domain.theatre;

public class DisabledSeat extends Seat {
	private static double addition = 50; //riduzione in percentuale

	public DisabledSeat(String position) {
		super(position);
	}

	public static double getAddition() {
		return addition;
	}

	public static void setAddition(double addition) {
		DisabledSeat.addition = addition;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Disabled";
	}
}
