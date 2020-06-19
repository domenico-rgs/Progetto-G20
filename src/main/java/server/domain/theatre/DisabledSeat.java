package server.domain.theatre;

//import javax.persistence.*;		//importazione non richiesta

//@Entity
//@DiscriminatorValue("DisabledSeat")
public class DisabledSeat extends Seat {
	//@Column(name="addition")
	private static double addition = 0.5; //riduzione in percentuale

	public DisabledSeat(String position) {
		super(position);
	}

	public static double getAddition() {
		return addition;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Disabled";
	}
}
