package engine.audio;

import game.EngineLogger;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundClip {

    private Clip clip = null;
    private FloatControl gainControl;
    public SoundClip(String path){

        try {
            InputStream audioSource = SoundClip.class.getResourceAsStream(path);
            InputStream bufferedInput = new BufferedInputStream(audioSource);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedInput);
            AudioFormat baseFormat = audioInput.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodedAudio = AudioSystem.getAudioInputStream(
                    decodeFormat, audioInput
            );

            clip = AudioSystem.getClip();
            clip.open(decodedAudio);

            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException |
                LineUnavailableException exception) {
            EngineLogger.LOGGER.warning(
                    "Could not read audio source: " +
                    exception.getMessage()
            );
        }
    }

    public void play() {
        if (clip == null) {
            return;
        }
        stop();
        clip.setFramePosition(0);
        int numTries = 5;
        while (!clip.isRunning() && numTries > 0) {
            clip.start();
            numTries--;
        }
    }

    public void stop() {
        if(clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void changeVolume(float decibelModifier) {
        gainControl.setValue(decibelModifier);
    }

    public boolean isRunning(){
        return clip.isRunning();
    }
}
