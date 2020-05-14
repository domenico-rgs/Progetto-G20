package it.unipv.www.g20.model.cinema;

import it.unipv.www.g20.model.payment.PaymentAdapter;
import it.unipv.www.g20.model.payment.PaymentSimulatorAdapter;

public class ServiceFactory {
	private static ServiceFactory istance = null;
	private PaymentAdapter paymentSimulator;

	private ServiceFactory() {
		paymentSimulator=null;
	}

	public PaymentAdapter getPaymentAdapter() {
		if (paymentSimulator == null)
			paymentSimulator = new PaymentSimulatorAdapter();
		return paymentSimulator;
	}

	public static ServiceFactory getTicketLedger() {
		if (istance == null)
			istance = new ServiceFactory();
		return istance;
	}
}
