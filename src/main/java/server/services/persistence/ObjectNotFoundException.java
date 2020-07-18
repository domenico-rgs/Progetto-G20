package server.services.persistence;

@SuppressWarnings("serial")
public class ObjectNotFoundException extends Exception{
	public ObjectNotFoundException() {
		super("Object not found!");
	}

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
