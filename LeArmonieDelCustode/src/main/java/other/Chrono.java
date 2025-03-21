package other;

import java.io.Serializable;

public class Chrono implements Serializable {
    private Long startTime;
    private Long endTime;
    private Long elapsedTime;
    private boolean isRunning;

    public Chrono() {
        reset();
    }

    /**
     * Il metodo start() inizializza il cronometro.
     */
    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    public void startAgain(long elapsedTime) {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            this.elapsedTime = elapsedTime;
            isRunning = true;
        }
    }

    /**
     * Il metodo stop() ferma il cronometro.
     */
    public void stop() {
        if (isRunning) {
            endTime = System.currentTimeMillis();
            elapsedTime = elapsedTime + endTime - startTime;
            isRunning = false;
        }
    }

    /**
     * Il metodo reset() resetta il cronometro.
     */
    public void reset() {
        startTime = 0L;
        endTime = 0L;
        elapsedTime = 0L;
        isRunning = false;
    }

    /**
     * Il metodo getElapsedTime() restituisce il tempo trascorso.
     * 
     * @return tempo trascorso
     */
    public long getElapsedTime() {
        if (isRunning) {
            return System.currentTimeMillis() - startTime + elapsedTime;
        }
        return elapsedTime;
    }

    /**
     * Il metodo setElapsedTime() imposta il tempo trascorso.
     * 
     * @param elapsedTime tempo trascorso
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Il metodo isRunning() restituisce true se il cronometro è in esecuzione.
     * 
     * @return true se il cronometro è in esecuzione, false altrimenti
     */

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Il metodo getStartTime() restituisce il tempo di inizio.
     * 
     * @return tempo di inizio
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Il metodo getTimeFormatted() restituisce il tempo formattato in maniera
     * leggibile nel formato hh:mm:ss.
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
