package eccezioni;

public class SendRecordException extends Exception {

    @Override
    public String getMessage() {
        return "Errore durante l'invio del record al Server\no Server non disponibile";
    }

}
