package eccezioni;

public class GameFileException extends Exception {

    @Override
    public String getMessage() {
        return "File di salvataggio corrotto/non trovato";
    }

}
