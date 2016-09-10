

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Hannah Fuchshuber
 * Originally written by Robert Duvall
 */
class Game {
    private static final String TITLE = "Dodgeblocks";
    
    private Scene myScene;
    private Group root;
    private int level = 1;
    private Stage myStage;
    private Engine myEngine;


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
    	root = new Group();
    	myEngine = new Engine();
    	myScene = new Scene(root, width, height, Color.BLACK);
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
    
    
    
    public void stopGame(Group r, Scene s, Engine.gameEnd status) {
    	root = r;
    	myScene = s;
    	String txt = "";
    	if (status == Engine.gameEnd.ADVANCE) {
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
		myEngine = new Engine();
		myEngine.levelScene(root, myScene, level);
		myEngine.gameEngine();
	}
	
    

}


