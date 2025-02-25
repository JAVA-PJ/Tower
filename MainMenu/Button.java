package MainMenu;
import Enum.SoundType;
import Game.Sound;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Button extends JLabel {
    public Button(ImageIcon icon) {
        setIcon(icon);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setLocation(getX(), getY() - 3);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setLocation(getX(), getY() + 3);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                Sound.playSound(SoundType.CLICK);
                setLocation(getX() + 2, getY() + 2);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setLocation(getX() - 2, getY() - 2);
            }
        });
    }
}

