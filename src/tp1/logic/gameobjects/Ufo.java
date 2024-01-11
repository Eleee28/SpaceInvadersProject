package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class Ufo extends EnemyShip {
	
	private boolean enabled;

	public Ufo(Game game, Position pos, int life) {
		super(game, pos, life);

		this.enabled = false;
		this.points = 25;
		this.resistance = 1;
		this.symbol = Messages.UFO_SYMBOL;
		this.description = Messages.UFO_DESCRIPTION;
		this.info = Messages.alienDescription(this.description, this.points, 0, this.resistance);
		this.dir = Move.LEFT;
	}

	@Override
	protected int getArmour() {return 0;}

	@Override
	public void onDelete() {die();}
	
	/**
	 * Performs the actions of the UFO (generation and movement)
	 * */
	public void computerAction() {
		if (!enabled && canGenerateRandomUfo()) {
			this.life = this.resistance;
			enable();
		} 
		else {
			if (this.pos.getCol() == 0)
				onDelete();
			else
				performMovement(dir);
		}
	}
	
	private void performMovement(Move dir) {
		this.pos.moveAlien(dir);
	}
	
	private void enable() {
		this.pos.setPos(0, Game.DIM_Y);
		this.enabled = true;
	}
	
	public void receiveDamage(int damage) {
		this.life -= damage;
		if (this.life == 0) {
			die();
		}
	}

	public void die() {
		this.pos.setPos(15, 15);
		this.enabled = false;
	}
	
	/**
	 * Checks if the game should generate an ufo.
	 * 
	 * @return <code>true</code> if an ufo should be generated.
	 */
	private boolean canGenerateRandomUfo() {
		return (game.getRandom().nextDouble() < game.getLevel().getUfoFrequency());
	}

	@Override
	public void setVerticalMove(boolean verticalMove) {}

	@Override
	public void setPerformeVertical(boolean ok) {}

	@Override
	public void setEnable(boolean ok) {}

	@Override
	public boolean getEnabled() {return false;}

	@Override
	public void setDeploy(boolean ok) {}

	@Override
	public boolean getDeploy() {return false;}

	@Override
	public int getId() {return 0;}

	@Override
	public int getDamage() {return 0;}

	@Override
	public void automaticMove() {}
}
