package server.exception;

@SuppressWarnings("serial")
public class SearchException extends Exception{
	public SearchException() {
		super("Something went wrong with the search");
	}

	public SearchException(String msg) {
		super(msg);
	}
}
