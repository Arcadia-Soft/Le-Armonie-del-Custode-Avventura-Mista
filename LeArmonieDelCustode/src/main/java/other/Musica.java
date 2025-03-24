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
     * @param filePath percorso del file audio da riprodurre
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
     * Ferma e chiude il clip se è in esecuzione.
     */
    public void stopMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            musicaGioco.stop();
            musicaGioco.close();
        }
    }

    /**
     * Mette in pausa la riproduzione della musica corrente.
     * La posizione viene mantenuta per una successiva ripresa.
     */
    public void pausaMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            // musicaGioco.setMicrosecondPosition(musicaGioco.getMicrosecondPosition());
            musicaGioco.stop();
        }
    }

    /**
     * Riprende la riproduzione della musica dalla posizione di pausa.
     */
    public void riprendiMusica() {
        musicaGioco.start();
    }

    /**
     * Verifica se la musica è attualmente in riproduzione.
     * 
     * @return true se la musica è in riproduzione, false altrimenti
     */
    public boolean isPlaying() {
        return musicaGioco.isRunning();
    }

    /**
     * Metodo che gestisce il volume della musica.
     * 
     * @param volume livello del volume da impostare in decibel
     */
    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

    /**
     * Restituisce il volume corrente della musica.
     * 
     * @return il livello corrente del volume in decibel
     */
    public float getVolume() {
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);
        return gainControl.getValue();
    }

    /**
     * Riproduce un effetto sonoro una sola volta.
     * 
     * @param filePath percorso del file audio da riprodurre
     */
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