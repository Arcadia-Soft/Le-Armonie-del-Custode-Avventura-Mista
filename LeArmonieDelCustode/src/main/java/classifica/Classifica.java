package classifica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce la classifica dei giocatori.
 * Mantiene una lista di record contenenti le informazioni sui punteggi dei giocatori
 * come nome, tempo impiegato e data di completamento del gioco.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */

public class Classifica implements Serializable {

    private List<Record> records;

    /**
     * Costruttore della classe Classifica.
     * Inizializza una nuova lista vuota di record.
     */
    public Classifica() {
        records = new ArrayList<>();
    }

    /**
     * Aggiunge un nuovo record alla classifica.
     * 
     * @param record Il record da aggiungere alla classifica
     */
    public void addRecord(Record record) {
        records.add(record);
    }

    /**
     * Restituisce la lista completa dei record presenti nella classifica.
     * 
     * @return Lista di tutti i record nella classifica
     */
    public List<Record> getRecords() {
        return records;
    }

    /**
     * Imposta una nuova lista di record sostituendo quella esistente.
     * 
     * @param records La nuova lista di record da impostare
     */
    public void setRecords(List<Record> records) {
        this.records = records;
    }

    /**
     * Converte la lista di record in una matrice di stringhe.
     * Ogni riga della matrice rappresenta un record con:
     * - Prima colonna: nome del giocatore
     * - Seconda colonna: tempo impiegato formattato
     * - Terza colonna: data di completamento
     * 
     * @return Matrice di stringhe contenente i dati della classifica
     */
    public String[][] getRecordsAsMatrix() {
        String[][] array = new String[records.size()][3];
        for (int i = 0; i < records.size(); i++) {
            array[i][0] = records.get(i).getNome();
            array[i][1] = records.get(i).timeFormatted();
            array[i][2] = records.get(i).getData();
        }
        return array;
    }
}
