package server.domain.payment;

/**This interface implements all essential methods for a payment */
public interface PaymentAdapter {
	/**
	 * this methods permits to pay a ticket
	 * @param money the amount of available money 
	 * @param code code for a discount
	 * @param pin card's pin
	 * @param cvc card's cvc
	 * @return true if the payment is successful
	 */
	boolean pay(double money, String code, String date, String cvc);
}
