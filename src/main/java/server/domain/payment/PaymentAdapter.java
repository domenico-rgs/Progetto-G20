package server.domain.payment;

/**This interface implements all essential methods for a payment */
public interface PaymentAdapter {
	boolean pay(double money, String code, String pin, String cvc);
}
