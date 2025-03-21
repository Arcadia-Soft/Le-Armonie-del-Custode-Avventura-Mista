package learmoniedelcustode;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

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
