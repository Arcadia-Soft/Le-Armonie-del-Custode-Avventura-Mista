package eccezioni;

/**
 * Eccezione lanciata quando non è possibile inizializzare il gioco.
 * Può essere causata da problemi con le risorse necessarie o errori di configurazione.
 */
public class GameNotAvailableException extends Exception {

    @Override
    public String getMessage() {
        return "Errore nell'inizializzazione del gioco";
    }

}
