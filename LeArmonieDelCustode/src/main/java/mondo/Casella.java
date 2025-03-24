package mondo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import tipi.Item;

/**
 * Classe che rappresenta una casella (stanza/luogo) nel mondo di gioco.
 * Ogni casella ha un identificatore, un nome, descrizioni, collegamenti con altre caselle
 * e può contenere oggetti. Gestisce anche lo stato di visibilità e accessibilità.
 * 
 * @author Kevin Saracino
 * @author Alessandro Pellegrino
 */
public class Casella implements Serializable {
    private final int id;
    private String nome;
    private String descrizioneIniziale;
    private String descrizioneAggiornata;
    private String descrizioneAggiuntiva;
    private boolean enterable = true;
    private boolean visited = false;
    private boolean updated = false;

    private Casella south = null;
    private Casella north = null;
    private Casella east = null;
    private Casella west = null;

    private final Map<Item, Integer> oggetti = new HashMap<>();

    /**
     * Costruttore che inizializza una casella con un identificatore.
     * 
     * @param id Identificatore univoco della casella
     */
    public Casella(int id) {
        this.id = id;
    }

    /**
     * Costruttore che inizializza una casella con identificatore, nome e descrizione.
     * 
     * @param id Identificatore univoco della casella
     * @param nome Nome della casella
     * @param descrizione Descrizione iniziale della casella
     */
    public Casella(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizioneIniziale = descrizione;
    }

    /**
     * Metodo getter per il nome della casella
     * 
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo setter per il nome della casella
     * 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo getter per la descrizione della casella
     * 
     * @return
     */
    public String getDescrizione() {
        if (visited) {
            if (descrizioneAggiornata.length() != 0 && updated)
                return descrizioneAggiornata + printItems();
            else
                return descrizioneAggiuntiva + printItems();
        }

        this.visited = true;
        return descrizioneIniziale;
    }

    /**
     * Metodo setter per la descrizione iniziale della casella
     * 
     * @param descrizione
     */
    public void setDescrizioneIniziale(String descrizione) {
        this.descrizioneIniziale = descrizione;
    }

    /**
     * Metodo setter per la descrizione aggiornata della casella
     * 
     * @param descrizioneAggiornata
     */
    public void setDescrizioneAggiornata(String descrizioneAggiornata) {
        this.descrizioneAggiornata = descrizioneAggiornata;
    }

    /**
     * Metodo setter per la descrizione aggiuntiva della casella
     * 
     * @param descrizioneAggiornata
     */
    public void setDescrizioneAggiuntiva(String descrizioneAggiuntiva) {
        this.descrizioneAggiuntiva = descrizioneAggiuntiva;
    }

    /**
     * Metodo di verifica della visibilità della casella
     * 
     * @return
     */
    public boolean isEnterable() {
        return enterable;
    }

    /**
     * Metodo setter per la visibilità della casella
     * 
     * @param visible
     */
    public void setEnterable(boolean visible) {
        this.enterable = visible;
    }

    /**
     * Metodo getter per la casella a sud
     * 
     * @return
     */
    public Casella getSouth() {
        return south;
    }

    /**
     * Metodo setter per la casella a sud
     *
     * @param south
     */
    public void setSouth(Casella south) {
        this.south = south;
    }

    /**
     * Metodo getter per la casella a nord
     *
     * @return
     */
    public Casella getNorth() {
        return north;
    }

    /**
     * Metodo setter per la casella a nord
     *
     * @param north
     */
    public void setNorth(Casella north) {
        this.north = north;
    }

    /**
     * Metodo getter per la casella a est
     *
     * @return
     */
    public Casella getEast() {
        return east;
    }

    /**
     * Metodo setter per la casella a est
     *
     * @param east
     */
    public void setEast(Casella east) {
        this.east = east;
    }

    /**
     * Metodo getter per la casella a ovest
     *
     * @return
     */
    public Casella getWest() {
        return west;
    }

    /**
     * Metodo setter per la casella a ovest
     *
     * @param west
     */
    public void setWest(Casella west) {
        this.west = west;
    }

    /**
     * Metodo getter per l'identificatore della casella
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casella other = (Casella) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * Restituisce l'insieme di tutti gli oggetti presenti nella casella
     * 
     * @return Set di oggetti di tipo Item presenti nella casella
     */
    public Set<Item> getOggetti() {
        return oggetti.keySet();
    }

    /**
     * Aggiunge un oggetto alla casella con la quantità specificata.
     * Se l'oggetto è già presente, ne incrementa la quantità.
     * 
     * @param oggetto l'oggetto da aggiungere alla casella
     * @param quantity la quantità dell'oggetto da aggiungere
     */
    public void addOggetto(Item oggetto, int quantity) {
        if (oggetti.containsKey(oggetto)) {
            oggetti.put(oggetto, oggetti.get(oggetto) + quantity);
        } else {
            oggetti.put(oggetto, quantity);
        }
    }

    /**
     * Rimuove un oggetto dalla casella e restituisce la quantità rimossa
     * 
     * @param oggetto l'oggetto da rimuovere
     * @return la quantità dell'oggetto che è stata rimossa
     */
    public int removeOggetto(Item oggetto) {
        int quantity = 0;
        if (oggetti.containsKey(oggetto)) {
            quantity = oggetti.get(oggetto);
            oggetti.remove(oggetto);
        }
        return quantity;
    }

    /**
     * Rimuove completamente un oggetto dalla casella indipendentemente dalla sua quantità
     * 
     * @param oggetto l'oggetto da rimuovere
     */
    public void clearOggetto(Item oggetto) {
        oggetti.remove(oggetto);
    }

    /**
     * Verifica se la casella è stata aggiornata
     * 
     * @return true se la casella è stata aggiornata, false altrimenti
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Imposta lo stato di aggiornamento della casella e la marca come visitata
     * 
     * @param updated il nuovo stato di aggiornamento
     */
    public void setUpdated(boolean updated) {
        visited = true;
        this.updated = updated;
    }

    /**
     * Crea una stringa formattata con l'elenco degli oggetti visibili presenti nella casella
     * 
     * @return una stringa contenente l'elenco degli oggetti visibili con le relative quantità
     */
    public String printItems() {
        String msg = "";

        for (Item item : oggetti.keySet()) {
            if (item.isVisible()) {
                msg += "    " + Pattern.compile("^.").matcher(item.getName()).replaceFirst(m -> m.group().toUpperCase())
                        + " x" + oggetti.get(item) + "\n";
            }
        }

        if (msg.length() != 0) {
            msg = "\nNella stanza sono presenti i seguenti oggetti:\n" + msg;
        }

        return msg;
    }

    /*
     * Prova funzionamento caselle
     * public static void main(String[] args) {
     * Casella c = new Casella(1, "Cucina",
     * "Cucina di casa tua, molto carina veramente.");
     * Casella Nord = new Casella(2, "Sala",
     * "Sala di casa tua, molto carina veramente.");
     * Casella Sud = new Casella(3, "Bagno",
     * "Bagno di casa tua, fa schifo veramente.");
     * 
     * c.setNorth(Nord);
     * c.setSouth(Sud);
     * 
     * System.out.println(c.getNorth().getnome());
     * System.out.println(c.getSouth().getnome());
     * }
     */

}