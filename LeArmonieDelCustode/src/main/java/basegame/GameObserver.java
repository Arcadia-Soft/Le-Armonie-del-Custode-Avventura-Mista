package basegame;

import parser.ParserOutput;

/**
 * Interfaccia che definisce un osservatore del gioco secondo il pattern Observer.
 * Viene utilizzata per aggiornare la vista quando lo stato del gioco cambia.
 * 
 * @author pierpaolo
 */
public interface GameObserver {

    /**
     * Metodo chiamato quando lo stato del gioco viene aggiornato.
     * 
     * @param description la descrizione aggiornata del gioco
     * @param parserOutput l'output del parser che ha causato l'aggiornamento
     * @return una stringa contenente il risultato dell'aggiornamento
     */
    public String update(GameDescription description, ParserOutput parserOutput);

}
