package server.exception;

@SuppressWarnings("serial")
public class OverlapException extends Exception {
	public OverlapException() {
		super("The showing overlaps with another");
	}

	public OverlapException(String msg) {
		super(msg);
	}
}
