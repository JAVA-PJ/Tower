package Game;
import javax.swing.JFrame;
public class App extends JFrame {
	// Screen
	public static final int HEIGHT = 1000;
	public static final int WIDTH = 700;

	// Display
	Display panel;

	// Init App
	public App() {
		setTitle("Tower Game");
		setSize(WIDTH, HEIGHT);
		// setExtendedState(JFrame.MAXIMIZED_VERT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new Display();
		add(panel);
		pack();
		setVisible(true);
		
	}
}