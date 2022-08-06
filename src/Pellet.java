import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 * The Pellet class models a pellet/powerup on the snake field
 * 
 * @author kabaram
 *
 */
public class Pellet {


	// location of the pellet's upper left corner
	private int x, y;

	/**
	 * Creates a pellet at a random location
	 * 
	 * @param obstacles data on field obstacles
	 */
	public Pellet(List<Obstacle> obstacles) {
		move(obstacles);
	}

	/**
	 * Generates a location for the pellet, ensuring no overlap between obstacles
	 * obstacles
	 * 
	 * @param obstacles data on field obstacles
	 */
	public void move(List<Obstacle> obstacles) {
		do {
			x = gen();
			y = gen();
		} while (pelletInObstacle(obstacles));
	}

	/**
	 * 
	 * @param obstacles data on field obstacles
	 * @return true if pellet overlaps obstacle, false otherwise
	 */
	private boolean pelletInObstacle(List<Obstacle> obstacles) {
		for (Obstacle o : obstacles) {
			if (Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2)) <= Main.BIT) {
				return true;
			}
			if (Math.sqrt(Math.pow(x + Main.BIT - o.getX() + Main.BIT, 2) + Math.pow(y + Main.BIT - o.getY() + Main.BIT, 2)) <= Main.BIT) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return a random coordinate in the field
	 */
	private int gen() {
		return (int) (Math.random() * (Main.DIM - Main.BIT - 50));
	}

	/**
	 * 
	 * @return the pellet's x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return the pellet's y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Draws a filled Main.BIT*Main.BIT circle
	 * @param g Graphics object for drawing
	 */
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, Main.BIT, Main.BIT);
	}
}
