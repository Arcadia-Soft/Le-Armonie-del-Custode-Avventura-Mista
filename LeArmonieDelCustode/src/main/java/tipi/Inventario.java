package tipi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Classe che rappresenta l'inventario del giocatore.
 * L'inventario è implementato come una HashMap, dove la chiave è un oggetto
 * Item
 * rappresentante l'oggetto (sottoforma di Stringa) e il valore è la quantità di
 * quell'oggetto.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Inventario implements Serializable {
    private final HashMap<Item, Integer> inventario;

    public Inventario() {
        inventario = new HashMap<>();
    }

    /**
     * Metodo per aggiungere un oggetto all'inventario.
     * 
     * @param oggetto
     */
    public void addOggetto(Item oggetto, int quantity) {
        if (inventario.containsKey(oggetto)) {
            inventario.put(oggetto, inventario.get(oggetto) + quantity);
        } else {
            inventario.put(oggetto, quantity);
        }
    }

    /**
     * Metodo per rimuovere un oggetto dall'inventario. (per comodità, non potrà
     * usarlo il giocatore, ma solo il programmatore)
     * 
     * @param oggetto
     */
    public void remove(Item oggetto) {
        if (inventario.containsKey(oggetto)) {
            if (inventario.get(oggetto) > 1) {
                inventario.put(oggetto, inventario.get(oggetto) - 1);
            } else {
                inventario.remove(oggetto);
            }
        }
    }

    /**
     * Metodo per ricevere la dimensione dell'inventario. (numero di oggetti
     * presenti, utile per stamparli)
     * 
     * @return int per la dimensione dell'inventario.
     */
    public int getSize() {
        return inventario.size();
    }

    /**
     * Metodo per ricevere l'insieme di oggetti presenti nell'inventario.
     * 
     * @return Set di oggetti presenti nell'inventario.
     */
    public Set<Item> getOggetti() {
        return inventario.keySet();
    }

    /**
     * Metodo per controllare se un oggetto è presente nell'inventario.
     * 
     * @param oggetto
     * @return true se l'oggetto è presente, false altrimenti.
     */
    public boolean contains(Item oggetto) {
        return inventario.containsKey(oggetto);
    }

    public boolean contains(String... oggetto) {
        for (String nameItem : oggetto) {
            boolean found = false;
            nameItem = nameItem.toLowerCase().trim();
            for (Item item : inventario.keySet()) {
                if (item.getName().equals(nameItem) || item.getAlias().contains(nameItem)) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo per ricevere la quantità di un oggetto presente nell'inventario.
     * 
     * @param oggetto
     * @return int per la quantità dell'oggetto.
     */
    public int getQuantity(Item oggetto) {
        if (!inventario.containsKey(oggetto)) {
            return 0;
        }
        return inventario.get(oggetto);
    }

    /**
     * Metodo per svuotare l'inventario.
     * (metodo non utilizzato per questa release)
    public void clear() {
        inventario.clear();
    }

    /**
     * Metodo per controllare se l'inventario è vuoto.
     * (metodo non utilizzato per questa release)
     * @return true se l'inventario è vuoto, false altrimenti.
     *
    public boolean isEmpty() {
        return inventario.isEmpty();
    }

    /**
     * Metodo per stampare l'inventario.
     * Stampa l'inventario con il formato "NomeOggetto xQuantità" (utile per il
     * debug).
     * (metodo non utilizzato per questa release)
     *
    public void printInventario() {
        if (inventario.isEmpty()) {
            System.out.println("Inventario vuoto");
        } else {
            System.out.println("Inventario:");
            inventario.forEach((k, v) -> System.out.println(k.getName() + " x" + v));
        }
    }*/

    /**
     * Metodo per ricevere l'inventario sottoforma di matrice di Stringhe.
     * 
     * @return String[][] per l'inventario.
     */
    public String[][] getInventarioToJTableData() {
        String[][] invS = new String[inventario.size()][2];
        int i = 0;

        for (Item item : inventario.keySet()) {
            invS[i][0] = Pattern.compile("^.").matcher(item.getName()).replaceFirst(m -> m.group().toUpperCase());
            invS[i][1] = inventario.get(item).toString();
            i++;
        }
        return invS;
    }

    /**
     * Metodo per ricevere l'inventario come oggetto.
     * 
     * @return HashMap<Item, Integer> per l'inventario.
     */
    public HashMap<Item, Integer> getInventario() {
        return inventario;
    }
}
