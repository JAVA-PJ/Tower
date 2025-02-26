package Game;
import Enum.SoundType;
import MainMenu.BackgroundMusic;
import javax.swing.JFrame;
public class App extends JFrame {
	// Screen
	public static final int HEIGHT = 1000;
	public static final int WIDTH = 700;

	// Display
	Display panel;

	// Sound
	private BackgroundMusic bgSound;

	// Init App
	public App() {
		setTitle("Tower Game");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		bgSound = new BackgroundMusic(SoundType.SOUND_GAME.getPath());
		bgSound.play();
		panel = new Display(bgSound);
		add(panel);
		pack();
		setVisible(true);
		
	}
}