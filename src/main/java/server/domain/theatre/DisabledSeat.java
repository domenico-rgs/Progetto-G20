package server.domain.theatre;

//import javax.persistence.*;		//importazione non richiesta

//@Entity
//@DiscriminatorValue("DisabledSeat")
public class DisabledSeat extends Seat {
	//@Column(name="addition")
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
