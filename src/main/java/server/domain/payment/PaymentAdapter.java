package server.domain.payment;

import server.domain.exception.PaymentException;

/**This interface implements all essential methods for a payment */
public interface PaymentAdapter {
	/**
	 * Allows you to pay the ticket given the price, the card code, the expiry date and the cvc code
	 * @param money the amount of available money
	 * @param code code for a discount
	 * @param date card's pin
	 * @param cvc card's cvc
	 * @return true if the payment is successful
	 * @throws PaymentException
	 */
	boolean pay(double money, String code, String date, String cvc) throws PaymentException;
}
