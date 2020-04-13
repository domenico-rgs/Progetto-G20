/**This class is used when an exception occurs. The type of the exception is "FileNotFoundException".*/
package it.unipv.www.g20.model.exception;

@SuppressWarnings("serial")
public class NotFoundException extends Exception{

	public NotFoundException(String message) {
		super(message);
	}

}
