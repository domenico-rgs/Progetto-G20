package it.unipv.www.g20.model.exception;

/**
 * Exception in case something is not available
 */
@SuppressWarnings("serial")
public class NotAvailableException extends Exception {
	public NotAvailableException() {
		super("Risorsa non disponibile");
	}

	public NotAvailableException(String text) {
		super(text);
	}
}
