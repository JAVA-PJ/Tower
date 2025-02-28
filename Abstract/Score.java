package Abstract;
import Enum.ImageType;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Score {
	// Score
	protected static int score = 0;
	
	// Score Imgae
	protected Image scoreImg;

	public Score() {
		scoreImg = new ImageIcon(getClass().getResource(ImageType.SCORE.getPath())).getImage();
    }

	public int getScore() { return (score); }

	public void resetScore() { score = 0; }

	public void updateSocre() { score += 1; }

	public abstract void drawScore(Graphics g);
}
