package tp1.exception;

public class CommandExecuteException extends Exception {
	
	public CommandExecuteException() {
		super();
	}
	
	public CommandExecuteException(String str) {
		super(str);
	}
	
	public CommandExecuteException(String message, Throwable cause) {
		super(message, cause);
	}

}
