package it.unipv.www.g20.model.payment;

public interface PaymentAdapter {
	boolean pay(double money, String code, String pin, String cvc);
}
