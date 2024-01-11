package tp1.control.commands;

import tp1.exception.CommandExecuteException;
import tp1.exception.LaserInFlightException;
import tp1.exception.NotEnoughtPointsException;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class SuperLaserCommand extends NoParamsCommand{
	@Override
	protected String getName() {
		return Messages.COMMAND_SUPER_LASER_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_SUPER_LASER_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_SUPER_LASER_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_SUPER_LASER_HELP;
	}
	
	@Override
	public boolean execute(GameModel game) throws CommandExecuteException {

		try {
		game.shootSuperLaser();
		}
		catch (LaserInFlightException | NotEnoughtPointsException e) {
			throw new CommandExecuteException(Messages.SUPER_LASER_ERROR, e);	
		}

		return true;
	}
	
}
