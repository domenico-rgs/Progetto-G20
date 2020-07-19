package server.services.payment;

/**
 *  A class which is the Factory for the different type of payment
 */
public class PaymentFactory {
	private static PaymentFactory istance = null;
	private PaymentAdapter paymentSimulator;

	private PaymentFactory() {
		paymentSimulator=null;
	}

	/**
	 * Allows you to get a SimPaymentAdapter to make a payment simulation
	 * @return PaymenAdapter "SimPaymentAdapter"
	 */
	public PaymentAdapter getPaymentAdapter() {
		if (paymentSimulator == null) {
			paymentSimulator = new SimPaymentAdapter();
		}
		return paymentSimulator;
	}

	/**
	 * 'Pattern Singleton Implementation'
	 *
	 * If the object has not already been instanced, it is instanced and it is returned.
	 * @return instance(PaymentFactory)
	 */
	public static synchronized PaymentFactory getInstance() {
		if (istance == null) {
			istance = new PaymentFactory();
		}
		return istance;
	}
}
