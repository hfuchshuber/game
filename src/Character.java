

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
	
	
	/**
	 * This is a constructor that takes (X,Y)
	 * 
	 * @param double
	 * @param double
	 */
	public Character(double x, double y) {
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * This is a constructor that takes (X,Y), weight, height, and Type
	 * 
	 * @param double
	 * @param double
	 * @param double
	 * @param double
	 * @param Type
	 */
	public Character (double x, double y, double h, double w, Type t) {
		this.posX = x;
		this.posY = y;
		this.height = h;
		this.width = w;
		this.type = t;
	}

	/**
	 * This gets the Y coordinate of Character
	 * 
	 */
	public double getY() {
		return posY;
	}
	
	/**
	 * This gets the X coordinate of Character
	 * 
	 */
	public double getX() {
		return posX;
	}
	
	/**
	 * This gets the height of Character
	 * 
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * This gets the width of Character
	 * 
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * This gets the type of Character
	 * 
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * This sets the Y coordinate of Character
	 * 
	 */
	public void setY(double pos) {
		this.posY = (pos);
	}
	
	
	/**
	 * This sets the X coordinate of Character
	 * 
	 */
	public void setX(double pos) {
		this.posX = (pos);
	}
	

}
