package Source;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Block {
	protected int x, y, width, height;
	protected boolean falling = false;
	private int speedX = 10;
	private int speedY = 10;

	// Image
    private Image blockImg;


	public Block (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		blockImg = new ImageIcon(getClass().getResource("../Assets/block.png")).getImage();

	}

	public void swing(int screenWidth) {
		if (!falling) {
			x += speedX;
			if (x < 0 || x + width > screenWidth)
				speedX *= -1;
		}
	}

	public void fall() {
		falling = true;
		y += speedY;
	}

	public void draw(Graphics g) {
		if (blockImg != null)
			g.drawImage(blockImg, x, y, width, height, null);
	}
}
