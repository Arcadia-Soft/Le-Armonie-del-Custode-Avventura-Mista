package tipi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Classe che rappresenta l'inventario del giocatore.
 * L'inventario è implementato come una HashMap, dove la chiave è un oggetto Item
 * rappresentante l'oggetto (sottoforma di Stringa) e il valore è la quantità di
 * quell'oggetto.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Inventario implements Serializable {
    private final HashMap<Item, Integer> inventario;

    /**
     * Costruttore che inizializza un nuovo inventario vuoto.
     */
    public Inventario() {
        inventario = new HashMap<>();
    }

    /**
     * Metodo per aggiungere un oggetto all'inventario.
     * Se l'oggetto è già presente, ne incrementa la quantità.
     * Se l'oggetto non è presente, lo aggiunge con la quantità specificata.
     * 
     * @param oggetto L'oggetto da aggiungere all'inventario
     * @param quantity La quantità dell'oggetto da aggiungere
     */
    public void addOggetto(Item oggetto, int quantity) {
        if (inventario.containsKey(oggetto)) {
            inventario.put(oggetto, inventario.get(oggetto) + quantity);
        } else {
            inventario.put(oggetto, quantity);
        }
    }

    /**
     * Metodo per rimuovere un oggetto dall'inventario.
     * Se la quantità dell'oggetto è maggiore di 1, ne decrementa la quantità.
     * Se la quantità è 1, rimuove completamente l'oggetto dall'inventario.
     * 
     * @param oggetto L'oggetto da rimuovere dall'inventario
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
     * Metodo per ricevere la dimensione dell'inventario.
     * Restituisce il numero di tipi di oggetti diversi presenti nell'inventario.
     * 
     * @return Il numero di tipi di oggetti nell'inventario
     */
    public int getSize() {
        return inventario.size();
    }

    /**
     * Metodo per ricevere l'insieme di oggetti presenti nell'inventario.
     * 
     * @return Un Set contenente tutti gli oggetti presenti nell'inventario
     */
    public Set<Item> getOggetti() {
        return inventario.keySet();
    }

    /**
     * Metodo per controllare se un oggetto specifico è presente nell'inventario.
     * 
     * @param oggetto L'oggetto da cercare
     * @return true se l'oggetto è presente, false altrimenti
     */
    public boolean contains(Item oggetto) {
        return inventario.containsKey(oggetto);
    }

    /**
     * Metodo per controllare se uno o più oggetti sono presenti nell'inventario.
     * La ricerca viene effettuata sia sul nome dell'oggetto che sui suoi alias.
     * 
     * @param oggetto Uno o più nomi di oggetti da cercare
     * @return true se tutti gli oggetti specificati sono presenti, false altrimenti
     */
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
     * @param oggetto L'oggetto di cui si vuole conoscere la quantità
     * @return La quantità dell'oggetto presente, 0 se l'oggetto non è presente
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
     * Converte l'inventario in un formato adatto per essere visualizzato in una JTable.
     * La prima colonna contiene i nomi degli oggetti con la prima lettera maiuscola,
     * la seconda colonna contiene le relative quantità.
     * 
     * @return Una matrice di stringhe rappresentante l'inventario
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
     * Metodo per ricevere l'inventario come oggetto HashMap.
     * 
     * @return L'HashMap che rappresenta l'inventario, dove le chiavi sono gli oggetti
     *         e i valori sono le relative quantità
     */
    public HashMap<Item, Integer> getInventario() {
        return inventario;
    }
}
