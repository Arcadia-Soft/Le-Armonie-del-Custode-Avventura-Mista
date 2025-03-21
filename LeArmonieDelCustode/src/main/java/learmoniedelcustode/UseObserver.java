package learmoniedelcustode;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.Command;
import tipi.CommandType;
import tipi.Item;

/**
 * Classe che implementa l'interfaccia GameObserver e che si occupa di gestire
 * l'uso degli oggetti.
 * In base alla casella in cui ci si trova e all'oggetto che si vuole usare,
 * viene restituito un messaggio
 * che indica se l'oggetto è stato usato correttamente o meno.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class UseObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.USE) {
            String command = parserOutput.getInputString().split(" ")[0] + "\\s+";
            String[] inputParsed = parserOutput.getInputString().split(command);
            if (inputParsed.length == 2) {
                String nameOBJ = inputParsed[1].toLowerCase();
                Item itemToUse = description.getInventarioSet().stream()
                        .filter(i -> i.getName().toLowerCase().equals(nameOBJ) || i.getAlias().contains(nameOBJ))
                        .findFirst()
                        .orElse(null);
                if (itemToUse != null) {
                    switch (description.getCurrentCasella().getId()) {
                        case 209 -> {
                            switch (itemToUse.getName()) {
                                case "coltello" -> {
                                    description.getCaselle()
                                            .stream()
                                            .filter(c -> c.getId() > 200 && c.getId() < 210)
                                            .forEach(action -> action.setUpdated(true));
                                    msg = "Hai liberato il bardo! \"Grazie mille pastore, prendi questa \"Ballata del Montone\", è l'unica cosa che mi è rimasta.\"";
                                    description.getCurrentCasella().getOggetti()
                                            .stream()
                                            .filter(i -> i.getName().equals("ballata del montone"))
                                            .forEach(action -> {
                                                action.setVisible(true);
                                                action.setPickable(true);
                                            });
                                }
                                case "ballata del montone" ->
                                    msg = "La ballata recita così: \"Baa baa, balla o mio montone, hai perso la coda, hai perso il corno, ma io ti voglio bene lo stesso, perché sei il mio montone! \n Il ballo consiste in due passi avanti, due passi indietro, uno a destra ed uno sinistra, capriola!\" ";
                                default -> msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 526 -> {
                            if (nameOBJ.equals("chiave del tempio") || nameOBJ.equals("chiave")) {
                                description.getCurrentCasella().setUpdated(true);
                                description.getCurrentCasella().getWest().setEnterable(true);
                                msg = "La porta d'avanti a te è aperta! Puoi proseguire!";
                            } else {
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 107 -> {
                            if (nameOBJ.equals("liuto leggendario") || nameOBJ.equals("liuto")
                                    && description.getInventario().contains(new Item("pentagramma armonico"))) {
                                parserOutput.setCommand(new Command(CommandType.THE_END, null));
                            } else {
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui, sbrigati!";
                            }
                        }

                        default -> {
                            if (nameOBJ.equals("ballata del montone") || nameOBJ.equals("ballata")) {
                                msg = "La ballata recita così: \"Baa baa, o mio montone, hai perso la coda, hai perso il corno, ma io ti voglio bene lo stesso, perché sei il mio montone! \n Il ballo consiste in due passi avanti, due passi indietro, uno a destra ed uno sinistra, capriola!\" ";
                            } else {
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }
                    }
                } else {
                    msg = "Non hai l'oggetto " + nameOBJ;
                }
            } else {
                msg = "Sii più specifico, svegliati... (usa il comando 'Usa' seguito dal nome dell'oggetto)";
            }
        }
        return msg;
    }

}
