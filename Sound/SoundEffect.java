package Sound;
import Enum.SoundType;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundEffect extends SoundControl {
    public SoundEffect(SoundType type) {
        loadSound(type);
    }

    // Quick play for sound effect
    public static void playSoundEffect(SoundType type) {
        try {
            File soundFile = new File(type.getPath());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
