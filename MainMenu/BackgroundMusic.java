package MainMenu;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;
public class BackgroundMusic {
    private Clip sound;
    private boolean isPlaying = false;
    public BackgroundMusic(String file) {
        try {
            URL url = getClass().getResource(file);
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            sound = AudioSystem.getClip();
            sound.open(audio);
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void play() {
        if (sound != null && !isPlaying) {
            sound.start();
            isPlaying = true;
        }
    }
    public void stop() {
        if (sound != null && isPlaying) {
            sound.stop();
            isPlaying = false;
        }
    }
}
