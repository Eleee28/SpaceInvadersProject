package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class SuperLaser extends UCMLaser{
	
	// enabled: false - no laser in the board, true - laser in the board
	
	public SuperLaser(Game game, Position pos, int life) {
		super(game, pos, life);

		this.damage = 2;
		this.symbol = Messages.SUPER_LASER_SYMBOL;
	}
	

}
