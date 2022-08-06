import java.awt.Color;
import java.awt.Graphics;

/**
 * The SnakeBit class maintains information on one square bit of a snake.
 * @author kabaram
 *
 */

public class SnakeBit {
	
	//x and y coordinate of upper left corner
	private int x, y;

	//Creates a new bit at the location (x, y)
	public SnakeBit(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return the bit's x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Update the bit's x-coordinate
	 * @param x the new x-coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @return the bit's y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Update the bit's y-coordinate
	 * @param y the new y-coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Chooses the bit's color and draws a filled Main.BIT*Main.BIT square
	 * @param g the Graphics object for drawing
	 */
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, Main.BIT, Main.BIT);
	}
}
