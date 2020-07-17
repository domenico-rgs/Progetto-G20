package server.exception;

@SuppressWarnings("serial")
public class SeatException extends Exception{
	public SeatException() {
		super("Something went wrong with the seat");
	}

	public SeatException(String msg) {
		super(msg);
	}
}
