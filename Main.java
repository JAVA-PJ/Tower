import GUI.TowerGameMenu;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(TowerGameMenu::new);
	}
}