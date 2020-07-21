package server.exception;

@SuppressWarnings("serial")
public class DiscountException extends Exception{
	public DiscountException() {
		super("Discount exception");
	}

	public DiscountException(String msg) {
		super(msg);
	}
}
