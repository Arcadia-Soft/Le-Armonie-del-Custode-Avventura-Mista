package tipi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Source code by:
 * @author pierpaolo
 * 
 * Modified by:
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Command implements Serializable {

    private final CommandType type;

    private final String name;

    private Set<String> alias;

    /**
     *
     * @param type
     * @param name
     */
    public Command(CommandType type, String name) {
        this.type = type;
        this.name = name;
        alias = new HashSet<>();
    }

    /**
     *
     * @param type
     * @param name
     * @param alias
     */
    public Command(CommandType type, String name, Set<String> alias) {
        this.type = type;
        this.name = name;
        this.alias = alias;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public Set<String> getAlias() {
        return alias;
    }

    /**
     *
     * @param alias
     */
    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    /**
     *
     * @param alias
     */
    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    /**
     *
     * @return
     */
    public CommandType getType() {
        return type;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.type);
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
        final Command other = (Command) obj;
        return this.type == other.type;
    }

}
