package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public abstract class GameObject implements GameItem {

	protected Position pos;
	protected int life;
	protected GameWorld game;
	protected String symbol;
	
	public GameObject(GameWorld game, Position pos, int life) {	
		this.pos = pos;
		this.game = game;
		this.life = life;
	}
	
	public GameObject() {}

	@Override
	public boolean isAlive() {
		return this.life > 0;
	}

	protected int getLife() {
		return this.life;
	}
	
	public boolean equalPos(Position pos) {
		return (this.pos.equals(pos));
	}
	
	public boolean equalPos(int row, int col) {
		return (this.pos.equals(row, col));
	}
	
	public Position getPos() {
		return (this.pos);
	}
	
	public String getSymbol() {
		return (this.symbol);
	}
	
	public void setPos(Position nPos) {
		this.pos.setPos(nPos);
	}

/*
 * auxiliary methods to manipulate the position and life
 * 
 * auxiliary methods that we call during each cycle of the game
 * 
 * 
 * */
	
	public abstract int getDamage();
	protected abstract int getArmour();
	public abstract int getPoints();
	
	public abstract void onDelete();
	
	/**
	 * Performs the automatic move of the object
	 * */
	public abstract void automaticMove();
	
	/**
	 * Performs the computer action of the object
	 * */
	public void computerAction() {};
	
	/**
	 * Sets the vertical move
	 * */
	public abstract void setVerticalMove(boolean verticalMove);
	
	/**
	 * Performs the vertical move of the object
	 * */
	public abstract void setPerformeVertical(boolean ok);
	
	public abstract void setEnable (boolean ok);
	public abstract boolean getEnabled();
	public abstract void setDeploy(boolean ok);
	public abstract boolean getDeploy();
	public abstract int getId();
	public abstract void die();
	
	
	@Override
	public boolean performAttack(GameItem other) {
		boolean attacked = false;
		
		if (other.isOnPosition(this.pos)) {
			weaponAttack(other);
			die();
			attacked = true;
		}

		return (attacked);
	}
	
	private boolean weaponAttack(GameItem other) {
		return (other.receiveAttack((UCMWeapon)this));
	}
	
	@Override
	public boolean receiveAttack(EnemyWeapon weapon) {
		this.life -= weapon.getDamage();
		if (this.life <= 0)
			die();			
		
		return true;
	}

	@Override
	public boolean receiveAttack(UCMWeapon weapon) {
		this.life -= weapon.getDamage();
		if (this.life <= 0) {
			if (this.symbol == Messages.EXPLOSIVE_ALIEN_SYMBOL)
				game.explosiveDamage(this.pos);
			die();				
		}
		
		return true;	
	}
	
	public void reduceLife() {
		this.life--;
		if (this.life <= 0)
			this.die();
	}
}
