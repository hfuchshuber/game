// This entire file is part of my masterpiece.
// HANNAH FUCHSHUBER



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

/**
 * This is the Engine class, used to run the animation of game play.
 * 
 * @author Hannah Fuchshuber
 */

public class Engine {
	
   
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final int KEY_INPUT_SPEED = 15;
    
    public enum gameEnd {
    	CONTINUE, LOST, ADVANCE, WIN
    }
	
    private gameEnd status = Engine.gameEnd.CONTINUE;
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private Level myLevel;
    private Group root;
    private Character myPlayer;
    private Scene myScene;
    private Game myGame;
    private Timeline animation;
    Image charImage = new Image(getClass().getClassLoader().getResourceAsStream("tinyrocket.png"));
   
    

    
    /**
     * Sets up the scene
     * 
     * @param Group
     * @param Scene
     * @param int
     */
    public void levelScene(Group r, Scene scene, int level) {
    	root = r; 
    	myScene = scene;
    	root.getChildren().clear();
    	myGame = new Game();
    	if (level == 1) {
    		myLevel = new Level1(); 
    	}
    	if (level == 2) {
    		myLevel = new Level2();
    	}
    	myPlayer = new Character(myScene.getWidth(), myScene.getHeight());
    	myPlayer.setX(myScene.getWidth() / 2 - drawPlayer(myPlayer).getBoundsInLocal().getWidth() / 2);
        myPlayer.setY(myScene.getHeight() - drawPlayer(myPlayer).getBoundsInLocal().getHeight());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        gameEngine();
    }

    /**
     * Starts the timeline for each animation cycle
     */
    private void gameEngine() {
    	if (status == Engine.gameEnd.CONTINUE) {
    		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
    				e -> step());
    		animation = new Timeline();
    		animation.setCycleCount(Timeline.INDEFINITE);
    		animation.getKeyFrames().add(frame);
    		animation.playFromStart();
    	}
    }
    
    /**
     * Change properties of shapes to animate them
     * 
     */
    private void step () {
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
    		animation.stop();
    		myGame.stopGame(root, myScene, status);
    	}
    }
    
	/**
	 * Creates score label 
	 * @return Label
	 */
	private Label scoreLabel() {
		Label scoreLabel = new Label("Score: " + myLevel.getScore());
		scoreLabel.setTextFill(Color.WHITE);
		return scoreLabel;
	}
    
    /**
     * Creates rectangles for enemies
     * 
     * @param enemy
     * @return Rectangle
     */
    public Rectangle drawEnemy(Character enemy) {
		Rectangle visual = new Rectangle(enemy.getX(), enemy.getY(), enemy.getHeight(), enemy.getWidth());
		if (enemy.getType() == Character.Type.ENEMY) {
			visual.setFill(Color.RED);
		} else {
			visual.setFill(Color.GREEN);
		}
		return visual;
	}
    
    /**
     * Creates picture for Player
     * 
     * @param Character
     * @return ImageView
     */
    public ImageView drawPlayer(Character player) {
        ImageView visual = new ImageView(charImage);
        visual.setX(player.getX());
        visual.setY(player.getY());
    	return visual; 
    }
    
    /**
     * Implements Player's movement
     * 
     * @param KeyCode
     */
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
