package server.domain.payment.discount;

public interface TicketPricingStrategy {
	public double getTotalPrice(double price);
}
