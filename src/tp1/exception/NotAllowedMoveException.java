package tp1.exception;

public class NotAllowedMoveException extends GameModelException {
	
	public NotAllowedMoveException() {
		super();
	}
	
	public NotAllowedMoveException (String message) {
		super(message);
	}
	
	public NotAllowedMoveException (String message, Throwable cause) {
		super(message, cause);
	}

}
