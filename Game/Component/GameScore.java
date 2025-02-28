package Game.Component;
import Abstract.Score;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameScore extends Score {
	public GameScore() {
		super();
    }

	@Override
	public void drawScore(Graphics g) {
		if (scoreImg != null)
			g.drawImage(scoreImg, 10, 10, 100, 50, null);
		g.setColor(Color.black);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		
		int posX = 120, posY = 50;
		String scoreStr = String.valueOf(score);

		for (char c : scoreStr.toCharArray()) {
			g.drawString(String.valueOf(c), posX, posY);
			posX += 30;
		}
	}

	// public void drawGameOverScore(Graphics g, int x, int y) {
	// 	g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));

	// 	String scoreStr = String.valueOf(score);
	// 	int posX = x + 100;

	// 	for (char c : scoreStr.toCharArray()) {
	// 		g.drawString(String.valueOf(c), posX, y + 40);
	// 		posX += 30;
	// 	}
	// }
}
