
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create
 * an animated scene.
 * 
 * @author Robert C. Duvall
 * Originally written by Robert Duvall
 */
public class Main extends Application {
	
    private static final int SIZE = 400;

    private GameEngine myGame;


    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        // create your own game here
    	myGame = new GameEngine();
        s.setTitle(myGame.getTitle());
        // attach game to the stage and display it
        myGame.init(s, SIZE, SIZE);
	}
    
	/**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}