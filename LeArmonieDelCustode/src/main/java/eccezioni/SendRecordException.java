package eccezioni;

/**
 * Eccezione lanciata quando si verifica un errore durante l'invio
 * di un record al server.
 * Può essere causata da problemi di connessione o indisponibilità del server.
 */
public class SendRecordException extends Exception {

    @Override
    public String getMessage() {
        return "Errore durante l'invio del record al Server\no Server non disponibile";
    }

}
