import java.util.ArrayList;
import javafx.scene.Scene;

/**
 * This is the Level1 class, used to specify conditions for Level 1.
 * 
 * @author Hannah Fuchshuber
 */

public class Level1 extends Level {
	
	private static final int ENEMY_SIZE = 10;
	private static final int WIN_SCORE = 20;
	private static final int ENEMY_SPEED = 1;
	private static final double SPAWN_RATE = 0.99;
	
	private int score = 0;
	private Engine myEngine = new Engine();
	
	
	/**
	 * This moves the enemies down the screen and deletes then when they are no longer needed
	 * 
	 * @param ArrayList<Character>
	 * @param Scene
	 */
	@Override
	public void moveEnemies(ArrayList<Character> enemies, Scene scene) {
		if (Math.random() > SPAWN_RATE && enemies.size() < 30 || enemies.size() < 2) { //the magic number is to determine spawn rate
			spawnEnemies(enemies, scene.getWidth());
		}
    	for (int i = 0; i < enemies.size(); i++) {
    		//move down screen
        	enemies.get(i).setY(enemies.get(i).getY() + ENEMY_SPEED);
        	//delete unused ones
        	if (enemies.get(i).getY() > scene.getHeight()) {
        		enemies.remove(i);
        		score++;
        	}
        }
    }

	/**
	 * This makes the enemies at the top of screen
	 * 
	 * @param ArrayList<Character>
	 * @param double
	 */
	private void spawnEnemies(ArrayList<Character> enemies, double width) {
		Character temp = new Character(width * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, Character.Type.ENEMY);
		enemies.add(temp);
	}
	
	
	/**
	 * Checks collisions between characters and returns condition of game
	 * 
	 * @param ArrayList<Character>
	 * @param Character
	 * @param Engine.gameEnd
	 */
	@Override
	public Engine.gameEnd checkCollisions(ArrayList<Character> enemies, Character player, Engine.gameEnd status) {
    	for (int i = 0; i < enemies.size(); i++) {
    		if (myEngine.drawEnemy(enemies.get(i)).intersects(myEngine.drawPlayer(player).getBoundsInParent())) {
    				if (enemies.get(i).getType() == Character.Type.ENEMY) {
    					status = Engine.gameEnd.LOST;
    				} else {
    					enemies.remove(i);
    				}
    		}
    	}
    	if (score > WIN_SCORE) {
    		status = Engine.gameEnd.ADVANCE;
    	}
    	return status;
    }
	
	/**
	 * Gets the current score
	 * 
	 * @return int
	 */
	@Override
	public int getScore() {
		return score;
	}
	

}
