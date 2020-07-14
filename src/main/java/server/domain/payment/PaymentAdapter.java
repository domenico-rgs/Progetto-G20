package server.domain.payment;

import server.domain.exception.PaymentException;

/**This interface implements all essential methods for a payment */
public interface PaymentAdapter {
	/**
	 * this methods permits to pay a ticket
	 * @param money the amount of available money
	 * @param code code for a discount
	 * @param pin card's pin
	 * @param cvc card's cvc
	 * @return true if the payment is successful
	 * @throws PaymentException 
	 */
	boolean pay(double money, String code, String pin, String cvc) throws PaymentException;
}
