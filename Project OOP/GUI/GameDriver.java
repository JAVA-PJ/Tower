package GUI;
import javax.swing.SwingUtilities;
public class GameDriver {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(TowerGameMenu::new);
    }
}

