package it.unipv.www.g20.model.exception;

@SuppressWarnings("serial")
public class SearchException extends Exception {
	public SearchException() {
		super("Si è verificato un errore in un tentativo di ricerca dei dati");
	}

	public SearchException(String text) {
		super(text);
	}
}
