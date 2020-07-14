package server.domain.payment;

/**This singleton class creates different payment items. */
public class ServiceFactory {
	private static ServiceFactory istance = null;
	private PaymentAdapter paymentSimulator;

	private ServiceFactory() {
		paymentSimulator=null;
	}

	public PaymentAdapter getPaymentAdapter() {
		if (paymentSimulator == null) {
			paymentSimulator = new SimPaymentAdapter();
		}
		return paymentSimulator;
	}

	public static ServiceFactory getInstance() {
		if (istance == null) {
			istance = new ServiceFactory();
		}
		return istance;
	}
}
