package learmoniedelcustode;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

public class TheEndObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.THE_END) {
            description.getChrono().stop();
            parserOutput.getInterfacciaGioco().endGame();
        }

        return msg;
    }

}
