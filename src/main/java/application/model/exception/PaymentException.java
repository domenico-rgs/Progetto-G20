package application.model.exception;

@SuppressWarnings("serial")
public class PaymentException extends Exception{
	public PaymentException() {
		super();
	}
	
	public PaymentException(String msg) {
		super(msg);
	}
}
