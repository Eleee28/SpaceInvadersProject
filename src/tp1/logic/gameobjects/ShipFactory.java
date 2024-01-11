package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;
import tp1.exception.InitializationException;
import tp1.logic.AlienManager;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public class ShipFactory {

	private static final List<AlienShip> AVAILABLE_ALIEN_SHIPS = Arrays.asList(
			new RegularAlien(),
			new DestroyerAlien(),
			new ExplosiveShip()
		);
	
	public static AlienShip spawnAlienShip(String input, GameWorld game, Position pos, AlienManager am) throws InitializationException{
		AlienShip alien = null;
		boolean check = false;
		
		for (AlienShip a: AVAILABLE_ALIEN_SHIPS) {
			if (a.getSymbol().equalsIgnoreCase(input.toUpperCase())) {
				alien = a.copy(game, pos, am);
				check = true;
			}
		}
		if (!check)
			throw new InitializationException("Unknown ship: \"" + input + "\"");
		return alien;
	}
}
