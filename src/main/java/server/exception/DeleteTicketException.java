package server.exception;

@SuppressWarnings("serial")
public class DeleteTicketException extends Exception{
	public DeleteTicketException() {
		super("Ipossible to delete the ticket");
	}

	public DeleteTicketException(String msg) {
		super(msg);
	}
}
