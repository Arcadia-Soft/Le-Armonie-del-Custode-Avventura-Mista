package other;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Classe che gestisce il controllo della musica di sottofondo del programma.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Musica {

    private Clip musicaGioco;
    // private File defaultMusic = new
    // File("LeArmonieDelCustode\\resource\\other\\background_music.wav");

    /**
     * Metodo che avvia la riproduzione della musica all'esecuzione del programma.
     * 
     * @param filePath
     */
    public void playMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            musicaGioco = AudioSystem.getClip();

            musicaGioco.open(AudioSystem.getAudioInputStream(musicFile));

            musicaGioco.loop(Clip.LOOP_CONTINUOUSLY);

            musicaGioco.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
        }
    }

    /**
     * Metodo che gestisce lo stop della riproduzione della musica.
     */
    public void stopMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            musicaGioco.stop();
            musicaGioco.close();
        }
    }

    public void pausaMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            // musicaGioco.setMicrosecondPosition(musicaGioco.getMicrosecondPosition());
            musicaGioco.stop();
        }
    }

    public void riprendiMusica() {
        musicaGioco.start();
    }

    public boolean isPlaying() {
        return musicaGioco.isRunning();
    }

    /**
     * Metodo che gestisce il volume della musica.
     * 
     * @param volume
     */
    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);
        return gainControl.getValue();
    }

    public void riproduciClip(String filePath) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
