package other;

/**
 * Interfaccia che definisce il comportamento di un oggetto osservabile nel gioco.
 * Implementa il pattern Observer per gestire le notifiche agli osservatori.
 * 
 * @author pieerpaolo
 */
public interface GameObservable {

    /**
     * Aggiunge un nuovo osservatore alla lista degli osservatori.
     * 
     * @param o L'osservatore da aggiungere
     */
    public void attach(GameObserver o);

    /**
     * Rimuove un osservatore dalla lista degli osservatori.
     * 
     * @param o L'osservatore da rimuovere
     */
    public void detach(GameObserver o);

    /**
     * Notifica tutti gli osservatori registrati di un cambiamento di stato.
     * Questo metodo dovrebbe essere chiamato quando si verifica un evento significativo.
     */
    public void notifyObservers();

}
