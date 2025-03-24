package classifica;

import java.io.Serializable;

/**
 * Classe che rappresenta un record nella classifica di gioco.
 * Contiene informazioni sul nome del giocatore, il tempo impiegato per completare il gioco
 * e la data di completamento.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Record implements Serializable{
    
    private final String nome;
    private final long timeTaken;
    private final String data;

    /**
     * Costruttore della classe Record.
     * 
     * @param nome Nome del giocatore
     * @param timeTaken Tempo impiegato in millisecondi
     * @param data Data di completamento del gioco
     */
    public Record(String nome, long timeTaken, String data) {
        this.nome = nome;
        this.timeTaken = timeTaken;
        this.data = data;
    }

    /**
     * Restituisce il nome del giocatore.
     * 
     * @return Nome del giocatore
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il tempo impiegato in millisecondi.
     * 
     * @return Tempo impiegato in millisecondi
     */
    public long getTimeTaken() {
        return timeTaken;
    }

    /**
     * Restituisce la data di completamento del gioco.
     * 
     * @return Data di completamento
     */
    public String getData() {
        return data;
    }

    /**
     * Formatta il tempo impiegato nel formato ore:minuti:secondi (hh:mm:ss).
     * 
     * @return Tempo formattato come stringa
     */
    public String timeFormatted() {
        long second = (timeTaken / 1000) % 60;
        long minute = (timeTaken / (1000 * 60)) % 60;
        long hour = (timeTaken / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * Restituisce una rappresentazione testuale del record.
     * Utile per scopi di debug.
     * 
     * @return Stringa che rappresenta il record
     */
    @Override
    public String toString() {
        return "Record{" +
                "name='" + nome + '\'' +
                ", timeTaken=" + timeFormatted() +
                ", data= " + data +
                '}';
    }
}
