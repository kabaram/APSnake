import javax.swing.JFrame;

/**
 * Class Main acts as a driver for the AP Snake program. It maintains the game frame that houses the panel.
 * @author kabaram
 *
 */

public class Main {

	//frame dimensions
	public static final int DIM = 800;
	
	//snake bit, pellet, and obstacle dimensions
	public static final int BIT = 20;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("AP Snake");
		frame.setSize(DIM, DIM);
		frame.setLocation(0, 0);
		SnakePanel snake = new SnakePanel();
		frame.setContentPane(snake);
		frame.addKeyListener(snake);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
