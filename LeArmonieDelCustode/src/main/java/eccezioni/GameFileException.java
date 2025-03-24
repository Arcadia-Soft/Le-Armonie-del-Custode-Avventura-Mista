package eccezioni;

/**
 * Eccezione lanciata quando si verifica un errore durante il caricamento
 * di un file di salvataggio del gioco.
 * Può essere causata da file corrotti o non trovati.
 */
public class GameFileException extends Exception {

    @Override
    public String getMessage() {
        return "File di salvataggio corrotto/non trovato";
    }

}
