package it.unipv.www.g20.model.exception;

/**
 * Exception to use when doing a search in a data structure
 * and the result is already present or not present
 */
@SuppressWarnings("serial")
public class SearchException extends Exception {
	public SearchException() {
		super("Si è verificato un errore in un tentativo di ricerca dei dati");
	}

	public SearchException(String text) {
		super(text);
	}
}
