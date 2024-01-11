package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class Weapon extends GameObject {
	
	protected Move dir;
	protected boolean enabled;
	protected int damage;
	
	public Weapon(Game game, Position pos, int life) {
		super(game, pos, life);

	}
	
	public boolean getEnabled() {
		return this.enabled;
	}
	
	public void setEnable (boolean ok) {
		this.enabled = ok;
	}
	
	public int getDamage() {
		return (this.damage);
	}
}
