import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Levels {
	
    private static final int ENEMY_SPEED = 1;
    private static final int ENEMY_SIZE = 10;
    private static final int WIN_SCORE = 5;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int KEY_INPUT_SPEED = 10;
	

    private boolean stopGame = false;
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private int counter;
    private GameEngine myGame = new GameEngine();
    private int level;
    private Group root;
    private Character myPlayer;
    private Scene myScene;
   
    public void setScore(int s) {
    	counter = s;
    }
    
    public void setstopGame(boolean b) {
    	stopGame = b;
    }
    
    public Character getPlayer() {
    	return myPlayer;
    }
    
    public void levelScene(Group r, Scene scene) {
    	root = r; 
    	myScene = scene;
    	root.getChildren().clear();
    	myPlayer = new Character(myScene.getWidth(), myScene.getHeight());
    	myPlayer.setX(myScene.getWidth() / 2 - drawPlayer(myPlayer).getBoundsInLocal().getWidth() / 2);
        myPlayer.setY(myScene.getHeight() - drawPlayer(myPlayer).getBoundsInLocal().getHeight());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        // order added to the group is the order in which they are drawn
        // respond to input
    }

    public void gameEngine() {
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> step(SECOND_DELAY));
    	Timeline animation = new Timeline();
    	animation.setCycleCount(Timeline.INDEFINITE);
    	animation.getKeyFrames().add(frame);
    	animation.play();
    }
    
    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
    	root.getChildren().clear();
    	root.getChildren().add(drawPlayer(myPlayer));
    	if (!stopGame) { 
    		root.getChildren().add(scoreLabel());
    		moveEnemies();
    		checkCollisions();
    		for (int i = 0; i < enemies.size(); i++) {
    			root.getChildren().add(drawEnemy(enemies.get(i)));
    		}
    	}
    	else {
    		scoreLabel();
    		myGame.stopGame(root, myScene);
    	}
    }
    
	public void spawnEnemies() {
    	Character temp = new Character(myScene.getWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, true);
		enemies.add(temp);
		if (level == 2) {
			Character temp2 = new Character(myScene.getWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, false);
			enemies.add(temp2);
		}
	}
    
	private Label scoreLabel() {
		Label scoreLabel = new Label("Score: " + counter);
		scoreLabel.setTextFill(Color.WHITE);
		return scoreLabel;
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
        	//delete unused ones
        	if (enemies.get(i).getY() > myScene.getHeight()) {
        		enemies.remove(i);
        		if (level == 1) {
        			counter++;
        		}
        	}
        	if (counter > WIN_SCORE) {
        		stopGame = true;
        	}
        }
    }
	

   

    
    
    public void checkCollisions() {
    	for (int i = 0; i < enemies.size(); i++) {
    		if (drawEnemy(enemies.get(i)).intersects(drawPlayer(myPlayer).getBoundsInParent())) {
    				if (enemies.get(i).getType()) {
    					stopGame = true;
    					myGame.isGameOver(true);
    				} else {
    					enemies.remove(i);
    					counter++;
    				}
    		}
    	}
    }
    
    private void handleKeyInput (KeyCode code) {
    	
        switch (code) {
            case RIGHT:
            	if (myPlayer.getX() < myScene.getWidth() - drawPlayer(myPlayer).getBoundsInParent().getWidth()) {
            		myPlayer.setX(myPlayer.getX() + KEY_INPUT_SPEED);
            	}
            	break;
            case LEFT:
            	if (myPlayer.getX() > 0) {
            		myPlayer.setX(myPlayer.getX() - KEY_INPUT_SPEED);
            	}
            	break;
            case UP:
            	if (myPlayer.getY() > 0) {
            		myPlayer.setY(myPlayer.getY() - KEY_INPUT_SPEED);
            	}
            	break;
            case DOWN:
            	if (myPlayer.getY() < myScene.getHeight() - drawPlayer(myPlayer).getBoundsInParent().getHeight()) {
            		myPlayer.setY(myPlayer.getY() + KEY_INPUT_SPEED);
            	}
            	break;
            default:
                // do nothing
        
    	}
    }

   
}
