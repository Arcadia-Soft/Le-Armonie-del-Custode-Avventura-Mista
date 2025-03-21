package parser;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import grafica.InterfacciaGioco;
import other.Utils;
import tipi.Command;
import tipi.CommandType;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Parser implements Serializable {
    private final Set<String> stopwords;

    /**
     *
     * @param stopwords
     */
    public Parser(Set<String> stopwords) {
        this.stopwords = stopwords;
    }

    private int checkForCommand(String token, List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ATTENZIONE: il parser è implementato in modo abbastanza indipendente dalla
     * lingua, ma riconosce solo
     * frasi semplici del tipo <azione> <oggetto> <oggetto>. Eventuali articoli o
     * preposizioni vengono semplicemente
     * rimossi.
     *
     * @param command
     * @param commands
     * @param objects
     * @param inventory
     * @return
     */

    public ParserOutput parse(String command, List<Command> commands, InterfacciaGioco interfaccia) {
        List<String> tokens = Utils.parseString(command, stopwords);
        Command c = new Command(CommandType.NULL_COMMAND, null);
        if (!tokens.isEmpty()) {
            int ic = checkForCommand(tokens.get(0), commands);

            if (ic > -1) {
                c = commands.get(ic);
                return new ParserOutput(c, command, interfaccia);
            }

            return new ParserOutput(c, command, interfaccia);
        }

        return new ParserOutput(c, command, interfaccia);
    }

}
