package media;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlay {
    private Clip clip;
    String soundFile = "./src/main/resources/sounds/loadingPageSound.wav";

    public SoundPlay() {
    }

    public Clip getSound(){



        if(clip == null){
            try(
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile())
            ){
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                return clip;
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException x) {
                x.printStackTrace();
            }
        }
        return clip;
    }
}




