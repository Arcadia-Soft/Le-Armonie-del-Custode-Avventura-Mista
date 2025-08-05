package learmoniedelcustode;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import parser.ParserOutput;

public class PrayObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.PRAY) {
            msg = "Aiutati che Dio ti aiuterà! Forse... \n";
        }
        return msg;
    }

}
