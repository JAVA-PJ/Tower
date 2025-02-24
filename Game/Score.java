package Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Score {
	// Score
	public int score = 0;

	// Score Imgae
	private Image scoreImg;

	// Imgae Size
	private  int imgWidth = 150;
	private int imgHeight = 60;

	public Score() {
		try {
            Image originalImg = ImageIO.read(new File("Assets/score.png"));
            scoreImg = originalImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Error loading score background image: " + e.getMessage());
        }
    }

	public void updateSocre() { score += 1; }

	public void drawScore(Graphics g){
		int x = 10, y = 10;

		if (scoreImg != null)
			g.drawImage(scoreImg, x, y, null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 30));

		String scoreStr = String.valueOf(score);
		int posX = x + 100;  // ตำแหน่งเริ่มต้นของตัวเลข

		// วาดตัวเลขทีละหลัก
		for (char c : scoreStr.toCharArray()) {
			g.drawString(String.valueOf(c), posX, y + 37);  // วาดตัวเลขที่ตำแหน่ง
			posX += 15;
		}
	}
}
