package learmoniedelcustode;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

public class DanceObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.DANCE) {
            String command = parserOutput.getInputString().split(" ")[0] + "\\s+";
            String[] inputParsed = parserOutput.getInputString().split(command);
            if (inputParsed.length == 2) {
                String danceMoves = inputParsed[1].toLowerCase().replaceAll("\\s", "");
                if (description.getCurrentCasella().getId() == 326) {
                    if (!description.getCurrentCasella().getNorth().isEnterable() && danceMoves.equals("nnsseo")
                            || danceMoves.equals("aaiids") || danceMoves.equals("nnssew")) {
                        description.getCurrentCasella().getNorth().setEnterable(true);
                        description.getCurrentCasella().setUpdated(true);
                        msg = "Hai ballato bene, la porta è aperta!";
                    } else if (!description.getCurrentCasella().getNorth().isEnterable() && !danceMoves.equals("nnsseo")
                            && !danceMoves.equals("aaiids") && !danceMoves.equals("nnssew")) {
                        msg = "Non sembra si voglia aprire con questa danza strampalata, la porta è ancora chiusa...";
                    } else if (description.getCurrentCasella().getNorth().isEnterable()) {
                        msg = "Hai già aperto la porta, non c'è bisogno di ballare di nuovo!";
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
            } else {
                msg = "Sisi balla pure, ma sii più specifico... (usa il comando 'Balla' seguito dai passi di danza!)";
            }
        }
        return msg;

    }

}
