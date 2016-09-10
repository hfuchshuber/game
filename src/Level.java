import java.util.ArrayList;

import javafx.scene.Scene;

public abstract class Level {
	
    abstract Engine.gameEnd checkCollisions(ArrayList<Character> enemies, Character player, Engine.gameEnd status);
    abstract void moveEnemies(ArrayList<Character> enemies, Scene scene);
	abstract int getScore();
}
