package eccezioni;

/**
 * Eccezione lanciata quando si verifica un errore durante la richiesta
 * della classifica al server.
 * Può essere causata da problemi di connessione o indisponibilità del server.
 */
public class GetClassificaException extends Exception {

    @Override
    public String getMessage() {
        return "Errore nella richiesta della classifica al Server\no Server al momento non disponibile";
    }

}
