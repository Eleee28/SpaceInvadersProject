package tp1.logic;

import java.util.Random;
import tp1.control.InitialConfiguration;
import tp1.exception.InitializationException;
import tp1.exception.LaserInFlightException;
import tp1.exception.NotAllowedMoveException;
import tp1.exception.NotEnoughtPointsException;
import tp1.exception.OffWorldException;
import tp1.logic.gameobjects.Bomb;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.ShockWave;
import tp1.logic.gameobjects.SuperLaser;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.gameobjects.UCMShip;
import tp1.logic.gameobjects.Ufo;
import tp1.view.Messages;

public class Game implements GameStatus, GameModel, GameWorld {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	
	private GameObjectContainer container;
	private UCMShip player;
	private AlienManager alienManager;
	private int currentCycle;
	private Level level;
	private UCMLaser laser;
	private SuperLaser superLaser;
	private ShockWave shockwave;
	private Ufo ufo;
	private long seed;
	private int points;
	private boolean doExit;
	private boolean initFailed;

	public Game (Level level, long seed) throws InitializationException {
		this.seed = seed;
		this.level = level;
		this.initFailed = false;
		initGame(InitialConfiguration.NONE);
	}
	
	/**
	 * To initialize the game, create all objects and initialize them
	 * @throws InitializationException 
	 * */
	private void initGame (InitialConfiguration conf) throws InitializationException {	
		this.doExit = false;
		this.currentCycle = 0;
		this.points = 0;
		this.alienManager = new AlienManager(this, level);
		
		try {
			this.container = alienManager.initialize(conf);
			this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), 3);
			this.container.add(player);
			this.shockwave = new ShockWave(this, new Position(DIM_X / 2, DIM_Y - 1), 0);
			this.laser = new UCMLaser(this, new Position(DIM_X + 1, DIM_Y + 1), 0);
			this.container.add(this.laser);
			this.superLaser = new SuperLaser(this, new Position(DIM_X + 2, DIM_Y + 2), 0);
			this.container.add(this.superLaser);
			this.ufo = new Ufo(this, new Position(DIM_X, DIM_Y), 0);
			this.container.add(this.ufo);
		}
		catch (InitializationException a) {
			throw new InitializationException(a.getMessage());
		}
	}

	public void setInitFailed(boolean initFailed) {
		this.initFailed = initFailed;
	}

	//CONTROL METHODS
	public int numIniRegAlien(Level level) {
		return level.getNumRegularAliens();
	}
	
	public int numIniDesAlien(Level level) {
		return level.getNumDestroyerAliens();
	}
	
	public int getRowsRegAlien(Level level) {
		return level.getNumRowsRegularAliens();
	}
	
	/**
	 * Checks if the game is finished
	 * */
	public boolean isFinished() {
		boolean finished = false;
		
		if (this.doExit) {
			finished = true;
		}
		else if (this.initFailed) {
			finished = false;
		}
		else if (this.playerWin() || this.aliensWin())
			finished = true;
		
		return finished;
	}

	public boolean isInitFailed() {
		return this.initFailed;
	}

	public void exit() {
		this.doExit = true;
	}
	
	public void printList() {
		System.out.println(this.player.getInfo());
		System.out.println(this.alienManager.getInfoAliens());
	}
	
	public void reset(InitialConfiguration conf) throws InitializationException {
		try{
			this.initGame(conf);
		}
		catch (InitializationException a) {
			throw new InitializationException(a.getMessage());
		}
	}
	
	/**
	 * Updates a cycle of the game performing all the automatic movements of the objects in the game
	 * 
	 * Performs all the attacks
	 * */
	public void update() {
	    this.currentCycle++;
	    
	    this.action1();
		
	    this.container.computerActions();
	    this.container.automaticMoves();
	    
	    this.bombVSlaser();
		this.laserVSufo();
		this.action2();
		this.bombVSship();
	    
	    if (alienOnBorder()) {
	    	for (int i = 0; i < this.container.getNum(); i++) {
	    		if (this.container.getObjectSymbol(i) == Messages.REGULAR_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.EXPLOSIVE_ALIEN_SYMBOL) {
	    			GameObject object = this.container.getObject(i);
	    			object.setPerformeVertical(true);
	    		}
	    	}
	    }	    
	}
	
	public void action1() {		// to kill aliens with laser or superLaser when they are in the second to last row
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.REGULAR_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.EXPLOSIVE_ALIEN_SYMBOL) {
				if (this.container.getObjectPosition(i).secondLastRow()) {
					
					if (this.container.getObjectPosition(i).equals(this.laser.getPos())) {	// laser attack
						this.laser.performAttack(this.container.getObject(i));
						if (!this.container.getObject(i).isAlive()) {		// check if it is alive
							this.updatePoints(this.container.getObjectPoints(i));
							this.container.getObject(i).onDelete();	
							this.alienManager.reduceRem();
						}
					}
					
					else if (this.container.getObjectPosition(i).equals(this.superLaser.getPos())) {		// super laser attack
						this.superLaser.performAttack(this.container.getObject(i));
						if (!this.container.getObject(i).isAlive()) {		// check if it is alive
							this.updatePoints(this.container.getObjectPoints(i));
							this.container.getObject(i).onDelete();
							this.alienManager.reduceRem();
						}
					}
				}
			}
		}
		
	}
	
	public void action2() { // to kill aliens with laser or superLaser
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.REGULAR_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.EXPLOSIVE_ALIEN_SYMBOL) {
				
				if (this.container.getObjectPosition(i).equals(this.laser.getPos())) {		// laser attack
					this.laser.performAttack(this.container.getObject(i));
					if (!this.container.getObject(i).isAlive()) {		// check if it is alive
						this.updatePoints(this.container.getObjectPoints(i));
						this.container.getObject(i).onDelete();		
						this.alienManager.reduceRem();
					}
				}
				
				else if (this.container.getObjectPosition(i).equals(this.superLaser.getPos())) {		// super laser attack
					this.superLaser.performAttack(this.container.getObject(i));
					if (!this.container.getObject(i).isAlive()) {		// check if it is alive
						this.updatePoints(this.container.getObjectPoints(i));
						this.container.getObject(i).onDelete();		
						this.alienManager.reduceRem();
					}
				}
			}
		}		
	}
	
	public void bombVSlaser() {		// attack between bomb and laser
		for (int i = 0; i < this.container.getNum(); i++) {
			
			if (this.container.getObjectSymbol(i) == Messages.BOMB_SYMBOL) {

				if (this.container.getObjectPosition(i).compBombLaser(this.laser.getPos())) {	// laser attack
					this.container.getObject(i).receiveAttack(this.laser);
					this.laser.receiveAttack((Bomb)this.container.getObject(i));
				}
				
				if (this.container.getObjectPosition(i).compBombLaser(this.superLaser.getPos())) {		// super laser attack
					this.container.getObject(i).receiveAttack(this.superLaser);
					this.superLaser.receiveAttack((Bomb)this.container.getObject(i));
				}
				
				if (this.container.getObjectPosition(i).secondLastRow()) {		// when the bomb is in the second to last row
					
					if (this.container.getObjectPosition(i).compBombLaser(this.laser.getPos())) {	// laser attack
						this.container.getObject(i).receiveAttack(this.laser);
						this.laser.receiveAttack((Bomb)this.container.getObject(i));
					}
					
					if (this.container.getObjectPosition(i).compBombLaser(this.superLaser.getPos())) {		// super laser attack
						this.container.getObject(i).receiveAttack(this.superLaser);
						this.superLaser.receiveAttack((Bomb)this.container.getObject(i));
					}
				}
			}	
		}
	}
	
	public void laserVSufo() {		// laser attack on the ufo
		if (this.laser.equalPos(this.ufo.getPos())) {		// laser attack
			this.updatePoints(this.ufo.getPoints());
				
			this.ufo.receiveDamage(this.player.getDamage());
			
			if (!this.ufo.isAlive()) 
				this.shockwave.setEnable(true);
			
			this.laser.die();
			
		}
		
		else if (this.superLaser.equalPos(this.ufo.getPos())) {		// super laser attack
			this.updatePoints(this.ufo.getPoints());
			
			this.ufo.receiveDamage(this.player.getDamage());
			
			if (!this.ufo.isAlive()) 
				this.shockwave.setEnable(true);
			
			this.superLaser.die();
		
		}
	}
	
	public void bombVSship() {		// bomb attack on the UCMShip
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectSymbol(i) == Messages.BOMB_SYMBOL)
				if (this.container.getObjectPosition(i).equals(this.player.getPos()))
					this.player.updateLife(this.container.getObjectDamage(i));
		}
	}
	
	/**
	 * Performs the explosion damage of the Explosive Ship on the surrounding objects
	 * 
	 */
	public void explosiveDamage(Position pos) {
		for (int k = 0; k < this.container.getNum(); k++)
			for (int i = -1; i < 2; i++)
				for (int j = -1; j < 2; j++) {
					if (this.container.getObject(k).equalPos(pos.getRow() + i, pos.getCol() + j) && !this.container.getObjectPosition(k).equals(pos))
						this.container.getObject(k).reduceLife();
				}
	}
	
	public void updatePoints(int points) {
		this.points += points;
	}
	
	/**
	 * Gets a random number with the game seed
	 * */
	public Random getRandom() {
		Random ran = new Random();
		return ran;
	}

	//CALLBACK METHODS
	
	public void addObject(GameObject object) {
	    this.container.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.container.remove(object);
	}
	
	//VIEW METHODS
	
	/**
	 * Used for printing the gameBoard
	*/
	public String positionToString(int col, int row) {
		String str = "   ";
		Position pos = new Position(col, row);
		
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectPosition(i).equals(pos)) {
				switch (this.container.getObjectSymbol(i)) {
					case Messages.LASER_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.UFO_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.REGULAR_ALIEN_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.DESTROYER_ALIEN_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.UCMSHIP_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.SUPER_LASER_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.EXPLOSIVE_ALIEN_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
					case Messages.BOMB_SYMBOL:
						str = this.container.getObject(i).toString();
						break;
				}		
			}		
		}
		
		return (str);
	}
	
	@Override
	public String infoToString() {return null;}

	/**
	 * Used for printing the game information
	 * */
	@Override
	public String stateToString() {
		StringBuilder str = new StringBuilder();
		
		str.append("Life:").append(" ").append(this.player.getLife()).append(System.lineSeparator()).append("Points:")
		.append(" ").append(this.points).append(System.lineSeparator()).append("ShockWave:").append(" ")
		.append(this.shockwave.getEnabled() ? "ON" : "OFF").append(System.lineSeparator());

		return (str.toString());
	}

	@Override
	public boolean playerWin() {
		return (this.getRemainingAliens() == 0);
	}

	@Override
	public boolean aliensWin() {
		boolean win = false;
		
		if (alienInFinalRow())
			win = true;
		
		if (this.player.getLife() == 0) {
			this.player.setSymbol(Messages.UCMSHIP_DEAD_SYMBOL);
			win = true;
		}
		
		return win;
	}

	@Override
	public int getCycle() {
		return (this.currentCycle);
	}

	@Override
	public int getRemainingAliens() {
		return (this.alienManager.getRemainingAliens());
	}
	
	public Level getLevel() {
		return (this.level);
	}
	
	public int speedLevel(Level level) {
		return (level.getNumCyclesToMoveOneCell());
	}
	
	public int getShootFrec(Level level) {
		return (int) level.getShootFrequency();
	}
	
	/**
	 * Performs the movement of the player
	 * */

	@Override
	public void move(Move move) throws OffWorldException, NotAllowedMoveException {
		this.player.move(move);
	}

	@Override
	public void shootLaser() throws LaserInFlightException {
		if (this.laser.getEnabled())
			throw new LaserInFlightException(Messages.L_IN_BOARD);
		else {
			this.laser.initLaser(new Position(this.player.getCol(), this.player.getRow() - 1), 1);	// initialize the laser
		}
		
	}
	
	/**
	 * Checks if there are aliens in the last row
	 * */
	public boolean alienInFinalRow() {
		boolean finalRow = false;
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectSymbol(i) == Messages.REGULAR_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL)
				if (this.container.getObjectPosition(i).onLastRow())
					finalRow = true;
		}
		return (finalRow);
	}
	
	/**
	 * Checks if there are aliens on the border 
	 * */
	public boolean alienOnBorder() {
		return this.alienManager.onBorder(this.container);
	}
	
	/**
	 * Searchs for a destroyer alien
	 * @param id of the destroyer alien
	 * 
	 * */
	public int searchDestroyer(int id) {
		int idx = -1;
		
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL) {
				GameObject object = this.container.getObject(i);
				if (object.getId() == id)
					idx = i;
			}
		}
		
		return idx;
	}
	
	public Position posDestroyer(int idx) {
		return this.container.getObjectPosition(idx);
	}
	
	/**
	 * Deploys bombs when possible, each of the associated to a destroyer alien
	 * 
	 * */
	public void deploy() {
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObjectSymbol(i) == Messages.BOMB_SYMBOL) {
				GameObject object = this.container.getObject(i);
				if (!object.getEnabled()) {
					if (shoot()) {
						object.setDeploy(true);
						object.setEnable(true);
						object.setPos(this.container.getObjectPosition(searchDestroyer(object.getId())));
					}
				}
			}
		}
	}
	
	/**
	 * Destroyer alien's shooting
	 * 
	 * */
	public boolean shoot() {
		boolean shot = false;
		int min = 1;
		int max = (int) (1 / this.level.getShootFrequency());

		if (min + this.getRandom().nextInt(max - min + 1) == 1) {
			shot = true;
		}
		return shot;
	}
	
	public boolean enabledShockwave() {
		return (this.shockwave.getEnabled());
	}
	
	public void shockwaveAttack() {
		for (int i = 0; i < this.container.getNum(); i++) {
			if (this.container.getObject(i).isAlive())
				if (this.container.getObjectSymbol(i) == Messages.REGULAR_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.DESTROYER_ALIEN_SYMBOL || this.container.getObjectSymbol(i) == Messages.EXPLOSIVE_ALIEN_SYMBOL) {
					this.container.getObject(i).receiveAttack(this.shockwave);
					if (!this.container.getObject(i).isAlive())
						this.alienManager.reduceRem();
				}
		}
	}
	
	public void disableShockwave() {
		this.shockwave.setEnable(false);;
	}
	
	public void shootSuperLaser() throws LaserInFlightException, NotEnoughtPointsException {
		if (this.superLaser.getEnabled())
			throw new LaserInFlightException(Messages.SL_IN_BOARD);
		else if (this.points < 5)
			throw new NotEnoughtPointsException(Messages.errorPointsDisplay(this.points, 5));
		
		this.superLaser.initLaser(new Position(this.player.getCol(), this.player.getRow() - 1), 1);		// initializes superLaser
		this.points -= 5;
	}
	
}
