package server.domain.exception;

@SuppressWarnings("serial")
public class SearchException extends Exception{
	public SearchException() {
		super();
	}

	public SearchException(String msg) {
		super(msg);
	}
}
