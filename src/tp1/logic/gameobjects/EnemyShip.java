package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public abstract class EnemyShip extends Ship {
	
	protected int resistance;
	protected int points;
	protected Move dir;

	public EnemyShip(GameWorld game, Position pos, int life) {
		super(game, pos, life);
	}

	public EnemyShip() {
		super();
	}
	
	public int getPoints() {
		return (this.points);
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return (Messages.status(getSymbol(), this.life));
	}
	
	public Position getPos() {
		return (this.pos);
	}

		
}
