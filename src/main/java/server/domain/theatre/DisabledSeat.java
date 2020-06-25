package server.domain.theatre;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Disabled")
public class DisabledSeat extends Seat {
	@Column(name="addition")
	private final double addition = 0.5; //riduzione in percentuale

	public DisabledSeat(String position) {
		super(position);
	}

	public double getAddition() {
		return addition;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Disabled";
	}
}
