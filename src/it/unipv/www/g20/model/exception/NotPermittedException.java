package it.unipv.www.g20.model.exception;

@SuppressWarnings("serial")
public class NotPermittedException extends Exception {
	public NotPermittedException() {
		super("Non si ha il permesso necessario per l'accesso alla risorsa");
	}

	public NotPermittedException(String text) {
		super(text);
	}
}
