package Game;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Health {
	// Health
	protected static final int maxHealth = 3;
	private boolean isDie = false;
	protected static int curHealth = 3;

	// Position
	private int posX = App.WIDTH - 10;
	private int posY = 10;
	
	// Image
	private Image non_health;
	private Image full_health;

	public Health(int posX, int posY) {
		this.posX += posX;
		this.posY += posY;
		non_health = new ImageIcon(getClass().getResource("../Assets/non-health.png")).getImage();
		full_health = new ImageIcon(getClass().getResource("../Assets/full-health.png")).getImage();
	}

	public void setIsDie(boolean isDie) { this.isDie = isDie; }

	public static void updateCurHealth() { curHealth -= 1; }

	public boolean checkIfDie() {
		return (curHealth <= 0 ? true : false);
	}

	public void updateHealth(Graphics g) {
		if (non_health != null && isDie == true)
			g.drawImage(non_health, posX, posY, 50, 50, null);
		else if (full_health != null)
			g.drawImage(full_health, posX, posY, 50, 50, null);
	}
}
