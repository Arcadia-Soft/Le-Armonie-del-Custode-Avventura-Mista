package learmoniedelcustode;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import grafica.InterfacciaMorte;
import parser.ParserOutput;

public class DeathObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.DEATH) {
            parserOutput.getInterfacciaGioco().getMusica().stopMusica();
            parserOutput.getInterfacciaGioco().getMusica().riproduciClip("resource/other/death_effect.wav");
            InterfacciaMorte morte = new InterfacciaMorte(parserOutput.getInterfacciaGioco().getParentFrame());
            morte.setVisible(true);
            parserOutput.getInterfacciaGioco().dispose();
        }
        return msg;
    }

}
