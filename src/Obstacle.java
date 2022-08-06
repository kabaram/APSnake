import java.awt.Color;
import java.awt.Graphics;


/**
 * The Obstacle class maintains a single field obstacle
 * @author kabaram
 *
 */
public class Obstacle {
	
	//coordinates of the obstacle's upper left corner
	private int x, y;

	/**
	 * Creates a new obstacle at the indicated location
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return the obstacle's x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return the obstacle's y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Draws a blue Main.BIT*Main.BIT square
	 * @param g the Graphics object for drawing
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLUE.brighter().brighter());
		g.fillRect(x, y, Main.BIT, Main.BIT);
	}
}
