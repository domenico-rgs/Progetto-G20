package it.unipv.www.g20.model.exception;

@SuppressWarnings("serial")
public class NotAvailableException extends Exception {
	public NotAvailableException() {
		super("Risorsa non disponibile");
	}

	public NotAvailableException(String text) {
		super(text);
	}
}
