import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The SnakePanel class maintains the playing field for the Snake game
 * 
 * @author kabaram
 *
 */
public class SnakePanel extends JPanel implements ActionListener, KeyListener {

	//Inserted by Eclipse to circumvent warning
	private static final long serialVersionUID = 1L;
	
	private Snake snake;
	private Pellet pellet;
	private List<Obstacle> obstacles;
	private Timer timer;
	private int dim = Main.DIM;

	/**
	 * Construct the initial snake panel
	 */
	public SnakePanel() {
		obstacles = new ArrayList<Obstacle>();
		reset();

		// Set initial timer delay, call actionPerformed whenever "time" is called
		timer = new Timer(snake.getDelay(), this);
		timer.start();
		addKeyListener(this);
		repaint();
	}

	/**
	 * Place obstacles, snakes, and pellets
	 */
	public void reset() {
		generateObstacles();
		snake = new Snake(obstacles);
		pellet = new Pellet(obstacles);
	}

	/**
	 * Choose a layout for obstacles 1) No obstacles 2) Between 10 and 50 randomly
	 * placed obstacles 3) Border obstacle only 4) Border obstacle plus between 10
	 * and 30 randomly placed obstacles
	 */
	public void generateObstacles() {
		int layout = (int) (Math.random() * 4);
		int numObstacles = 0;
		boolean border = false;
		switch (layout) {
		case 0:
			return;
		case 1:
			numObstacles = (int) (Math.random() * 40 + 10);
			break;
		case 2:
			numObstacles = (int) (Math.random() * 20 + 10);
		default:
			border = true;
		}
		for (int i = 0; i < numObstacles; i++) {
			int x = (int) (Math.random() * Main.DIM);
			int y = (int) (Math.random() * Main.DIM);
			obstacles.add(new Obstacle(x, y));
		}
		if (border) {
			for (int i = 0; i <= Main.DIM; i += Main.BIT) {
				obstacles.add(new Obstacle(i, 0));
				obstacles.add(new Obstacle(0, i));
				obstacles.add(new Obstacle(i, dim - 38 - Main.BIT));
				obstacles.add(new Obstacle(dim - 15 - Main.BIT, i));
			}
		}
		System.out.println(numObstacles);
	}

	/**
	 * Draw obstacles, snakes, and pellet
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (Obstacle o : obstacles) {
			o.draw(g);
		}
		snake.draw(g);
		pellet.draw(g);
		if (!timer.isRunning()) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial Bold", Font.BOLD, 20));
			g.drawString("Press R to retry", dim / 2 - 50, dim / 2 + 10);
		}
	}

	/**
	 * Called to update the frame
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		snake.move();
		snake.checkHitPellet(pellet, obstacles);
		timer.setDelay(snake.getDelay());
		if (snake.checkHitObstacle(obstacles) || snake.checkHeadInSnake(snake)) {
			timer.stop();
		}
		repaint();
	}

	/**
	 * Called whenever a key is pressed
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
	//	if (timer.isRunning()) {
			
			//change direction; snake cannot change to 180 degrees from its current direction
			switch (key) {
			case KeyEvent.VK_UP:
				if (snake.getDirection() != Snake.DOWN) {
					snake.turn(Snake.UP);
				}
				break;
			case KeyEvent.VK_DOWN:
				if (snake.getDirection() != Snake.UP) {
					snake.turn(Snake.DOWN);
				}
				break;
			case KeyEvent.VK_LEFT:
				if (snake.getDirection() != Snake.RIGHT) {
					snake.turn(Snake.LEFT);
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (snake.getDirection() != Snake.LEFT) {
					snake.turn(Snake.RIGHT);
				}
				break;

			default:
				break;
			}
	//	} else {
			if (key == KeyEvent.VK_R) {
				obstacles.clear();
				reset();
				timer.start();
		//	}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
