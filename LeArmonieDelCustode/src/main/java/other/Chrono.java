package other;

import java.io.Serializable;

/**
 * Classe che implementa un cronometro per tenere traccia del tempo di gioco.
 * Fornisce funzionalità per avviare, fermare, resettare e formattare il tempo trascorso.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Chrono implements Serializable {
    private Long startTime;
    private Long endTime;
    private Long elapsedTime;
    private boolean isRunning;

    /**
     * Costruttore che inizializza un nuovo cronometro resettato.
     */
    public Chrono() {
        reset();
    }

    /**
     * Avvia il cronometro se non è già in esecuzione.
     */
    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    /**
     * Riavvia il cronometro con un tempo già trascorso.
     * Utile per riprendere un cronometro da un salvataggio.
     * 
     * @param elapsedTime Tempo già trascorso in millisecondi
     */
    public void startAgain(long elapsedTime) {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            this.elapsedTime = elapsedTime;
            isRunning = true;
        }
    }

    /**
     * Ferma il cronometro se è in esecuzione.
     */
    public void stop() {
        if (isRunning) {
            endTime = System.currentTimeMillis();
            elapsedTime = elapsedTime + endTime - startTime;
            isRunning = false;
        }
    }

    /**
     * Resetta il cronometro a zero.
     */
    public void reset() {
        startTime = 0L;
        endTime = 0L;
        elapsedTime = 0L;
        isRunning = false;
    }

    /**
     * Restituisce il tempo trascorso in millisecondi.
     * 
     * @return Tempo trascorso in millisecondi
     */
    public long getElapsedTime() {
        if (isRunning) {
            return System.currentTimeMillis() - startTime + elapsedTime;
        }
        return elapsedTime;
    }

    /**
     * Imposta il tempo trascorso.
     * 
     * @param elapsedTime tempo trascorso in millisecondi
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Restituisce true se il cronometro è in esecuzione.
     * 
     * @return true se il cronometro è in esecuzione, false altrimenti
     */

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Restituisce il tempo di inizio.
     * 
     * @return tempo di inizio del cronometro
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Restituisce il tempo formattato in maniera leggibile nel formato hh:mm:ss.
     * 
     * @return tempo formattato
     */
    public String getTimeFormatted() {
        try {
            Thread.sleep(1000);
            long time = getElapsedTime();
            long second = (time / 1000) % 60;
            long minute = (time / (1000 * 60)) % 60;
            long hour = (time / (1000 * 60 * 60)) % 24;
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } catch (InterruptedException e) {
            return "00:00:00";
        }
    }
}
