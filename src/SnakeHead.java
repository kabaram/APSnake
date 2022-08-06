import java.awt.Color;
import java.awt.Graphics;

/**
 * The SnakeHead class maintains an enhanced SnakeBit with eyes
 * @author kabaram
 *
 */
public class SnakeHead extends SnakeBit {
	
	//the snake's direction of travel
	private int direction;

	/**
	 * Creates a new snake head at the indicated location and direction
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param direction direction of travel
	 */
	public SnakeHead(int x, int y, int direction) {
		super(x, y);
		this.direction = direction;
	}
	

	/**
	 * Draws a standard snake bit, then adds a pair of blue eyes
	 */
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.blue.darker());
		int bit = Main.BIT;
		int x = getX();
		int y = getY();
		switch (direction) {
		case Snake.UP:
			g.fillOval(x + bit / 10, y, 6, 6);
			g.fillOval(x + 6 * bit / 10, y, 6, 6);
			break;
		case Snake.RIGHT:
			g.fillOval(x + bit - 4 * bit / 10, y + bit / 10, 6, 6);
			g.fillOval(x + bit - 4 * bit / 10, y + 6 * bit / 10, 6, 6);
			break;
		case Snake.DOWN:
			g.fillOval(x + bit / 10, y + bit - 4 * bit / 10, 6, 6);
			g.fillOval(x + bit - 4 * bit / 10, y + bit - 4 * bit / 10, 6, 6);
			break;
		default:
			g.fillOval(x, y + bit / 10, 6, 6);
			g.fillOval(x, y + 6 * bit / 10, 6, 6);
		}

	}

}
