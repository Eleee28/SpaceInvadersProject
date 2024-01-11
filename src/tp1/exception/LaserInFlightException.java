package tp1.exception;

public class LaserInFlightException extends GameModelException {
	
	public LaserInFlightException() {
		super();
	}
	
	public LaserInFlightException(String str) {
		super(str);
	}
	
	public LaserInFlightException(String message, Throwable cause) {
		super(message, cause);
	}
}
