package server.domain.theatre;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DisabledSeat")
public class DisabledSeat extends Seat {
	@Column(name="addition")
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
