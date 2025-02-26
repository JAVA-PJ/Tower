package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Block {
	// Block Size
	protected final int Width = 180;
	protected final int Height = 140;

	// Block Position
	protected int posY = 50;
	protected int posX = 260;

	// Block Movement
	private int speedX = 5;
	private int speedY = 10;
	protected boolean falling = false;
	protected boolean animation = true;

	// Block Image
	private Image blockImg;

	//color
	private Color color;


	// เพิ่มในคลาส Block

	public Image getImage() {
		return this.blockImg;
	}

	public Block() {
		blockImg = new ImageIcon(getClass().getResource("../Assets/block.png")).getImage();
	}

	public Block(int posX) {
		this.posX = posX;
		blockImg = new ImageIcon(getClass().getResource("../Assets/block.png")).getImage();
	}
	public void setImage(Image image) {
		this.blockImg = image;
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
	public Color getColor() {
		return color;
	}


}
