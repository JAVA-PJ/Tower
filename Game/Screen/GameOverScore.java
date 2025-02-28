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
		int posX = 330, posY = 340;

		for (char c : scoreStr.toCharArray()) {
			g.drawString(String.valueOf(c), posX, posY);
			posX += 30;
		}
	}
}
