package tipi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Classe che rappresenta un comando del gioco.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Command implements Serializable {

    private final CommandType type;

    private final String name;

    private Set<String> alias;

    /**
     * Costruttore della classe Command
     * @param type il tipo del comando
     * @param name il nome del comando
     */
    public Command(CommandType type, String name) {
        this.type = type;
        this.name = name;
        alias = new HashSet<>();
    }

    /**
     * Costruttore della classe Command con alias
     * @param type il tipo del comando
     * @param name il nome del comando
     * @param alias l'insieme degli alias del comando
     */
    public Command(CommandType type, String name, Set<String> alias) {
        this.type = type;
        this.name = name;
        this.alias = alias;
    }

    /**
     * Restituisce il nome del comando
     * @return il nome del comando
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce l'insieme degli alias del comando
     * @return l'insieme degli alias
     */
    public Set<String> getAlias() {
        return alias;
    }

    /**
     * Imposta l'insieme degli alias del comando
     * @param alias il nuovo insieme di alias
     */
    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    /**
     * Imposta gli alias del comando da un array di stringhe
     * @param alias l'array di stringhe contenente i nuovi alias
     */
    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    /**
     * Restituisce il tipo del comando
     * @return il tipo del comando
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Calcola l'hash code dell'oggetto Command
     * @return il valore hash calcolato
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.type);
        return hash;
    }

    /**
     * Verifica l'uguaglianza tra due oggetti Command
     * @param obj l'oggetto da confrontare
     * @return true se gli oggetti sono uguali, false altrimenti
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
        final Command other = (Command) obj;
        return this.type == other.type;
    }

}
