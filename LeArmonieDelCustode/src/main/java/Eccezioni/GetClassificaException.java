package eccezioni;

public class GetClassificaException extends Exception {

    @Override
    public String getMessage() {
        return "Errore nella richiesta della classifica al Server\no Server al momento non disponibile";
    }

}
