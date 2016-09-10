

/**
 * This is the character class, used to create character objects.
 * 
 * @author Hannah Fuchshuber
 */

public class Character {
	
	public enum Type {
		ENEMY, FOOD
	}
	
	private double posX;
	private double posY;
	private double height;
	private double width;
	private Type type;
	
	
	public Character(double x, double y) {
		this.posX = x;
		this.posY = y;
	}
	
	public Character (double x, double y, double h, double w, Type t) {
		this.posX = x;
		this.posY = y;
		this.height = h;
		this.width = w;
		this.type = t;
	}

	
	public double getY() {
		return posY;
	}
	
	public double getX() {
		return posX;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public Type getType() {
		return type;
	}
	
	
	public void setY(double pos) {
		this.posY = (pos);
	}
	
	public void setX(double pos) {
		this.posX = (pos);
	}
	

}
