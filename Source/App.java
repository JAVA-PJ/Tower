package Source;
import javax.swing.JFrame;
import javax.swing.Timer;
public class App extends JFrame {
	// Screen
	protected static final int HEIGHT = 1000;
	protected static final int WIDTH = 700;

	// Display
	Display dp;

	public App() {
		dp = new Display();
		setTitle("Tower Game");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(dp);
		pack();
		setVisible(true);
		
		Timer gameLoop = new Timer(0, e -> dp.gameLoop());
        gameLoop.start();
	}
}