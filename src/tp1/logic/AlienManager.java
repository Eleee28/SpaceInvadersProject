package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.exception.InitializationException;
import tp1.logic.gameobjects.AlienShip;
import tp1.logic.gameobjects.Bomb;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.ShipFactory;
import tp1.logic.gameobjects.Ufo;
import tp1.view.Messages;

public class AlienManager  {
	
	private Game game;
	private Level level;
	private int remainingAliens;
	
	public AlienManager(Game game, Level level) {
		this.game = game;
		this.level = level;
	}
	
	/**
	 * Initializes the aliens using two auxiliary methods for each type 
	 * 
	 */
	public  GameObjectContainer initialize(InitialConfiguration conf)  throws InitializationException{
		this.remainingAliens = 0;
		GameObjectContainer container = new GameObjectContainer();
		
		if (conf == null || conf == InitialConfiguration.NONE) {
			initializeRegularAliens(container);
			initializeDestroyerAliens(container);
		}
		else {
			try{
				costumedInitialization(container, conf);
			}
			catch (InitializationException a){
				game.setInitFailed(true);
				throw new InitializationException(a.getMessage());
			}
		}

		return container;
	}
	
	/**
	 * Initializes the regular aliens in the default configuration
	 * */
	private void initializeRegularAliens(GameObjectContainer container) {
		
		int row = 1, col = 2;
		for (int i = 0; i < this.game.numIniRegAlien(this.level); i++) {
			container.add(new RegularAlien(this.game, new Position(col + i, row), this));

			if ((i + 1) % 4 == 0) {
				row++;
				col = 2 - (i + 1);
			}
			this.remainingAliens++;
		}
	}
	
	/**
	 * Initializes the destroyer aliens in the default configuration
	 * */
	
	private void initializeDestroyerAliens(GameObjectContainer container) {
	
		int num = this.game.numIniDesAlien(this.level);
		int row = this.game.getRowsRegAlien(this.level) + 1, col = 2;
		if (num == 2)
			col = 3;

		for (int i = 0; i < num; i++) {
			container.add(new DestroyerAlien(this.game, new Position(col + i, row), this, i));
			container.add(new Bomb(this.game, new Position(33, 33), 1, this.game.getShootFrec(this.level), i));

			this.remainingAliens++;
		}
	}
	
	/**
	 * Performs the custom initialization
	 * */
	
	private void costumedInitialization(GameObjectContainer container, InitialConfiguration conf) throws InitializationException{
		
		for (String shipDescription : conf.getDescriptions()) {
			String[] words = shipDescription.toLowerCase().trim().split("\\s+");
			
			String word = shipDescription;
			
			if (words.length < 3)
				throw new InitializationException("Incorrect entry \"" + word + "\". Insufficient parameters");
			if (words.length > 3)
				throw new InitializationException("Incorrect entry \"" + word + "\". Overload parameters");
			try {
			if (Integer.parseInt(words[1]) < 0 || Integer.parseInt(words[1]) > Game.DIM_X || Integer.parseInt(words[2]) < 0 || Integer.parseInt(words[2]) > Game.DIM_Y)
				throw new InitializationException("Postition (" + words[1] + ", " + words[2] + ") is off board");
			}
			catch (NumberFormatException a) {
				throw new InitializationException("Invalid position (" + words[1] + ", " + words[2] + ")");
			}
			try{
				AlienShip ship = ShipFactory.spawnAlienShip(words[0], game, new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), this);
				container.add(ship);
				this.remainingAliens++;
			}
			catch (InitializationException a){
				throw new InitializationException(a.getMessage());
			}
		}
	}
	
	public int getRemainingAliens() {
		return this.remainingAliens;
	}
	
	/**
	 * Used for the list command to get the info of the aliens and bombs
	 * 
	 * */
	public String getInfoAliens() {
		StringBuilder str = new StringBuilder();
		RegularAlien regAlien = new RegularAlien(this.game, new Position(Game.DIM_X, Game.DIM_Y), this);
		DestroyerAlien desAlien = new DestroyerAlien(this.game, new Position(Game.DIM_X, Game.DIM_Y), this, -1);
		Ufo ufo = new Ufo(this.game, new Position(Game.DIM_X, Game.DIM_Y), 1);
		
		str.append(regAlien.getInfo()).append(Messages.LINE_SEPARATOR).append(desAlien.getInfo()).append(Messages.LINE_SEPARATOR).append(ufo.getInfo()).append(Messages.LINE_SEPARATOR);
		
		return (str.toString());
	}
	
	/**
	 * Checks if an alien is on the border
	 * 
	 * */
	public boolean onBorder(GameObjectContainer container) {
		boolean onborder = false;
		int i = 0;
		
		while (!onborder && i < container.getNum()) {
			
			if (container.getObjectSymbol(i) == Messages.REGULAR_ALIEN_SYMBOL || container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL)
				if (container.getObjectPosition(i).onBorder())
					onborder = true;
			i++;
		}
		
		return onborder;
	}
	
	public void reduceRem() {
		this.remainingAliens--;
	}
	
}


 