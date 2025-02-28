package Sound;
import Enum.SoundType;
import javax.sound.sampled.*;

public class BackgroundMusic extends SoundControl {
    private FloatControl volume;

    public BackgroundMusic(SoundType type) {
        loadSound(type);
        
        if (sound != null) {
            volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(-20.0f);
            
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Set volume
    private void setVolume(float value) {
        if (volume != null) {
            volume.setValue(value);
        }
    }
}
