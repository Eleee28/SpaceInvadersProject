package tp1.exception;

public class InitializationException extends GameModelException {
	
	public InitializationException() {
		super();
	}
	
	public InitializationException(String message) {
		super(message);
	}
	
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
