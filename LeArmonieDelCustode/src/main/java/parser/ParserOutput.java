package parser;

import grafica.InterfacciaGioco;
import tipi.Command;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class ParserOutput {

    private Command command;
    private InterfacciaGioco interfaccia;
    private Object params;

    /**
     * 
     * @param command
     * @param inputString
     * @param interfaccia
     */
    public ParserOutput(Command command, Object params, InterfacciaGioco interfaccia) {
        this.command = command;
        this.params = params;
        this.interfaccia = interfaccia;
    }

    /**
     *
     * @return command
     */
    public Command getCommand() {
        return command;
    }

    /**
     *
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * 
     * @return interfaccia
     */
    public InterfacciaGioco getInterfacciaGioco() {
        return interfaccia;
    }

    /**
     * 
     * @param interfaccia
     */
    public void setInterfacciaGioco(InterfacciaGioco interfaccia) {
        this.interfaccia = interfaccia;
        this.interfaccia = interfaccia;
    }

    /**
     * 
     * @return params
     */
    public Object getParams() {
        return params;
    }

}
