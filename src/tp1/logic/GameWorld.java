package tp1.logic;

import java.util.Random;

// Game World is implemented for GameObject

public interface GameWorld {
	public Level getLevel();
	public int speedLevel(Level level);
	public boolean shoot();
	public Position posDestroyer(int idx);
	public int searchDestroyer(int id);
	public Random getRandom() ;
	public void explosiveDamage(Position pos);
}
