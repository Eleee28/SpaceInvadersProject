package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class UCMLaser extends UCMWeapon {
	
	// enabled: false - no laser in the board, true - laser in the board
	
	public UCMLaser(Game game, Position pos, int life) {
		super(game, pos, life);
		
		this.dir = Move.UP;
		this.symbol = Messages.LASER_SYMBOL;
		this.enabled = false;
		this.damage = 1;
	}

	public String getSymbol() {
		return (this.symbol);
	}

	public Position getPos() {
		return (this.pos);
	}
	
	public int getCol() {
		return this.pos.getCol();
	}
	
	public int getRow() {
		return this.pos.getRow();
	}
	
	/**
	 * Initializes laser
	 * */
	public void initLaser(Position pos, int life) {
		this.pos.setPos(pos.getRow(), pos.getCol());
		this.enabled = true;
		this.life = life;
	}

	public String toString() {
		return (getSymbol());
	}

	/**
	 * Implements the automatic movement of the laser
	 */
	public void automaticMove() {
		performMovement(dir);
		if (isOut())
			die();
	}

	// PERFORM ATTACK METHODS

	public void die() {
		this.pos.setPos(12, 12);
		this.enabled = false;
	}

	/**
	 * Checks if its out
	 * */
	private boolean isOut() {
		boolean isOut = false;

		if (pos.getRow() >= 0)
			isOut = false;
		else
			isOut = true;
		return isOut;
	}

	private void performMovement(Move dir) {
		pos.updatePos(dir);
	}

	/**
	 * Method that implements the attack by the laser to a regular alien. It checks
	 * whether both objects are alive and in the same position. If so call the
	 * "actual" attack method {@link weaponAttack}.
	 * 
	 * @param other the regular alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */
	public boolean performAttack(RegularAlien other) {
		boolean attacked = false;

		if (other.equalPos(this.pos)) {
			weaponAttack(other);
			die();
			attacked = true;
		}

		return (attacked);
	}

	/**
	 * Method that implements the attack by the laser to a destroyer alien. It
	 * checks whether both objects are alive and in the same position. If so call
	 * the "actual" attack method {@link weaponAttack}.
	 * 
	 * @param other the destroyer alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */

	public boolean performAttack(DestroyerAlien other) {
		boolean attacked = false;

		if (other.equalPos(this.pos)) {
			weaponAttack(other);
			die();
			attacked = true;
		}

		return (attacked);
	}

	// ACTUAL ATTACK METHODS

	/**
	 * 
	 * @param other regular alien under attack by the laser
	 * @return always returns <code>true</code>
	 */
	private boolean weaponAttack(RegularAlien other) {
		return other.receiveAttack(this);
	}

	private boolean weaponAttack(DestroyerAlien other) {
		return other.receiveAttack(this);
	}

	// RECEIVE ATTACK METHODS

	/**
	 * Method to implement the effect of bomb attack on a laser
	 * 
	 * @param weapon the received bomb
	 * @return always returns <code>true</code>
	 */

	public boolean receiveAttack(Bomb weapon) {
		receiveDamage(weapon.getDamage());
		return true;
	}

	public void receiveDamage(int damage) {
		this.life = 0;
		if (this.life == 0)
			die();
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
	public int getPoints() {return 0;}

	@Override
	public void setPerformeVertical(boolean ok) {}

	@Override
	public void setDeploy(boolean ok) {}

	@Override
	public boolean getDeploy() {return false;}

	@Override
	public int getId() {return 0;}
}
