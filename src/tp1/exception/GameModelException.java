package tp1.exception;

public abstract class GameModelException extends Exception {
	
	public GameModelException() {
		super();
	}
	
	public GameModelException (String message) {
		super(message);
	}
	
	public GameModelException (String message, Throwable cause) {
		super(message, cause);
	}
}
