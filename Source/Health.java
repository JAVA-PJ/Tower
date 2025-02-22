package Source;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Health {
	private final int maxHealth = 3;
	
	private Image healthImg;

	public Health() {
		healthImg = new ImageIcon(getClass().getResource("../Assets/")).getImage();
	}
}
