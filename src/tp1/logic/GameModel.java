package tp1.logic;

import java.util.Random;

import tp1.control.InitialConfiguration;
import tp1.exception.InitializationException;
import tp1.exception.LaserInFlightException;
import tp1.exception.NotAllowedMoveException;
import tp1.exception.NotEnoughtPointsException;
import tp1.exception.OffWorldException;

// Game Model is implemented for Controller

public interface GameModel {

	public boolean isFinished();
	// PLAYER ACTIONS
	public void move(Move move) throws OffWorldException, NotAllowedMoveException;
	public void shootLaser() throws LaserInFlightException;
	public void reset(InitialConfiguration conf) throws InitializationException;
	public void exit();
	public void printList();
	public Random getRandom();
	public void update();
	public boolean enabledShockwave();
	public void shockwaveAttack();
	public void disableShockwave();
	public void shootSuperLaser() throws LaserInFlightException, NotEnoughtPointsException;
}