

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Hannah Fuchshuber
 * Originally written by Robert Duvall
 */
class GameEngine {
    private static final String TITLE = "Dodgeblocks";
    private static final int KEY_INPUT_SPEED = 10;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int SIZE = 400;

    private Scene myScene;
    private Group root;
    private boolean gameOver = false;
    private int level = 1;
    private Stage myStage;
    private Timeline animation;
    private Levels myLevel;
    private Character myPlayer;
    
    public void isGameOver(boolean b) {
    	gameOver = b;
    }


    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }
    
    public double getSceneWidth() {
    	return myScene.getWidth();
    }
    
    public double getSceneHeight() {
    	return myScene.getHeight();
    }
    
    public Group getRoot() {
    	return root;
    }
    
    public Character getPlayer() {
    	return myPlayer;
    }

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
    	//pass the stage 
    	root = new Group();
    	// create a place to see the shapes
    	myScene = new Scene(root, width, height, Color.BLACK);
        // make some shapes and set their properties
        // x and y represent the top left corner, so center it
    	myPlayer = new Character(width, height);
    	myPlayer.setX(width / 2 - myLevel.drawPlayer(myPlayer).getBoundsInLocal().getWidth() / 2);
        myPlayer.setY(height - myLevel.drawPlayer(myPlayer).getBoundsInLocal().getHeight());
        // order added to the group is the order in which they are drawn
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myStage.setScene(myScene);
        myStage.show();
        splashScreen();
        return myScene;
    }
    
    public void gameEngine() {
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> myLevel.step(SECOND_DELAY));
    	animation = new Timeline();
    	animation.setCycleCount(Timeline.INDEFINITE);
    	animation.getKeyFrames().add(frame);
    	animation.play();
    }
    
    public void splashScreen() {
    	Button start = new Button("hi");
    	start.setOnAction(e -> restartGame(myStage));
    	root.getChildren().add(start);
    }
    
    public void stopGame() {
    	animation.stop();
    	String txt = "";
    	if (!gameOver && level == 1) {
    			level = 2;
    			txt = "Start Next Level";
    	} else {
    		level = 1;
        	txt = "Restart";
    	}
    	Button newgamebutton = new Button(txt);
    	newgamebutton.setOnAction(e -> restartGame(myStage));
    	root.getChildren().add(newgamebutton);
    }
    
    
    
	public void restartGame(Stage s) {
		gameOver = false;
		myLevel.setstopGame(false);
		myLevel.setScore(0);
		root.getChildren().clear();
		init((int) myScene.getHeight(), (int) myScene.getWidth());
		gameEngine();
	}
	
	
	public void startGame(Stage s) {
		myStage = s;
		myLevel = new Levels();
		Scene scene = init((int) SIZE, (int) SIZE);
		myStage.setScene(scene);
		myStage.show();
	}
	
    
	// What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	
        switch (code) {
            case RIGHT:
            	if (myPlayer.getX() < myScene.getWidth() - myLevel.drawPlayer(myPlayer).getBoundsInParent().getWidth()) {
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
            	if (myPlayer.getY() < myScene.getHeight() - myLevel.drawPlayer(myPlayer).getBoundsInParent().getHeight()) {
            		myPlayer.setY(myPlayer.getY() + KEY_INPUT_SPEED);
            	}
            	break;
            default:
                // do nothing
        
    	}
    }
}


