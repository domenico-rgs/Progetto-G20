package server.domain.theatre;

import javax.persistence.*;

@Entity
@DiscriminatorValue("PremiumSeat")
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
