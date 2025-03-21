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

    private String inputString;

    /**
     * 
     * @param command
     * @param inputString
     * @param interfaccia
     */
    public ParserOutput(Command command, String inputString, InterfacciaGioco interfaccia) {
        this.command = command;
        this.inputString = inputString;
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
     * @return imgLabel
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
     * @return parsedInputString
     */
    public String getInputString() {
        return inputString;
    }

}
