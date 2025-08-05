package learmoniedelcustode;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import parser.ParserOutput;

public class DanceObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.DANCE) {
            Object arg = parserOutput.getParams();
            if (arg == null || !(arg instanceof String)) {
                msg = "Sisi balla pure, ma sii più specifico... (usa il comando 'Balla' seguito dai passi di danza!)";
            } else{
                String danceMoves = (String) arg;
                if (description.getCurrentCasella().getId() == 326) {
                    if (danceMoves.matches("^(nnsseo|aaiids|nnssew)$")) {
                        if (!description.getCurrentCasella().getNorth().isEnterable()) {
                            description.getCurrentCasella().getNorth().setEnterable(true);
                            description.getCurrentCasella().setUpdated(true);
                            msg = "Hai ballato bene, la porta si è aperta!";
                        } else {
                            msg = "Hai già aperto la porta, non c'è bisogno di ballare di nuovo!";
                        }
                    } else {
                        if (description.getCurrentCasella().getNorth().isEnterable()) {
                            msg = "La porta è già aperta, non c'è bisogno di ballare un'altra danza strampalata!";
                        } else {
                            msg = "Non sembra si voglia aprire con questa danza strampalata, la porta è ancora chiusa...";
                        }
                    }
                } else if (description.getCurrentCasella().getId() >= 401
                        && description.getCurrentCasella().getId() <= 411) {
                    msg = "Non è rispettoso ballare in mezzo ai morti, non credi? Sono sconvolto...";
                } else if (description.getCurrentCasella().getId() >= 101
                        && description.getCurrentCasella().getId() <= 112) {
                    msg = "Sei diventato lo scemo del villaggio...";
                } else {
                    msg = "Che strana danza, ma finchè ti diverti...";
                }
            }
        }
        return msg;

    }

}
