package server.domain.theatre;

//import javax.persistence.*; //importazione non richiesta

//@Entity
//@DiscriminatorValue("PremiumSeat")
public class PremiumSeat extends Seat{
	//@Column(name="addition")
	private static double addition = 1.2; //aggiunta in percentuale

	public PremiumSeat(String position) {
		super(position);
	}

	public static double getAddition() {
		return addition;
	}

	@Override
	public String toString() {
		return super.toString() + ", type: Premium";
	}
}
