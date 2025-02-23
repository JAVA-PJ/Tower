package Source;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Block {
	// Block Size
	protected final int Width = 180;
	protected final int Height = 140;

	// Block Position
	protected int posX = 260;
	protected int posY = 50;

	// Block Movement
	protected boolean falling = false;
	private int speedX = 10;
	private int speedY = 15;

	// Block Image
    private Image blockImg;

	public Block () {
		blockImg = new ImageIcon(getClass().getResource("../Assets/block.png")).getImage();
	}

	public void swing(int screenWidth) {
		if (!falling) {
			posX += speedX;
			if (posX < 0 || posX + Width > screenWidth)
				speedX *= -1;
		}
	}

	public void fall() {
		falling = true;
		posY += speedY;
	}

	public void draw(Graphics g) {
		if (blockImg != null)
			g.drawImage(blockImg, posX, posY, Width, Height, null);
	}
}
