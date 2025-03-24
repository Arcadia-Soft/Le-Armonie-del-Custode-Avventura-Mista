package tipi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
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

    public Item(String name) {
        this.name = name;
        description = "";
        alias = new HashSet<>();
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        alias = new HashSet<>();
    }

    public Item(String name, String description, Set<String> alias) {
        this.name = name;
        this.description = description;
        this.alias = alias;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    public boolean isPickable() {
        return pickable;
    }

    public void setPickable(boolean consumable) {
        this.pickable = consumable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

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

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
