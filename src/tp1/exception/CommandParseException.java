package tp1.exception;

public class CommandParseException extends Exception {
	
	public CommandParseException() {
		super();
	}
	
	public CommandParseException(String str) {
		super(str);
	}
	
	public CommandParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
