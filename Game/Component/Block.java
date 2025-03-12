package Game.Component;
import Enum.ImageType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Block {
	// Block Size
	public final static int Width = 180;
	public final static int Height = 140;

	// Block Position
	private int posY = 40;
	private int posX = 260;

	// Block Movement
	private static float speedX = 5;
	private static int speedY = 20;
	private boolean falling = false;
	private boolean animationPrveBlock = true;

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
	
	public int getPosX() { return (posX); }
	public int getPosY() { return (posY); }
	public Color getColor() { return (color); }
	public Image getImage() { return (blockImg); }
	public boolean getFalling() { return (falling); }

	public void setPosX(int posX) { this.posX = posX; }
	public void setPosY(int posY) { this.posY = posY; }
	public void setImage(Image image) { this.blockImg = image; }
	public void setFalling(boolean falling) { this.falling = falling; }
	public void setAnimationPrveBlock(boolean animationPrveBlock) { this.animationPrveBlock = animationPrveBlock; }

	public static void resetSpeed() { speedX = 5; }
	
	public static void speedUp() { speedX = speedX < 0 ? speedX - 0.25f : speedX + 0.25f; }

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
		if (blockImg != null && animationPrveBlock)
			g.drawImage(blockImg, posX, posY, Width, Height, null);
	}
}
