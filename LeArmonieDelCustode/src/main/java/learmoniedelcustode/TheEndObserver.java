package learmoniedelcustode;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import parser.ParserOutput;

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
