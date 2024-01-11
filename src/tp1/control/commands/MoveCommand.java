package tp1.control.commands;

import tp1.exception.*;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.view.Messages;

public class MoveCommand extends Command {

	private Move move;

	public MoveCommand() {}

	protected MoveCommand(Move move) {
		this.move = move;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_MOVE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_MOVE_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_MOVE_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_MOVE_HELP;
	}
	
	@Override
	public boolean execute(GameModel game) throws CommandExecuteException {

		try {
			game.move(move);
		}
		catch (OffWorldException e) {
			throw new CommandExecuteException(Messages.MOVEMENT_ERROR, e);
		}
		catch (NotAllowedMoveException | IllegalArgumentException e) {
			throw new CommandExecuteException(Messages.DIRECTION_ERROR, e);
		}
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command com = null;
		
		if (matchCommandName(commandWords[0])) {
			try {
				this.move = moveCom(commandWords[1]);
				if (this.move != null)
					com = new MoveCommand(move);
			}
			catch (NotAllowedMoveException e) {
				throw new CommandParseException(Messages.DIRECTION_ERROR + commandWords[1].toUpperCase(), e);
			}
		}
			
	    return com;
	}
	
	/*
	 * Deals with the direction of the movement
	 */
	private Move moveCom(String command) throws NotAllowedMoveException {
		Move move;
		
		if (command.equalsIgnoreCase("n") || command.equalsIgnoreCase("none") || command.equalsIgnoreCase(""))
			move = Move.NONE;
		else if (command.equalsIgnoreCase("left"))
			move = Move.LEFT;
		else if (command.equalsIgnoreCase("right"))
			move = Move.RIGHT;
		else if (command.equalsIgnoreCase("lleft"))
			move = Move.LLEFT;
		else if (command.equalsIgnoreCase("rright"))
			move = Move.RRIGHT;
		else
			throw new NotAllowedMoveException(Messages.ALLOWED_SHIP_MOVE);
		
		return (move);
	}

}