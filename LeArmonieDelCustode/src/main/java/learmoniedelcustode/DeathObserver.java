package learmoniedelcustode;

import Grafica.InterfacciaMorte;
import Other.GameDescription;
import Other.GameObserver;
import Parser.ParserOutput;
import Tipi.CommandType;

public class DeathObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if(parserOutput.getCommand().getType() == CommandType.DEATH) {
            parserOutput.getInterfacciaGioco().getMusica().stopMusica();
            parserOutput.getInterfacciaGioco().getMusica().riproduciClip("resource\\other\\death_effect.wav");
            InterfacciaMorte morte = new InterfacciaMorte(parserOutput.getInterfacciaGioco());
            morte.setVisible(true);
            parserOutput.getInterfacciaGioco().dispose();
        }
        return msg;
    }

}
