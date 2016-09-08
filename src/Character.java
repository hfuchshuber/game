

/**
 * This is the character class, used to create character objects.
 * 
 * @author Hannah Fuchshuber
 */

public class Character {
	
	private double posX;
	private double posY;
	private double height;
	private double width;
	private boolean type;
	
	public Character(double x, double y) {
		this.posX = x;
		this.posY = y;
	}
	
	public Character (double x, double y, double h, double w, boolean bool) {
		this.posX = x;
		this.posY = y;
		this.height = h;
		this.width = w;
		this.type = bool;
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
	
	public boolean getType() {
		return type;
	}
	
	
	public void setY(double pos) {
		this.posY = (pos);
	}
	
	public void setX(double pos) {
		this.posX = (pos);
	}
	

}
