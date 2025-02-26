package Game;
import Enum.ImageType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Block {
	// Block Size
	protected final int Width = 180;
	protected final int Height = 150;

	// Block Position
	protected int posY = 50;
	protected int posX = 260;

	// Block Movement
	private static int speedX = 5;
	private static int speedY = 20;
	protected boolean falling = false;
	protected boolean animation = true;

	// Block Image
    private Image blockImg;

	// Color
	private Color color;

	public Block() {
		blockImg = new ImageIcon(getClass().getResource(ImageType.BLOCK.getPath())).getImage();
	}

	public Block (int posX) {
		this();
		this.posX = posX;
	}

	public Image getImage() { return (blockImg); }

	public Color getColor() { return (color); }

	public void setImage(Image image) { this.blockImg = image; }

	public static void speedUp() {
		if (speedX < 0)
			speedX -= 1;
		else
			speedX += 1;
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

	public void drawBlock(Graphics g) {
		if (blockImg != null && animation)
			g.drawImage(blockImg, posX, posY, Width, Height, null);
	}
}
