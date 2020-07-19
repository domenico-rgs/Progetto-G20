package server.exception;

@SuppressWarnings("serial")
public class DeleteTicketException extends Exception{
	public DeleteTicketException() {
		super("Impossible to delete the ticket");
	}

	public DeleteTicketException(String msg) {
		super(msg);
	}
}
