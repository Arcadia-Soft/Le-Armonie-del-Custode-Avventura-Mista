package tipi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che rappresenta un oggetto generico nel gioco.
 * Ogni oggetto ha un nome, una descrizione, degli alias (nomi alternativi),
 * può essere raccoglibile e può essere visibile o nascosto.
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Item implements Serializable {

    private String name;
    private String description;
    private Set<String> alias;
    private boolean pickable = false;
    private boolean visible = false;

    /**
     * Costruttore che inizializza un oggetto con solo il nome
     * @param name il nome dell'oggetto
     */
    public Item(String name) {
        this.name = name;
        description = "";
        alias = new HashSet<>();
    }

    /**
     * Costruttore che inizializza un oggetto con nome e descrizione
     * @param name il nome dell'oggetto
     * @param description la descrizione dell'oggetto
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        alias = new HashSet<>();
    }

    /**
     * Costruttore che inizializza un oggetto con nome, descrizione e alias
     * @param name il nome dell'oggetto
     * @param description la descrizione dell'oggetto
     * @param alias set di nomi alternativi dell'oggetto
     */
    public Item(String name, String description, Set<String> alias) {
        this.name = name;
        this.description = description;
        this.alias = alias;
    }

    /**
     * Restituisce il set di alias dell'oggetto
     * @return set di alias dell'oggetto
     */
    public Set<String> getAlias() {
        return alias;
    }

    /**
     * Restituisce il nome dell'oggetto
     * @return il nome dell'oggetto
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome dell'oggetto
     * @param name il nuovo nome dell'oggetto
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce la descrizione dell'oggetto
     * @return la descrizione dell'oggetto
     */
    public String getDescription() {
        return description;
    }

    /**
     * Imposta la descrizione dell'oggetto
     * @param description la nuova descrizione dell'oggetto
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Imposta gli alias dell'oggetto
     * @param alias set di nuovi alias
     */
    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    /**
     * Imposta gli alias dell'oggetto da un array di stringhe
     * @param alias array di nuovi alias
     */
    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    /**
     * Verifica se l'oggetto può essere raccolto
     * @return true se l'oggetto è raccoglibile, false altrimenti
     */
    public boolean isPickable() {
        return pickable;
    }

    /**
     * Imposta se l'oggetto può essere raccolto
     * @param consumable true se l'oggetto deve essere raccoglibile, false altrimenti
     */
    public void setPickable(boolean consumable) {
        this.pickable = consumable;
    }

    /**
     * Verifica se l'oggetto è visibile
     * @return true se l'oggetto è visibile, false altrimenti
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Imposta la visibilità dell'oggetto
     * @param visible true se l'oggetto deve essere visibile, false altrimenti
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Verifica l'uguaglianza tra questo oggetto e un altro
     * @param obj l'oggetto da confrontare
     * @return true se gli oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        final Item other = (Item) obj;
        return this.name.equals(other.name);
    }

    /**
     * Calcola l'hash code dell'oggetto basato sul nome
     * @return hash code dell'oggetto
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
