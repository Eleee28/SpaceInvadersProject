package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public abstract class EnemyWeapon extends Weapon {
	
	protected double firingRate;
	protected boolean deploy;

	public EnemyWeapon(Game game, Position pos, int life) {
		super(game, pos, life);
	}
	
	public boolean getDeploy() {
		return this.deploy;
	}
	
	public void setDeploy(boolean ok) {
		this.deploy = ok;
	}
	
}
