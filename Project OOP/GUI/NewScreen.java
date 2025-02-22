package GUI;
import javax.swing.*;
public class NewScreen extends JFrame {
    public NewScreen() {
        setTitle("New Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel label = new JLabel("This is the new screen!", SwingConstants.CENTER);
        add(label);
        setVisible(true);
    }
}
