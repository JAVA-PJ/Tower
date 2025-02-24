package MainMenu;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class BackgroundMusic {
    private Clip clip;
    private boolean isPlaying = false;
    public BackgroundMusic(String fileName) {
        try {
            URL soundURL = getClass().getResource(fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void play() {
        if (clip != null && !isPlaying) {
            clip.start();
            isPlaying = true;
        }
    }
    public void stop() {
        if (clip != null && isPlaying) {
            clip.stop();
            isPlaying = false;
        }
    }
}
