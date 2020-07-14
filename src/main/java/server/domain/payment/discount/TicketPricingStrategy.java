package server.domain.payment.discount;

/** This interface implements a method to obtain the total price of your purchases.*/
public interface TicketPricingStrategy {
	public double getTotalPrice(double price);
	public String getCode();
}
