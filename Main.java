
import javax.swing.SwingUtilities;
import MainMenu.TowerGameMenu;
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(TowerGameMenu::new);
	}
}