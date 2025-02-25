package GUI;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

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
                ClickSound();
                setLocation(getX() + 2, getY() + 2);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                    setLocation(getX() - 2, getY() - 2);
            }
        });
    }
    public static void ClickSound() {
        try {
            File sound = new File("GUI/Asset/ClickSound.wav"); 
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

