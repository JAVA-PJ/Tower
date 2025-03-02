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
		
		String scoreStr = String.valueOf(score);
		char[] arr = scoreStr.toCharArray();
		int posX = 120, posY = 50;

		for (char c : arr) {
			g.drawString(String.valueOf(c), posX, posY);
			posX += 30;
		}
	}
}
