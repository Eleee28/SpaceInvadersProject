package tp1.control.commands;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import tp1.control.InitialConfiguration;
import tp1.exception.CommandParseException;
import tp1.view.Messages;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
		new HelpCommand(),
		new MoveCommand(),
		new ExitCommand(),
		new ListCommand(),
		new NoneCommand(),
		new ResetCommand(InitialConfiguration.NONE),
		new ShockWaveCommand(),
		new ShootCommand(),
		new SuperLaserCommand()
	);
	
	/**
	 * Parses the command introduced and checks which type of command is from all the available commands
	 * 
	 * */

	public static Command parse(String[] commandWords) throws CommandParseException {		
		Command command = null;
		Command commandAux = null; 
		
		try {
			if (commandWords[0].equalsIgnoreCase(""))
				command = new NoneCommand();
			else {
				for (Command c: availableCommands) {
					commandAux = c.parse(commandWords);
					
					if (commandAux != null)
						command = commandAux;
				}
				if (command == null)
					throw new CommandParseException(Messages.UNKNOWN_COMMAND);
				
			}
		}
		catch (CommandParseException e) {
			throw new CommandParseException(e.getMessage()); 
		}
		
		return command;
	}
	
	/**
	 * Text to print for the help command
	 */
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();	
		for (Command c: availableCommands) {
			commands.append(c.helpText());
		}
		return commands.toString();
	}
	

}
