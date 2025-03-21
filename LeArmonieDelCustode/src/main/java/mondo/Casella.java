package mondo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import tipi.Item;

/**
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
    private int quantity = 0;

    /**
     *
     * @param id
     */
    public Casella(int id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param nome
     * @param descrizione
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
     * 
     * @return
     */
    public Casella getSouth() {
        return south;
    }

    /**
     *
     * @param south
     */
    public void setSouth(Casella south) {
        this.south = south;
    }

    /**
     *
     * @return
     */
    public Casella getNorth() {
        return north;
    }

    /**
     *
     * @param north
     */
    public void setNorth(Casella north) {
        this.north = north;
    }

    /**
     *
     * @return
     */
    public Casella getEast() {
        return east;
    }

    /**
     *
     * @param east
     */
    public void setEast(Casella east) {
        this.east = east;
    }

    /**
     *
     * @return
     */
    public Casella getWest() {
        return west;
    }

    /**
     *
     * @param west
     */
    public void setWest(Casella west) {
        this.west = west;
    }

    /**
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

    public Set<Item> getOggetti() {
        return oggetti.keySet();
    }

    public void addOggetto(Item oggetto, int quantity) {
        if (oggetti.containsKey(oggetto)) {
            oggetti.put(oggetto, oggetti.get(oggetto) + quantity);
        } else {
            oggetti.put(oggetto, quantity);
        }
    }

    public int removeOggetto(Item oggetto) {
        int quantity = 0;
        if (oggetti.containsKey(oggetto)) {
            quantity = oggetti.get(oggetto);
            oggetti.remove(oggetto);
        }
        return quantity;
    }

    public void clearOggetto(Item oggetto) {
        oggetti.remove(oggetto);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        visited = true;
        this.updated = updated;
    }

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