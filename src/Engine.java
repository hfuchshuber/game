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

public class Engine {
	
   
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int KEY_INPUT_SPEED = 10;
    
    public enum gameEnd {
    	CONTINUE, LOST, ADVANCE
    }
	
    private gameEnd status = Engine.gameEnd.CONTINUE;
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private Level myLevel;
    private Group root;
    private Character myPlayer;
    private Scene myScene;
    private Game myGame;
   
    
    public void setgameStatus(gameEnd s) {
    	status = s;
    }
    
    
    public void levelScene(Group r, Scene scene, int level) {
    	root = r; 
    	myScene = scene;
    	myGame = new Game();
    	if (level == 1) {
    		myLevel = new Level1(); 
    	}
    	if (level == 2) {
    		myLevel = new Level2();
    	}
    	root.getChildren().clear();
    	myPlayer = new Character(myScene.getWidth(), myScene.getHeight());
    	myPlayer.setX(myScene.getWidth() / 2 - drawPlayer(myPlayer).getBoundsInLocal().getWidth() / 2);
        myPlayer.setY(myScene.getHeight() - drawPlayer(myPlayer).getBoundsInLocal().getHeight());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    public void gameEngine() {
    	if (status == Engine.gameEnd.CONTINUE) {
    		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
    				e -> step(SECOND_DELAY));
    		Timeline animation = new Timeline();
    		animation.setCycleCount(Timeline.INDEFINITE);
    		animation.getKeyFrames().add(frame);
    		animation.play();
    	}
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
    	if (status == Engine.gameEnd.CONTINUE) { 
    		root.getChildren().add(scoreLabel());
    		myLevel.moveEnemies(enemies, myScene);
    		status = myLevel.checkCollisions(enemies, myPlayer, status);
    		for (int i = 0; i < enemies.size(); i++) {
    			root.getChildren().add(drawEnemy(enemies.get(i)));
    		}
    	}
    	else {
    		myGame.stopGame(root, myScene, status);
    	}
    }
    
	private Label scoreLabel() {
		Label scoreLabel = new Label("Score: " + myLevel.getScore());
		scoreLabel.setTextFill(Color.WHITE);
		return scoreLabel;
	}
    
    public Rectangle drawEnemy(Character enemy) {
		Rectangle visual = new Rectangle(enemy.getX(), enemy.getY(), enemy.getHeight(), enemy.getWidth());
		if (enemy.getType() == Character.Type.ENEMY) {
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
