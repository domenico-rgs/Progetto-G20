package it.unipv.www.g20.model.exception;

/**
 * This class is used when an exception occurs. The type of the exception is "FileNotFoundException".
 */
@SuppressWarnings("serial")
public class NotFoundException extends Exception{

	public NotFoundException(String message) {
		super(message);
	}

}
