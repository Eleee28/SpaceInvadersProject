package tp1.exception;

public class OffWorldException extends GameModelException {
	
	public OffWorldException() {
		super();
	}
	
	public OffWorldException (String message) {
		super(message);
	}
	
	public OffWorldException (String message, Throwable cause) {
		super(message, cause);
	}

}
