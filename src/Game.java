import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


/**
 * This is the Game class, used to control the different screens and control higher-level conditions of the game.
 * 
 * @author Hannah Fuchshuber
 * Originally written by Robert Duvall
 */

class Game {
    private static final String TITLE = "Dodgeblocks";
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    
    private Scene myScene;
    private Group root;
    private int level = 1;
    private Stage myStage;
    private Engine myEngine;
    private String cheatCode = ""; 
    private Timeline animation;



    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }
    

    /**
     * Create the game's scene
     * 
     * @param Stage
     * @param int
     * @param height
     */
    public Scene init (Stage s, int width, int height) {
    	//pass the stage 
    	myStage = s;
    	root = new Group();
    	myEngine = new Engine();
    	myScene = new Scene(root, width, height, Color.BLACK);
        myStage.setScene(myScene);
        myStage.show();
        setScreen("start.png");
        return myScene;
    }
    
	/**
	 * This sets the timeline animation for the screens
	 * 
	 * @param String
	 */
    private void checkInput(String file) {
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
    			e -> checkCheatCode(file));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
	/**
	 * This checks the condition of the game
	 * 
	 * @param String
	 */
    private void checkCheatCode(String file) {
    	if (cheatCode.equals("lvltwo")) {
        	animation.stop();
        	level = 2;
        	setScreen("levelup.png");
        }
    	if (cheatCode.equals("end")) {
    		animation.stop();
    		setScreen("wingraphic.png"); 
    	}
    	if (cheatCode.equals("case3")) {
    		animation.stop();
    		restartGame(myStage);
    	}
    	if (cheatCode.equals("case4")) {
    		animation.stop();
    		setScreen("start.png");
    	}
    }
    
	/**
	 * This sets the scene with a different screen
	 * 
	 * @param String
	 */
    private void setScreen(String file) {
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView startup = new ImageView(image);
        startup.setFitWidth(myScene.getWidth());
        startup.setPreserveRatio(true);
        root.getChildren().add(startup);
        checkInput(file);
        myScene.setOnKeyPressed(e -> cheatCode(e.getCode()));
    }
    
	/**
	 * This sets up the game for different conditions
	 * 
	 * @param String
	 */
    public void stopGame(Group r, Scene s, Engine.gameEnd status) {
    	root = r;
    	myScene = s;
    	String file = "";
    	if (status == Engine.gameEnd.ADVANCE) {
    		level = 2;
    		file = "levelup.png";
    	}
    	if (status == Engine.gameEnd.WIN) {
    		level = 1;
    		file = "wingraphic.png";
    	}
    	if (status == Engine.gameEnd.LOST) {
        	level = 1;
        	file = "losegraphic.png";
    	}
    	setScreen(file);
    }
    
    
	/**
	 * This calls into the Engine class to start the animation
	 *
	 * @param Stage
	 */
	private void restartGame(Stage s) {
		animation.stop();
		myEngine = new Engine();
		myEngine.levelScene(root, myScene, level);
	}
	
	
	/**
	 * This checks if the cheat codes are entered into the keyboard
	 * 
	 * @param KeyCode
	 */
    private void cheatCode (KeyCode code) {
        switch (code) {
            case L:
            	cheatCode = cheatCode + 'l';
            	break;
            case V:
            	cheatCode = cheatCode + 'v';
            	break;
            case T:
            	cheatCode = cheatCode + 't';
            	break;
            case W:
            	cheatCode = cheatCode + 'w';
            	break;
            case O:
            	cheatCode = cheatCode + 'o';
            	break;
            case UP:
            	cheatCode = "";
            	break;
            case E:
            	cheatCode = cheatCode + "e";
            	break; 
            case N:
            	cheatCode = cheatCode + "n";
            	break; 
            case D:
            	cheatCode = cheatCode + "d";
            	break; 
            case SPACE: 
            	cheatCode = "case3";
            	break;
            case RIGHT:
            	cheatCode = "case4";
            	break;
            default:
    	}
    }
    

}


