import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * The Snake class maintains the information on one snake in the game APSnake
 * Snakes consist of one head at the front, followed by a trail of bits.
 * Snakes may not crash into obstacles or other snakes (including themselves).
 * @author kabaram
 *
 */

public class Snake {
	
	//contains all bits that constitute the snake
	private List<SnakeBit> bits = new ArrayList<SnakeBit>();
	
	//the x and y coordinates of the head
	private int x, y;
	
	//directional values
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	
	//current direction of the snake
	private int direction;
	
	//distance traveled per frame
	private final int MOTION = Main.BIT;
	
	//Milliseconds between fraames
	private static int delay;

	/**
	 * Constructs a 3-bit snake with a fixed initial delay and random direction and starting location
	 * @param obstacles maintains location data of all obstacles on the field
	 */
	public Snake(List<Obstacle> obstacles) {
		delay = 150;
		direction = (int)(Math.random() * 4);
		build(obstacles);
	}
	

	/**
	 * Generates the random snake location to ensure that it is not inside any obstacle
	 * @param obstacles maintains location data of all obstacles on the field
	 */
	public void build(List<Obstacle> obstacles) {
		x = 0;
		y = 0;
		
		//generate location until not overlapping an obstacle
		do {
			x = (int)(Math.random() * Main.DIM - 50);
			y = (int)(Math.random() * Main.DIM - 50);
		}
		while(headInObstacle(x, y, obstacles));
		
		bits.add(new SnakeHead(x, y, direction));
		
		//creates two bits trailing the head based on direction
		switch (direction) {
		case UP:
			bits.add(new SnakeBit(x, y + Main.BIT));
			bits.add(new SnakeBit(x, y + 2 * Main.BIT));
			break;
		case RIGHT:
			bits.add(new SnakeBit(x - Main.BIT, y));
			bits.add(new SnakeBit(x - 2 * Main.BIT, y));
			break;
		case DOWN:
			bits.add(new SnakeBit(x, y - Main.BIT));
			bits.add(new SnakeBit(x, y - 2 * Main.BIT));
			break;
		default:
			bits.add(new SnakeBit(x + Main.BIT, y));
			bits.add(new SnakeBit(x + 2 * Main.BIT, y));
			break;
		}
	}
	
	/**
	 * Checks head location during snake initialization for overlap with an obstacle
	 * @param x the proposed x coordinate of the head
	 * @param y the proposed y coordinate of the head.
	 * @param obstacles
	 * @return true if head is in obstacle, false otherwise
	 */
	private boolean headInObstacle(int x, int y, List<Obstacle> obstacles) {
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
	 * Checks to see if the snakes head has collided with a snake (self of opponent)
	 * @param s The full snake to check for a collision with this snake's head
	 * @return true if collision, false otherwise.
	 */
	private boolean headInSnake(Snake s) {
		
		//start with third bit if checking against itself, check all bits if checking against opponent
		int start = s == this ? 2 : 0;
		
		int thisX = bits.get(0).getX() + Main.BIT / 2;
		int thisY = bits.get(0).getY() + Main.BIT / 2;
		
		for (int i = start; i < s.bits.size(); i++) {
			SnakeBit bit = s.bits.get(i);
			int otherX = bit.getX() + Main.BIT / 2;
			int otherY = bit.getY() + Main.BIT / 2;
			if (Math.sqrt(Math.pow(thisX - otherX, 2) + Math.pow(thisY - otherY, 2)) < Main.BIT) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks for a snake collision between either itself or the opponent
	 * @param other the opponent snake
	 * @return true if collision, false otherwise.
	 */
	public boolean checkHeadInSnake(Snake other) {
		return headInSnake(this) || headInSnake(other);
	}

	/**
	 * Changes the direction of travel for the snake
	 * @param direction the new direction of travel
	 */
	public void turn(int direction) {
		this.direction = direction;
		
		//modify the head to draw eyes in correct direction
		bits.set(0, new SnakeHead(bits.get(0).getX(), bits.get(0).getY(), direction));
	}

	/**
	 * Check if a snake has reached a pellet. If so, snake size increases by 3 bits and pellet moves
	 * @param pellet the pellet to check
	 * @param obstacles all obstacles on field to ensure pellet does not overlap an obstacle after moving
	 */
	public void checkHitPellet(Pellet pellet, List<Obstacle> obstacles) {
		SnakeBit head = bits.get(0);
		int snakeX = head.getX() + Main.BIT / 2;
		int snakeY = head.getY() + Main.BIT / 2;
		int pelletX = pellet.getX() + Main.BIT / 2;
		int pelletY = pellet.getY() + Main.BIT / 2;
		if (Math.sqrt(Math.pow((pelletX - snakeX), 2) + Math.pow((pelletY - snakeY), 2)) < (Main.BIT / 2
				+ Main.BIT / 2)) {
			pellet.move(obstacles);
			int x = bits.get(bits.size() - 1).getX();
			int y = bits.get(bits.size() - 1).getY();
			for (int i = 0; i < 3; i++) {
				bits.add(new SnakeBit(x, y));
			}
			speedUp();
		}

	}

	/**
	 * Checks if the snake head has traveled into an obstacle
	 * @param obstacles all obstacles on the field
	 * @return true if collision, false otherwise
	 */
	public boolean checkHitObstacle(List<Obstacle> obstacles) {
		SnakeBit head = bits.get(0);
		int snakeX = head.getX() + Main.BIT / 2;
		int snakeY = head.getY() + Main.BIT / 2;
		for (Obstacle o : obstacles) {
			int wallX = o.getX() + Main.BIT / 2;
			int wallY = o.getY() + Main.BIT / 2;
			if (Math.sqrt(Math.pow((wallX - snakeX), 2) + Math.pow((wallY - snakeY), 2)) < (Main.BIT / 2
					+ Main.BIT / 2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Increases the speed of the snake after eating a pellet
	 */
	private void speedUp() {
		if (delay >= 20) {
			delay -= 5;
		}
	}
	
	/**
	 * 
	 * @return the current direction of travel
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * 
	 * @return this snake's delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Moves the snake one frame
	 * Accomplished by adding a new snake head in the correct direction at the front of the snake,
	 * then reverting the previous head to a regular bit, removing the last bit, and updating x and y
	 * to the coordinates of the new head.
	 */
	public void move() {
		switch (direction) {
		case UP:
			if (y > 0) {
			bits.add(0, new SnakeHead(bits.get(0).getX(), y - MOTION, direction));
			}
			else {
				bits.add(0, new SnakeHead(x, Main.DIM - 38 - Main.BIT, direction));
			}
			break;
		case RIGHT:
			if (x < Main.DIM - 15) {
			bits.add(0, new SnakeHead(x + MOTION, y, direction));
			}
			else {
				bits.add(0, new SnakeHead(0, y, direction));
			}
			break;
		case DOWN:
			if (y < Main.DIM - 38) {
			bits.add(0, new SnakeHead(x, y + MOTION, direction));
			}
			else {
				bits.add(0, new SnakeHead(x, 0, direction));
			}
			break;
		default:
			if (x > 0) {
			bits.add(0, new SnakeHead(x - MOTION, y, direction));
			}
			else {
				bits.add(0, new SnakeHead(Main.DIM - 38 + Main.BIT, y, direction));
			}
		}
		bits.set(1, new SnakeBit(bits.get(1).getX(), bits.get(1).getY()));
		bits.remove(bits.size() - 1);
		x = bits.get(0).getX();
		y = bits.get(0).getY();
	}

	/**
	 * Draws all bits of the snake
	 * @param g Graphics object for drawing
	 */
	public void draw(Graphics g) {
		for (SnakeBit bit : bits) {
			bit.draw(g);
		}
	}

}
