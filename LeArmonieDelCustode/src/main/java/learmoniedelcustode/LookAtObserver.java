package learmoniedelcustode;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

public class LookAtObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.LOOK_AT) {
            msg = description.getCurrentCasella().getDescrizione();
        }
        return msg;
    }

}
