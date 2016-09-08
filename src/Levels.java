import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Levels {
	
    private static final int ENEMY_SPEED = 1;
    private static final int ENEMY_SIZE = 10;
    private static final int WIN_SCORE = 5;
	

    private boolean stopGame = false;
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private int counter;
    private GameEngine myGame = new GameEngine();
    private int level = 1;
    
    public void setScore(int s) {
    	counter = s;
    }
    
    public void setstopGame(boolean b) {
    	stopGame = b;
    }

    
    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
    	System.out.println(myGame.getRoot());
    	myGame.getRoot().getChildren().clear();
    	myGame.getRoot().getChildren().add(drawPlayer(myGame.getPlayer()));
    	if (!stopGame) { 
    		scoreLabel();
    		moveEnemies();
    		checkCollisions();
    	}
    	else {
    		scoreLabel();
    		myGame.stopGame();
    	}
    }
    
	private void scoreLabel() {
		Label scoreLabel = new Label("Score: " + counter);
		scoreLabel.setTextFill(Color.WHITE);
		myGame.getRoot().getChildren().add(scoreLabel);
	}
    
    public Rectangle drawEnemy(Character enemy) {
		Rectangle visual = new Rectangle(enemy.getX(), enemy.getY(), enemy.getHeight(), enemy.getWidth());
		if (enemy.getType()) {
			visual.setFill(Color.RED);
		} else {
			visual.setFill(Color.GREEN);
		}
		return visual;
	}
    
    public ImageView drawPlayer(Character player) {
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream("tinyrocket.png"));
        ImageView visual = new ImageView(image);
        visual.setX(player.getX());
        visual.setY(player.getY());
    	return visual; 
    }
    

	public void moveEnemies() {
		if (Math.random() > .99 && enemies.size() < 30 || enemies.size() < 2) {
			spawnEnemies();
		}
    	for (int i = 0; i < enemies.size(); i++) {
    		//move down screen
        	enemies.get(i).setY(enemies.get(i).getY() + ENEMY_SPEED);
        	myGame.getRoot().getChildren().add(drawEnemy(enemies.get(i)));
        	//delete unused ones
        	if (enemies.get(i).getY() > myGame.getSceneHeight()) {
        		removeEnemies(i);
        		if (level == 1) {
        			counter++;
        		}
        	}
        	if (counter > WIN_SCORE) {
        		stopGame = true;
        	}
        }
    }
	

    
    private void removeEnemies(int count) {
		myGame.getRoot().getChildren().clear();
		enemies.remove(count);
	}


	

	public void spawnEnemies() {
    	Character temp = new Character(myGame.getSceneWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, true);
		enemies.add(temp);
		myGame.getRoot().getChildren().add(drawEnemy(temp));
		if (level == 2) {
			Character temp2 = new Character(myGame.getSceneWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, false);
			enemies.add(temp2);
			myGame.getRoot().getChildren().add(drawEnemy(temp2));
		}
	}
    
    
    public void checkCollisions() {
    	for (int i = 0; i < enemies.size(); i++) {
    		if (drawEnemy(enemies.get(i)).intersects(drawPlayer(myGame.getPlayer()).getBoundsInParent())) {
    				if (enemies.get(i).getType()) {
    					stopGame = true;
    					myGame.isGameOver(true);
    				} else {
    					removeEnemies(i);
    					counter++;
    				}
    		}
    	}
    }
   
}
