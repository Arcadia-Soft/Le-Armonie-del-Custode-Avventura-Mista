package other;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mondo.Casella;
import parser.ParserOutput;
import tipi.Command;
import tipi.CommandType;
import tipi.Dialogo;
import tipi.Inventario;
import tipi.Item;

/**
 * Classe astratta che rappresenta la descrizione base del gioco.
 * Contiene le strutture dati e i metodi principali per gestire lo stato del gioco.
 * 
 * @author pierpaolo
 */
public abstract class GameDescription implements Serializable {

    /** Lista delle caselle del gioco */
    private final List<Casella> Caselle = new ArrayList<>();
    
    /** Lista dei comandi disponibili nel gioco */
    private final List<Command> commands = new ArrayList<>();
    
    /** Lista dei dialoghi presenti nel gioco */
    private List<Dialogo> dialoghi;
    
    /** Ultimo comando eseguito nel gioco */
    private Command lastCommand = new Command(CommandType.NULL_COMMAND, null);
    
    /** Inventario del giocatore */
    private final Inventario inventario = new Inventario();
    
    /** Cronometro per gestire il tempo di gioco */
    private Chrono chrono = new Chrono();

    /** Casella corrente in cui si trova il giocatore */
    private Casella currentCasella;

    /**
     * Restituisce la lista delle caselle del gioco
     * @return Lista delle caselle
     */
    public List<Casella> getCaselle() {
        return Caselle;
    }

    /**
     * Restituisce la lista dei comandi disponibili
     * @return Lista dei comandi
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Restituisce la casella corrente
     * @return Casella corrente
     */
    public Casella getCurrentCasella() {
        return currentCasella;
    }

    /**
     * Restituisce la lista dei dialoghi
     * @return Lista dei dialoghi
     */
    public List<Dialogo> getDialoghi() {
        return dialoghi;
    }

    /**
     * Imposta la lista dei dialoghi
     * @param dialoghi Lista dei dialoghi da impostare
     */
    public void setDialoghi(List<Dialogo> dialoghi) {
        this.dialoghi = dialoghi;
    }

    /**
     * Imposta la casella corrente
     * @param currentCasella Casella da impostare come corrente
     */
    public void setCurrentCasella(Casella currentCasella) {
        this.currentCasella = currentCasella;
    }

    /**
     * Restituisce l'inventario del giocatore
     * @return Inventario del giocatore
     */
    public Inventario getInventario() {
        return inventario;
    }

    /**
     * Restituisce il set degli oggetti nell'inventario
     * @return Set degli oggetti nell'inventario
     */
    public Set<Item> getInventarioSet() {
        return inventario.getOggetti();
    }

    /**
     * Restituisce l'ultimo comando eseguito
     * @return Ultimo comando eseguito
     */
    public Command getLastCommand() {
        return lastCommand;
    }

    /**
     * Imposta l'ultimo comando eseguito
     * @param lastCommand Comando da impostare come ultimo eseguito
     */
    public void setLastCommand(Command lastCommand) {
        this.lastCommand = lastCommand;
    }

    /**
     * Restituisce il cronometro di gioco
     * @return Cronometro di gioco
     */
    public Chrono getChrono() {
        return chrono;
    }

    /**
     * Imposta il cronometro di gioco
     * @param chrono Cronometro da impostare
     */
    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
    }

    /**
     * Restituisce l'introduzione del gioco
     * @return Stringa contenente l'introduzione
     */
    public abstract String getIntro();

    /**
     * Inizializza il gioco
     * @throws Exception in caso di errori durante l'inizializzazione
     */
    public abstract void init() throws Exception;

    /**
     * Gestisce la prossima mossa del gioco
     * @param p Output del parser che ha interpretato il comando
     * @param out Oggetto per la stampa del testo
     */
    public abstract void nextMove(ParserOutput p, StampaTesto out);
}
