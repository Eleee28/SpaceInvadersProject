package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class Bomb extends EnemyWeapon {
	
	private int Des_number;
	
	public Bomb(Game game, Position pos, int life, int firingRate, int Des_number) {
		super(game, pos, life);
		
		this.Des_number = Des_number;
		this.enabled = false;
		this.firingRate = firingRate;
		this.deploy = false;
		this.dir = Move.DOWN;
		this.damage = 1;
		this.symbol = Messages.BOMB_SYMBOL;
	}

	public Position getPos() {
		return (this.pos);
	}

	public int getDamage() {
		return (this.damage);
	}
	
	public String getSymbol() {
		return (this.symbol);
	}

	public boolean isDeploy() {
		return (this.deploy);
	}

	public void setDeploy(boolean deploy) {
		this.deploy = deploy;
	}

	public void setPos(Position pos) {
		this.pos.setPos(pos);
	}

	public void setPos(int col, int row) {
		this.pos.setPos(row, col);
	}

	public int getDes_number() {
		return (this.Des_number);
	}

	public void setDes_number(int des_number) {
		this.Des_number = des_number;
	}

	public Move getDir() {
		return (this.dir);
	}

	public int getLife() {
		return (this.life);
	}

	public String toString() {
		return (this.getSymbol());
	}

	/**
	 * Performs automatic move
	 * */
	public void automaticMove() {
		if(enabled) {
			performMovement(dir);
			if (isOut())
				die();
		}
	}

	// PERFORM ATTACK METHODS

	public void receiveDamage(int damage) {
		this.life -= damage;
		if (this.life == 0)
			die();
	}

	public void die() {
		this.pos.setPos(13, 13);
		this.enabled = false;
	}

	/**
	 * Checks if the bomb is out of the board
	 * */
	private boolean isOut() {
		boolean isOut = false;

		if (pos.getRow() <= Game.DIM_X)
			isOut = false;
		else
			isOut = true;
		return isOut;
	}

	private void performMovement(Move dir) {
		pos.updatePos(dir);
	}
	
	public void changePosBomb(Position pos) {
		this.pos.setPos(pos.getRow(), pos.getCol());
	}
	
	public boolean compBombLaser(Position laserPos) {
		return (this.getPos().compBombLaser(laserPos));
	}
	
	@Override
	public boolean isOnPosition(Position pos) {return false;}

	@Override
	protected int getArmour() {return 0;}

	@Override
	public void onDelete() {}

	@Override
	public void setVerticalMove(boolean verticalMove) {}

	@Override
	public void setPerformeVertical(boolean ok) {}

	public void computerAction() {
		if(!this.enabled) {
			if (this.game.shoot()) {
				this.deploy = true;
				this.enabled = true;
				int idx = this.game.searchDestroyer(this.Des_number);
				this.pos.setPos(this.game.posDestroyer(idx).getRow(), this.game.posDestroyer(idx).getCol());
			}
		}		
	}

	@Override
	public int getId() {return 0;}

	@Override
	public int getPoints() {return 0;}
}
