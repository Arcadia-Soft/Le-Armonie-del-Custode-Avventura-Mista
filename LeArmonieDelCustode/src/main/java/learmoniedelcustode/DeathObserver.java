package learmoniedelcustode;

import grafica.InterfacciaMorte;
import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

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
