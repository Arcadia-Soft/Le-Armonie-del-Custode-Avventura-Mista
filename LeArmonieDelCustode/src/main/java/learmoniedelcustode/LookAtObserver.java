package learmoniedelcustode;

import java.util.HashSet;
import java.util.Set;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import parser.ParserOutput;
import tipi.Item;

public class LookAtObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.LOOK_AT) {
            Object args = parserOutput.getParams();
            if (args == null){
                msg = description.getCurrentCasella().getNome() + "\n" + "-".repeat(description.getCurrentCasella().getNome().length() + 3);
                msg += "\n" + description.getCurrentCasella().getDescrizione();
            } else if (args instanceof String[]){
                String[] items = (String[]) args;
                if (items.length != 1){
                    msg = "Ehi calma! Hai solo due occhi, osserva un oggetto alla volta!";
                } else {
                    String nameOBJ = items[0].toLowerCase();
                    Set<Item> allItems = new HashSet<>();
                    allItems.addAll(description.getCurrentCasella().getOggetti());
                    allItems.addAll(description.getInventario().getOggetti());

                    Item item = allItems.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(nameOBJ) || i.getAlias().contains(nameOBJ))
                    .findFirst()
                    .orElse(null);

                    if (item != null) {
                        msg = item.getDescription();
                    } else {
                        msg = "Mmh... ho guardato ovunque ma non trovo nulla che assomigli a " + nameOBJ + ". Forse mi serve un paio di occhiali?";
                    }
                }
            }
        }
        return msg;
    }

}
