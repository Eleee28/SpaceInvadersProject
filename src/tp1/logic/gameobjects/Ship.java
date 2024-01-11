package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;

public abstract class Ship extends GameObject {
	
	protected int damage;
	protected String description;
	protected String info;

	public Ship(GameWorld game, Position pos, int life) {
		super(game, pos, life);
	}

	public Ship() {
		super();
	}

	@Override
	public boolean isOnPosition(Position pos) {return false;}

	@Override
	protected abstract int getArmour();

	@Override
	public abstract void onDelete();

	@Override
	public abstract void automaticMove();
	
	public String getInfo() {
		return (this.info);
	}
}
