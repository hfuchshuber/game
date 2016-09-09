

//import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Hannah Fuchshuber
 * Originally written by Robert Duvall
 */
class GameEngine {
    private static final String TITLE = "Dodgeblocks";
    private static final int SIZE = 400;
    
    private Scene myScene;
    private Group root;
    private boolean gameOver = false;
    private int level = 1;
    private Stage myStage;
    //private Timeline animation;
    private Levels myLevel;
    //private Character myPlayer;
    
    public void isGameOver(boolean b) {
    	gameOver = b;
    }


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
    	myLevel = new Levels();
    	myScene = new Scene(root, width, height, Color.BLACK);
        // make some shapes and set their properties
        // x and y represent the top left corner, so center it
    	//myPlayer = new Character(width, height);
    	//myPlayer.setX(width / 2 - myLevel.drawPlayer(myPlayer).getBoundsInLocal().getWidth() / 2);
        //myPlayer.setY(height - myLevel.drawPlayer(myPlayer).getBoundsInLocal().getHeight());
        // order added to the group is the order in which they are drawn
        // respond to input
        //myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myStage.setScene(myScene);
        myStage.show();
        splashScreen();
        return myScene;
    }
    
    
    public void splashScreen() {
    	Button start = new Button("hi");
    	start.setOnAction(e -> restartGame(myStage));
    	root.getChildren().add(start);
    }
    
    
    
    public void stopGame(Group r, Scene s) {
    	root = r;
    	myScene = s;
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
		myLevel = new Levels();
		myLevel.setstopGame(false);
		myLevel.setScore(0);
		//root.getChildren().clear();
		//init(myStage, (int) myScene.getHeight(), (int) myScene.getWidth());
		myLevel.levelScene(root, myScene);
		myLevel.gameEngine();
	}
	
	
	public void startGame(Stage s) {
		myStage = s;
		myLevel = new Levels();
		Scene scene = init(myStage, (int) SIZE, (int) SIZE);
		myStage.setScene(scene);
		myStage.show();
	}
	
    
	// What to do each time a key is pressed

}


