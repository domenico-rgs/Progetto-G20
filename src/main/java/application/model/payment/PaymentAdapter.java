package application.model.payment;

public interface PaymentAdapter {
	boolean pay(double money, String code, String pin, String cvc);
}
