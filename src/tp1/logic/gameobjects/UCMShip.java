package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.exception.NotAllowedMoveException;
import tp1.exception.OffWorldException;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.view.Messages;

public class UCMShip extends Ship {	

	public UCMShip(Game game, Position pos, int life) {
		super(game, pos, life);
		
		this.damage = 1;
		this.symbol = Messages.UCMSHIP_SYMBOL;
		this.description = Messages.UCMSHIP_DESCRIPTION;
		this.info = Messages.ucmShipDescription(this.description, this.damage, this.life);
	}
	
	public String getSymbol() {
		return(this.symbol);
	}
	
	public String getDescription() {
		return(this.description);
	}
	
	public String getInfo() {
		return(this.info);
	}
	
	public Position getPos() {
		return(this.pos);
	}
	
	public int getCol() {
		return this.pos.getCol();
	}
	
	public int getRow() {
		return this.pos.getRow();
	}
	
	public int getLife() {
		return (this.life);
	}
	
	public int getDamage() {
		return (this.damage);
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String toString() {
		return(getSymbol());
	}
	
	/**
	 * Performs movement
	 * */
	public void move(Move move) throws OffWorldException, NotAllowedMoveException {
		if (move == Move.DOWN || move == Move.UP)
			throw new NotAllowedMoveException();
		this.getPos().move(move);
	}
	
	public void updateLife(int life) {
		this.life -= life;
	}

	@Override
	protected int getArmour() { return 0;}

	@Override
	public void onDelete() {}

	@Override
	public void automaticMove() {}

	@Override
	public void setVerticalMove(boolean verticalMove) {}

	@Override
	public int getPoints() {return 0;}

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
	public void die() {}
}
