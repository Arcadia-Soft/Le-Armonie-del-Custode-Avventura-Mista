package learmoniedelcustode;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import basegame.PlayerState;
import parser.ParserOutput;
import tipi.Dialogo;
import tipi.Inventario;
import tipi.Item;

public class ResponseObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";

        if (description.getPlayerState() == PlayerState.TALKING) {
            if (parserOutput.getCommand().getType() == CommandType.NULL_COMMAND) {
                Dialogo dialogo = description.getDialoghi().stream()
                        .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                        .findAny()
                        .orElse(null);
                if (dialogo != null) {
                    if (dialogo.valutaRisposta((String) parserOutput.getParams()) && !dialogo.getRisposta().isBlank()) {
                        description.setPlayerState(PlayerState.NORMAL);
                        msg = dialogo.getMessaggioRispCorretta();
                        switch (description.getCurrentCasella().getId()) {
                            case 903 -> {
                                Inventario inv = description.getInventario();
                                if (inv.contains("moneta")) {
                                    inv.remove("moneta");
                                    dialogo.changeDialogo();
                                } else {
                                    msg = "\"Ma non hai monete vigliacco! Mi hai mentito!\" (non hai monete d'oro, prova a tornare a casa)";
                                }
                            }

                            case 908 -> {
                                dialogo.changeDialogo();
                                description.getCurrentCasella().getNorth().setEnterable(true);
                            }

                            case 110 -> {
                                Inventario inv = description.getInventario();
                                if (inv.contains("moneta")) {
                                    inv.remove("moneta");
                                    dialogo.changeDialogo();
                                    msg += "\nHai speso 1 Moneta d'oro per ripare il tuo Liuto!";
                                } else {
                                    msg = "\"Non hai abbastanza monete ragazzo, sarà per la prossima volta\" (non hai abbastanza monete d'oro per riparare il Liuto, prova a tornare a casa)";
                                }
                            }

                            case 112 -> {
                                Inventario inv = description.getInventario();
                                Item item = description.getCurrentCasella().getOggetti()
                                        .stream()
                                        .filter(i -> i.getName().equalsIgnoreCase("coltello"))
                                        .findFirst()
                                        .orElse(null);
                                if (item == null) {
                                    msg += "\nHai già un coltello!! A cosa ti serve un altro coltello?!";
                                } else if (inv.contains("moneta")) {
                                    dialogo.changeDialogo();
                                    inv.remove("moneta");

                                    item.setVisible(true);
                                    description.getCurrentCasella().clearOggetto(item);
                                    inv.addOggetto(item, 1);
                                    msg += "\nHai speso 1 Moneta d'oro per un coltello! Il Coltello è stato aggiunto al tuo inventario";
                                } else {
                                    msg = "\"Non hai abbastanza soldi ragazzo, sarà per la prossima volta\" (non hai abbastanza monete d'oro per comprare il coltello, prova a tornare a casa forse le hai lasciate li)";
                                }
                            }

                        }
                        description.setLastCommand(parserOutput.getCommand());
                    } else {
                        msg = dialogo.getMessaggioRispErrata();
                    }
                }
            } else if (parserOutput.getCommand().getType() != CommandType.TALK_TO && parserOutput.getCommand().getType() != CommandType.SAVE){
                description.setPlayerState(null);
            }
        }

        return msg;
    }

}
