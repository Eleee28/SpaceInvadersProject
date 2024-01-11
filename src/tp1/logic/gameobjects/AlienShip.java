package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.GameWorld;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class AlienShip extends EnemyShip {
	
	protected int cyclesToMove;
	protected int speed;
	protected AlienManager alienManager;
	protected boolean verticalMove;
	protected boolean performeVertical;

	public AlienShip(GameWorld game, Position pos, int life) {
		super(game, pos, 0);

		this.cyclesToMove = 0;
		this.dir = Move.LEFT;
		this.verticalMove = false;
		this.performeVertical = false;
	}
	
	public AlienShip() {
		super();
	}
	
	/**
	 * Creates a ship and adds it to AlienManager
	 * */
	protected abstract AlienShip copy(GameWorld game, Position pos, AlienManager am);
	
	public void setPerformeVertical(boolean ok) {
		this.performeVertical = ok;
	}

	@Override
	protected int getArmour() {return 0;}

	@Override
	public void onDelete() {}
	
	public boolean isOnPosition(Position pos) {
		return (this.pos.equals(pos));
	}
	
	/**
	 * Automatic move
	 * 
	 * */
	public void automaticMove() {		
		if (this.performeVertical && !verticalMove) {
			descent();	// move down and change direction
			this.verticalMove = true;
			
		}
		else if (this.cyclesToMove == this.speed) {
				performMovement(this.dir);
				verticalMove = false;
				performeVertical = false;

				this.cyclesToMove = 0;
			}
		else
			this.cyclesToMove++;
	}
	
	private void descent() {
		performMovement(Move.DOWN);
		if (this.dir == Move.LEFT)
			this.dir = Move.RIGHT;
		else if (this.dir == Move.RIGHT)
			this.dir = Move.LEFT;

	}

	private void performMovement(Move dir) {	
		pos.moveAlien(dir);
	}
	
	public int getSpeed() {
		return (this.speed);
	}

	public int getNumCycles() {
		return (this.cyclesToMove);
	}
	
	public int getLife() {
		return (this.life);
	}
	
	public String getDescription() {
		return (this.description);
	}
	
	public void setPos(int row, int col) {
		this.pos.setPos(row, col);
	}

	public void setVerticalMove(boolean verticalMove) {
		this.verticalMove = verticalMove;
	}	

	public boolean decreaseLife() {
		boolean dead = false;

		this.life--;
		if (this.life == 0) {
			dead = true;
			die();
		}
		return (dead);
	}
	
	public void die() {
		this.getPos().setPos(15, 15);
	}

	public boolean receiveAttack(UCMLaser laser) {
		
		boolean attacked = false;

		this.life--;
		if (this.life == 0)
			die();

		attacked = true;
		return (attacked);
	}

	public boolean hitLaser(UCMLaser laser) {
		return false;
	}
	
	public boolean equalPos(Position pos) {
		return this.pos.equals(pos);
	}
	
	public boolean secondLastRow() {
		return(this.pos.getRow() == Game.DIM_Y - 2);
	}
}
