
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main program, it is basically boilerplate to create
 * a Stage.
 * 
 * @author Hannah Fuchshuber
 * Originally written by Robert Duvall
 */

public class Main extends Application {
	
    private static final int SIZE = 400;

    private Game myGame;


    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        // create your own game here
    	myGame = new Game();
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