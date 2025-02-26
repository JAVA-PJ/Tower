package Game;
import Enum.ImageType;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Score {
	// Score
	public int score = 0;
	
	// Score Imgae
	private Image scoreImg;

	public Score() {
		scoreImg = new ImageIcon(getClass().getResource(ImageType.SCORE.getPath())).getImage();
    }

	public void updateSocre() { score += 1; }

	public void drawScore(Graphics g) {
		int posX = 10, posY = 10;

		if (scoreImg != null)
			g.drawImage(scoreImg, posX, posY, 100, 50, null);
		g.setColor(Color.black);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));

		String scoreStr = String.valueOf(score);
		int posXstr = posX + 110;  // ตำแหน่งเริ่มต้นของตัวเลข

		// วาดตัวเลขทีละหลัก
		for (char c : scoreStr.toCharArray()) {
			g.drawString(String.valueOf(c), posXstr, posY + 40);  // วาดตัวเลขที่ตำแหน่ง
			posXstr += 25;
		}
	}

	public void drawGameOverScore(Graphics g, int x, int y) {
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));

		String scoreStr = String.valueOf(score);
		int posX = x + 100;  // ตำแหน่งเริ่มต้นของตัวเลข

		// วาดตัวเลขทีละหลัก
		for (char c : scoreStr.toCharArray()) {
			g.drawString(String.valueOf(c), posX, y + 40);  // วาดตัวเลขที่ตำแหน่ง
			posX += 25;
		}
	}
}
