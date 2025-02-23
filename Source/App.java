package Source;
import javax.swing.JFrame;
import javax.swing.Timer;
public class App extends JFrame {
	// Screen
	protected static final int HEIGHT = 1000;
	protected static final int WIDTH = 700;

	// Display
	Display dp;

	// Init App
	public App() {
		setTitle("Tower Game");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Timer gameLoop = new Timer(0, e -> dp.gameLoop());
		dp = new Display(gameLoop);
		add(dp);
		pack();
		setVisible(true);
		
        gameLoop.start();
	}
}