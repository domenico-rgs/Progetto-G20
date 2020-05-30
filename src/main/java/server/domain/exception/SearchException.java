package server.domain.exception;

@SuppressWarnings("serial")
public class SearchException extends Exception{
	public SearchException() {
		super("Something went wrong in the search");
	}

	public SearchException(String msg) {
		super(msg);
	}
}
