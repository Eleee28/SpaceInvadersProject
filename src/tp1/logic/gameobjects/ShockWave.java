package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public class ShockWave extends UCMWeapon {
	
	public ShockWave(Game game, Position pos, int life) {
		super(game, pos, life);

		this.enabled = false;
		this.damage = 1;
	}

	@Override
	public boolean isOnPosition(Position pos) {return false;}

	@Override
	protected int getArmour() {return 0;}

	@Override
	public void onDelete() {}

	@Override
	public void automaticMove() {}

	@Override
	public void setVerticalMove(boolean verticalMove) {}
	
	@Override
	public void setPerformeVertical(boolean ok) {}

	@Override
	public void setDeploy(boolean ok) {}

	@Override
	public boolean getDeploy() {return false;}

	@Override
	public int getId() {return 0;}

	@Override
	public int getPoints() {return 0;}

	@Override
	public void die() {}
}
