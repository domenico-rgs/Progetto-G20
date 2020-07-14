package server.domain.exception;

@SuppressWarnings("serial")
public class PaymentException extends Exception{
	public PaymentException() {
		super("Something went wrong with payment");
	}

	public PaymentException(String msg) {
		super(msg);
	}
}
