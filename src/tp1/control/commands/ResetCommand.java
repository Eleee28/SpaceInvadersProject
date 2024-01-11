package tp1.control.commands;

import java.io.*;
import tp1.control.InitialConfiguration;
import tp1.exception.CommandExecuteException;
import tp1.exception.CommandParseException;
import tp1.exception.InitializationException;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class ResetCommand extends Command {
	
	private InitialConfiguration conf;
	
	public ResetCommand(InitialConfiguration conf) {
		this.conf = conf;
	}
	
	@Override
	protected String getName() {
		return Messages.COMMAND_RESET_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_RESET_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_RESET_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_RESET_HELP;
	}
	
	@Override
	public boolean execute(GameModel game) throws CommandExecuteException {
		boolean ok = false;

		try {
			game.reset(this.conf);
			ok = true;
		}
		catch (InitializationException e) {
			throw new CommandExecuteException("Invalid initial configuration" + Messages.LINE_SEPARATOR + e.getMessage());
		}
		
		return ok;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command com = null;
		
		if (matchCommandName(commandWords[0])) {
			try {
				this.conf = rConf(commandWords);
				if (this.conf != null)
					com = new ResetCommand(conf);
			}
			catch (InitializationException e) {
				throw new CommandParseException("Reset error" + Messages.LINE_SEPARATOR + e.getMessage());
			} 
			catch (FileNotFoundException a) {
				throw new CommandParseException(a.getMessage());
			} 
			catch (IOException e) {
				//e.printStackTrace();
				throw new CommandParseException(e.getMessage());
			}
			
		}
		
	    return com;
	}
	
	/**
	 * Deals with the initial configuration options
	 */
	private InitialConfiguration rConf(String[] command) throws InitializationException, FileNotFoundException, IOException {
		InitialConfiguration conf = null;
	
		if (command.length == 1)
			conf = InitialConfiguration.NONE;
		else if (command.length == 2)
		{
			try {
				String filename = command[1];
				conf = InitialConfiguration.readFromFile(filename);
			}
			catch (FileNotFoundException a){
				throw new FileNotFoundException(a.getMessage());
			}
		}
		else
			throw new InitializationException("Too many input parameters");
		
		return conf;
	}
}
