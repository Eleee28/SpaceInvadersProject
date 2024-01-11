package tp1.control.commands;

import tp1.exception.CommandParseException;
import tp1.view.Messages;

/**
 * Abstract class that inherits from Command and serves as the basis for all
 * commands that have no parameters (none, reset, help,...)
 * 
 */

public abstract class NoParamsCommand extends Command {

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command r = null;
		
		if (matchCommandName(commandWords[0])) {
			if (commandWords.length == 1)
				r = this;
			else 
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		
		return r;
	}
	
}
