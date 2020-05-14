package application.model.cinema;

import application.model.payment.PaymentAdapter;
import application.model.payment.SimPaymentAdapter;

public class ServiceFactory {
	private static ServiceFactory istance = null;
	private PaymentAdapter paymentSimulator;

	private ServiceFactory() {
		paymentSimulator=null;
	}

	public PaymentAdapter getPaymentAdapter() {
		if (paymentSimulator == null)
			paymentSimulator = new SimPaymentAdapter();
		return paymentSimulator;
	}

	public static ServiceFactory getTicketLedger() {
		if (istance == null)
			istance = new ServiceFactory();
		return istance;
	}
}
