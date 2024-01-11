package tp1.exception;

public class NotEnoughtPointsException extends GameModelException {
	
	public NotEnoughtPointsException() {
		super();
	}
	
	public NotEnoughtPointsException(String str) {
		super(str);
	}
	
	public NotEnoughtPointsException(String message, Throwable cause) {
		super(message, cause);
	}
}
