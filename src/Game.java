
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Hannah Fuchshuber
 * Originally written by Robert Duvall
 */
class Game {
    private static final String TITLE = "Dodgeblocks";
    private static final int KEY_INPUT_SPEED = 10;
    private static final int ENEMY_SPEED = 1;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int WIN_SCORE = 5;
    private static final int ENEMY_SIZE = 10;

    private Scene myScene;
    private Character myPlayer;
    private Group root;
    private ArrayList<Character> enemies = new ArrayList<Character>();  
    private boolean gameOver = false;
    private boolean stopGame = false;
    private int level = 1;
    private Stage myStage;
    private Image image;
    private Timeline animation;
    private int counter;


    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (Stage s, int width, int height) {
    	//pass the stage 
    	myStage = s;
    	// create a place to see the shapes
    	root = new Group();
    	myScene = new Scene(root, width, height, Color.BLACK);
        // make some shapes and set their properties
        //Image image = new Image(getClass().getClassLoader().getResourceAsStream("tinyrocket.png"));
        //myPlayer = new ImageView(image);
        // x and y represent the top left corner, so center it
    	myPlayer = new Character(width, height);
    	image = new Image(getClass().getClassLoader().getResourceAsStream("tinyrocket.png"));
        myPlayer.setX(width / 2 - drawPlayer(myPlayer, image).getBoundsInLocal().getWidth() / 2);
        myPlayer.setY(height - drawPlayer(myPlayer, image).getBoundsInLocal().getHeight());
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
                e -> step(SECOND_DELAY));
    	animation = new Timeline();
    	animation.setCycleCount(Timeline.INDEFINITE);
    	animation.getKeyFrames().add(frame);
    	animation.play();
    }
    
    public void splashScreen() {
    	Button start = new Button("hi");
    	start.setOnAction(e -> restartGame());
    	root.getChildren().add(start);
    }
    

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
    	root.getChildren().clear();
    	root.getChildren().add(drawPlayer(myPlayer, image));
    	if (!stopGame) { 
    		scoreLabel();
    		moveEnemies();
    		checkCollisions();
    	}
    	else {
    		scoreLabel();
    		stopGame();
    	}
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
    
    public ImageView drawPlayer(Character player, Image image) {
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
        	root.getChildren().add(drawEnemy(enemies.get(i)));
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
    


	

	public void spawnEnemies() {
    	Character temp = new Character(myScene.getWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, true);
		enemies.add(temp);
		root.getChildren().add(drawEnemy(temp));
		if (level == 2) {
			Character temp2 = new Character(myScene.getWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, false);
			enemies.add(temp2);
			root.getChildren().add(drawEnemy(temp2));
		}
	}
    
    
    public void checkCollisions() {
    	for (int i = 0; i < enemies.size(); i++) {
    		if (drawEnemy(enemies.get(i)).intersects(drawPlayer(myPlayer, image).getBoundsInParent())) {
    				if (enemies.get(i).getType()) {
    					stopGame = true;
    					gameOver = true;
    				} else {
    					enemies.remove(i);
    					counter++;
    				}
    		}
    	}
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
    	newgamebutton.setOnAction(e -> restartGame());
    	root.getChildren().add(newgamebutton);
    }
    
    
    
	private void restartGame() {
		gameOver = false;
		stopGame = false;
		counter = 0;
		enemies = new ArrayList<Character>();
		root.getChildren().clear();
		Scene s = init(myStage, (int) myScene.getWidth(), (int) myScene.getHeight());
		myStage.setScene(s);
		myStage.show();
		gameEngine();
	}
	
	private void scoreLabel() {
		Label scoreLabel = new Label("Score: " + counter);
		scoreLabel.setTextFill(Color.WHITE);
		root.getChildren().add(scoreLabel);
	}

	
    
	// What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	
        switch (code) {
            case RIGHT:
            	if (myPlayer.getX() < myScene.getWidth() - drawPlayer(myPlayer, image).getBoundsInParent().getWidth()) {
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
            	if (myPlayer.getY() < myScene.getHeight() - drawPlayer(myPlayer, image).getBoundsInParent().getHeight()) {
            		myPlayer.setY(myPlayer.getY() + KEY_INPUT_SPEED);
            	}
            	break;
            default:
                // do nothing
        
    	}
    }
}

