package Source;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		Timer timer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				background.addBlock();
			}
		});
		timer.start();
	}
	}

