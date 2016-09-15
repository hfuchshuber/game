import java.util.ArrayList;

/**
 * This is the abstract Level class, used to ensure similar implementation in subclasses.
 * 
 * @author Hannah Fuchshuber
 */

import javafx.scene.Scene;

public abstract class Level {
	
	/**
	 * Checks collisions between characters and returns condition of game
	 * 
	 * @param ArrayList<Character>
	 * @param Character
	 * @param Engine.gameEnd
	 */
    abstract Engine.gameEnd checkCollisions(ArrayList<Character> enemies, Character player, Engine.gameEnd status);
    
    /**
	 * This moves the enemies down the screen and deletes then when they are no longer needed
	 * 
	 * @param ArrayList<Character>
	 * @param Scene
	 */
    abstract void moveEnemies(ArrayList<Character> enemies, Scene scene);
	
	/**
	 * Gets the current score
	 * 
	 * @return int
	 */
    abstract int getScore();
}
