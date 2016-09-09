
public class Level1 extends Levels {
	
	
	public void spawnEnemies(Scene myScene, ArrayList<Character> enemies) {
    	Character temp = new Character(myScene.getWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, true);
		enemies.add(temp);
		//root.getChildren().add(drawEnemy(temp));
		if (level == 2) {
			Character temp2 = new Character(myScene.getWidth() * Math.random(), 0, ENEMY_SIZE, ENEMY_SIZE, false);
			enemies.add(temp2);
			//root.getChildren().add(drawEnemy(temp2));
		}
	}

}
