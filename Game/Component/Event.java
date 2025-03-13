package Game.Component;
import javax.swing.ImageIcon;
import java.awt.Image;
import Enum.EventType;

public class Event {
	public int posX, posY;
	public Image image;
	
	public Event(EventType type, int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.image = new ImageIcon(getClass().getResource(type.getPath())).getImage();
	}
}
