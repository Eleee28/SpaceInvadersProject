package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class DestroyerAlien extends AlienShip {
	
	private int DestroyerId;

	public DestroyerAlien(GameWorld game, Position pos, AlienManager alienManager, int id) {
		super(game, pos, 0);
		
		this.speed = game.speedLevel(game.getLevel());
		this.resistance = 1;
		this.damage = 1;
		this.points = 10;
		this.life = 1;
		this.symbol = Messages.DESTROYER_ALIEN_SYMBOL;
		this.description = Messages.EXPLOSIVE_ALIEN_DESCRIPTION;
		this.info = Messages.alienDescription(this.description, this.points, this.damage, this.resistance);
		this.DestroyerId = id;
	}
	
	public DestroyerAlien() {
		super();
		this.symbol = Messages.DESTROYER_ALIEN_SYMBOL;
	}

	public int getId() {
		return this.DestroyerId;
	}

	@Override
	public void setEnable(boolean ok) {}

	@Override
	public boolean getEnabled() {return false;}

	@Override
	public void setDeploy(boolean ok) {}

	@Override
	public boolean getDeploy() {return false;}

	@Override
	public int getDamage() {return 0;}
	
	@Override
	protected AlienShip copy(GameWorld game, Position pos, AlienManager am){
		return new DestroyerAlien(game, pos, am, DestroyerId);
	}
}
