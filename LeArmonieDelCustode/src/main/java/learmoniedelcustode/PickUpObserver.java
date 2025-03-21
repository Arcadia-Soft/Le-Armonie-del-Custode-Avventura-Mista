package learmoniedelcustode;

import java.util.regex.Pattern;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;
import tipi.Item;

public class PickUpObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.PICK_UP) {
            String command = parserOutput.getInputString().split(" ")[0] + "\\s+";
            String[] inputParsed = parserOutput.getInputString().split(command);
            if (inputParsed.length == 2) {
                String nameOBJ = inputParsed[1].toLowerCase();
                Item pickUpItem = description.getCurrentCasella().getOggetti().stream()
                        .filter(i -> i.getName().equalsIgnoreCase(nameOBJ) || i.getAlias().contains(nameOBJ))
                        .findFirst()
                        .orElse(null);

                if (pickUpItem != null && pickUpItem.isVisible()) {
                    int quantity = description.getCurrentCasella().removeOggetto(pickUpItem);
                    msg = "Hai raccolto ";
                    if (quantity > 1) {
                        msg += "x" + quantity + " ";
                    }
                    msg += Pattern.compile("^.").matcher(pickUpItem.getName())
                            .replaceFirst(m -> m.group().toUpperCase());

                    description.getInventario().addOggetto(pickUpItem, quantity);
                    if (pickUpItem.getName().equals("pentagramma armonico")
                            && description.getInventario().contains(new Item("liuto leggendario"))
                            || pickUpItem.getName().equals("liuto leggendario")
                                    && description.getInventario().contains(new Item("pentagramma armonico"))) {
                        description.getCaselle().stream()
                                .filter(c -> c.getId() == 106 || c.getId() == 107)
                                .forEach(c -> {
                                    if (c.getId() == 106)
                                        c.setUpdated(true);
                                    else if (c.getId() == 107)
                                        c.setEnterable(true);
                                });
                    }
                } else {
                    msg = "Nessun oggetto che si chiama " + nameOBJ;
                }
            } else {
                msg = "Sii più specifico, svegliati... (usa il comando 'Prendi' seguito dal nome dell'oggetto)";
            }
            // Stampa DEBUG
            // System.out.println(parserOutput.getInputString());
        }
        return msg;
    }

}
