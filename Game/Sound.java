package Game;
import Enum.SoundType;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {

    public static void playSound(SoundType type) {
        try {
            File sound = new File(type.getPath());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}