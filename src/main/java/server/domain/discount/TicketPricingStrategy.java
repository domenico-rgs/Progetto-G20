package server.domain.discount;

public interface TicketPricingStrategy {
	public double getTotalPrice(double price);
}
