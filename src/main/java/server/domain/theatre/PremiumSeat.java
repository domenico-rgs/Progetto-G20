package server.domain.theatre;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Premium")
public class PremiumSeat extends Seat{
	@Column(name="addition")
	private final double addition = 1.2; //aggiunta in percentuale


	public PremiumSeat(String position) {
		super(position);
	}

	public double getAddition() {
		return addition;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Premium";
	}
}
