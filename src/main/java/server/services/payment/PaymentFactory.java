package server.services.payment;

/**This singleton class creates different payment items. */
public class PaymentFactory {
	private static PaymentFactory istance = null;
	private PaymentAdapter paymentSimulator;

	private PaymentFactory() {
		paymentSimulator=null;
	}

	/**
	 * Allows you to get a SimPaymentAdapter to make a payment
	 * @return PaymenAdapter "SimPaymentAdapter"
	 */
	public PaymentAdapter getPaymentAdapter() {
		if (paymentSimulator == null) {
			paymentSimulator = new SimPaymentAdapter();
		}
		return paymentSimulator;
	}

	public static PaymentFactory getInstance() {
		if (istance == null) {
			istance = new PaymentFactory();
		}
		return istance;
	}
}
