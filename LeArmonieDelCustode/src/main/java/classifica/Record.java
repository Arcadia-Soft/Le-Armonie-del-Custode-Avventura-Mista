package classifica;

import java.io.Serializable;

/**
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Record implements Serializable{
    
    private final String nome;
    private final long timeTaken;
    private final String data;

    /**
     * Metodo costruttore della classe Record
     * @param nome
     * @param timeTaken
     * @param data
     */
    public Record(String nome, long timeTaken, String data) {
        this.nome = nome;
        this.timeTaken = timeTaken;
        this.data = data;
    }

    /**
     * Metodo che restituisce il tempo
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che restituisce il tempo registrato
     * @return
     */
    public long getTimeTaken() {
        return timeTaken;
    }

    /**
     * Metodo che restituisce la data del record
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     * Metodo che restituisce il tempo formattato (hh:mm:ss)
     * @return record
     */
    public String timeFormatted() {
        long second = (timeTaken / 1000) % 60;
        long minute = (timeTaken / (1000 * 60)) % 60;
        long hour = (timeTaken / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    
    /** 
    * Metodo che restistuice il Record formattato Record{name = '', timeTaken = '', data = ''} (DEBUG)
    * @return record
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