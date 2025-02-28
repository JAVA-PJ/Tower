package Sound;
import Enum.SoundType;
import Interface.Sound;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundControl implements Sound {
    protected Clip sound;
    protected boolean isPlaying = false;
    
    // Load sound from file
    public void loadSound(SoundType type) {
        try {
            File soundFile = new File(type.getPath());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            sound = AudioSystem.getClip();
            sound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play sound
    @Override
    public void play() {
        if (sound != null && !isPlaying) {
            sound.start();
            isPlaying = true;
        }
    }
    
    // Stop sound
    @Override
    public void stop() {
        if (sound != null && isPlaying) {
            sound.stop();
            isPlaying = false;
        }
    }
}