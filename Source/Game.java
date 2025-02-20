package Source;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
	int boardWidth = 750;
	int boardHeight = 1000;
	JFrame frame;
	Background background;

	public Game() {
		start();
	}

	public void start() {
		frame = new JFrame("Tower Building");
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		background = new Background();
		frame.add(background);
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

