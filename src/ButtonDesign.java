import javafx.scene.control.Button;

public class ButtonDesign {
	
	
	public void createButton(String text, e) {
		Button start = new Button(text);
    	start.setOnAction(e -> restartGame(myStage));
	}
	
}
