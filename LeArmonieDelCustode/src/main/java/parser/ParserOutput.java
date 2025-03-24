package parser;

import grafica.InterfacciaGioco;
import tipi.Command;

/**
 * Classe che rappresenta l'output del parser del gioco.
 * Contiene il comando interpretato, i parametri associati e l'interfaccia di gioco.
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class ParserOutput {

    private Command command;
    private InterfacciaGioco interfaccia;
    private Object params;

    /**
     * Costruttore della classe ParserOutput.
     * 
     * @param command il comando interpretato dal parser
     * @param params i parametri associati al comando
     * @param interfaccia l'interfaccia grafica del gioco
     */
    public ParserOutput(Command command, Object params, InterfacciaGioco interfaccia) {
        this.command = command;
        this.params = params;
        this.interfaccia = interfaccia;
    }

    /**
     * Restituisce il comando interpretato.
     *
     * @return il comando corrente
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Imposta un nuovo comando.
     *
     * @param command il nuovo comando da impostare
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Restituisce l'interfaccia grafica del gioco.
     * 
     * @return l'interfaccia grafica corrente
     */
    public InterfacciaGioco getInterfacciaGioco() {
        return interfaccia;
    }

    /**
     * Imposta una nuova interfaccia grafica.
     * 
     * @param interfaccia la nuova interfaccia grafica da impostare
     */
    public void setInterfacciaGioco(InterfacciaGioco interfaccia) {
        this.interfaccia = interfaccia;
        this.interfaccia = interfaccia;
    }

    /**
     * Restituisce i parametri associati al comando.
     * 
     * @return i parametri del comando
     */
    public Object getParams() {
        return params;
    }
}