package server.domain.payment.discount;

/** This interface implements a method to obtain the total price of your purchases.*/
public interface TicketPricingStrategy {
	/**
	 * It allows to obtain a final discounted price based on the discount percentage of the code
	 * @param price price not discounted
	 * @return discounted price of the percentage of the code
	 */
	double getTotalPrice(double price);
	String getCode();
}
