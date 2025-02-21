package Source;
import javax.swing.JFrame;


public class App {
	// Screen
	int screenWidth = 750;
	int screenHeight = 1000;

	// Frame
	JFrame frame;

	// Game Panel
	GamePanel gamePanel;

	public App() {
		start();
	}

	public void start() {
		frame = new JFrame("Tower Building");
		frame.setSize(screenWidth, screenHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamePanel = new GamePanel();
		frame.add(gamePanel);
		frame.pack();
		frame.setVisible(true);
	}
}
