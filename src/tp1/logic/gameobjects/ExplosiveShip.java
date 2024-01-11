package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExplosiveShip extends AlienShip {
	public ExplosiveShip(GameWorld game, Position pos, AlienManager alienManager) {
		super(game, pos, 0);
		
		this.speed = game.speedLevel(game.getLevel());
		this.resistance = 2;
		this.damage = 0;
		this.points = 12;
		this.life = 2;
		this.symbol = Messages.EXPLOSIVE_ALIEN_SYMBOL;
		this.description = Messages.REGULAR_ALIEN_DESCRIPTION;
		this.info = Messages.alienDescription(this.description, this.points, this.damage, this.resistance);

	}
	
	public ExplosiveShip() {
		super();
		this.symbol = Messages.EXPLOSIVE_ALIEN_SYMBOL;
	}

	@Override
	public void onDelete() {}
	
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
	protected AlienShip copy(GameWorld game, Position pos, AlienManager am){
		return new ExplosiveShip(game, pos, am);
	}

}
