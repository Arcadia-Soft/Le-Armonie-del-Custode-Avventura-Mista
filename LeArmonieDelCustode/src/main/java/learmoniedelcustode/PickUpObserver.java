package learmoniedelcustode;

import java.util.regex.Pattern;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import parser.ParserOutput;
import tipi.Item;

public class PickUpObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.PICK_UP) {
            Object args = parserOutput.getParams();
            if (args == null) {
                msg = "Sii più specifico, svegliati... (usa il comando 'Prendi' seguito dal nome dell'oggetto)";
                return msg;
            }
            if (args instanceof String[]) {
                String[] items = (String[]) args;
                if (items.length == 1){
                    String nameOBJ = items[0].toLowerCase();
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

                        if (description.getInventario().contains("liuto leggendario", "pentagramma armonico")) {
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
                        msg = "Non c'è nessun oggetto con quel nome qui!!!";
                    }
                } else {
                    msg = "Puoi prendere solo un oggetto per volta!!!";
                }
            } else {
                msg = "Sii più specifico, svegliati... (usa il comando 'Prendi' seguito dal nome dell'oggetto)";
            }
        }
        return msg;
    }
}