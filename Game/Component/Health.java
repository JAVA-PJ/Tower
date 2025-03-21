package Game.Component;
import Enum.ImageType;
import Game.Screen.App;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Health {
	// Health
	private boolean isDie = false;
	private static int curHealth = 3;
	public static final int maxHealth = 3;

	// Position
	private int posX = App.WIDTH - 10;
	private int posY = 10;
	
	// Image
	private Image no_health;
	private Image full_health;

	public Health(int posX, int posY) {
		this.posX += posX;
		this.posY += posY;
		no_health = new ImageIcon(getClass().getResource(ImageType.NO_HEALTH.getPath())).getImage();
		full_health = new ImageIcon(getClass().getResource(ImageType.FULL_HEALTH.getPath())).getImage();
	}

	public static int getCurHealth() { return (curHealth); }

	public static void resetCurHealth() { curHealth = maxHealth; }

	public void setIsDie(boolean isDie) { this.isDie = isDie; }

	public static void updateCurHealth() { curHealth -= 1; }

	public boolean checkIfDie() {
		return (curHealth <= 0);
	}

	public void updateHealth(Graphics g) {
		if (no_health != null && isDie == true)
			g.drawImage(no_health, posX, posY, 50, 50, null);
		else if (full_health != null)
			g.drawImage(full_health, posX, posY, 50, 50, null);
	}
}
