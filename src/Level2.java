import java.util.ArrayList;

import javafx.scene.Scene;

public class Level2 extends Level {
	
	private static final int ENEMY_SIZE = 10;
	private static final int WIN_SCORE = 50;
	private static final int ENEMY_SPEED = 3;
	
	private int score = 0;
	private Engine myEngine = new Engine();
	
	@Override
	public void moveEnemies(ArrayList<Character> enemies, Scene scene) {
		if (Math.random() > .96 && enemies.size() < 30 || enemies.size() < 2) {
			spawnEnemies(enemies, scene.getWidth());
		}
    	for (int i = 0; i < enemies.size(); i++) {
    		//move down screen
        	enemies.get(i).setY(enemies.get(i).getY() + ENEMY_SPEED);
        	//delete unused ones
        	if (enemies.get(i).getY() > scene.getHeight()) {
        		enemies.remove(i);
        	}
        }
    }


	private void spawnEnemies(ArrayList<Character> enemies, double width) {
		Character temp = new Character(width * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, Character.Type.ENEMY);
		enemies.add(temp);
		Character temp2 = new Character(width * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, Character.Type.FOOD);
		enemies.add(temp2);
	}
	
	
	@Override
	public Engine.gameEnd checkCollisions(ArrayList<Character> enemies, Character player, Engine.gameEnd status) {
    	for (int i = 0; i < enemies.size(); i++) {
    		if (myEngine.drawEnemy(enemies.get(i)).intersects(myEngine.drawPlayer(player).getBoundsInParent())) {
    				if (enemies.get(i).getType() == Character.Type.ENEMY) {
    					status = Engine.gameEnd.LOST;
    				} else {
    					enemies.remove(i);
    					score++;
    				}
    		}
    	}
    	if (score > WIN_SCORE) {
    		status = Engine.gameEnd.WIN;
    	}
    	return status;
    }
	
	@Override
	public int getScore() {
		return score;
	}

}