package Game.Screen;
import Abstract.Score;
import java.awt.Font;
import java.awt.Graphics;

public class GameOverScore extends Score {
	public GameOverScore() {
		super();
	}

	@Override
	public void drawScore(Graphics g) {
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));

		String scoreStr = String.valueOf(score);
		char[] arr = scoreStr.toCharArray();
		int posX = 330, posY = 340;

		if (arr.length > 1)
			posX -= 15 * (arr.length - 1);
		for (char c : arr) {
			g.drawString(String.valueOf(c), posX, posY);
			posX += 30;
		}
	}
}
