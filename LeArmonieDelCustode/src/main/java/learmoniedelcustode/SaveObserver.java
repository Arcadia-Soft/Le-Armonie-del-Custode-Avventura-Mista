package learmoniedelcustode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

public class SaveObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.SAVE) {
            Object arg = parserOutput.getParams();
            if (arg == null) {
                msg = "Ehi, mi serve un nome per il file! Non posso mica salvare nel vuoto cosmico!";
                return msg;
            }
            String nameFile = (String) arg;
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(System.getProperty("user.dir") + File.separator + "resource"
                                + File.separator + "save" + File.separator + nameFile + ".dat"));
                description.getChrono().stop();
                out.writeObject(description);
                description.getChrono().startAgain(description.getChrono().getElapsedTime());
                out.close();
                msg = "Salvataggio effettuato in " + System.getProperty("user.dir") + File.separator + "resource"
                        + File.separator + "save" + File.separator + nameFile + ".dat";
            } catch (IOException e) {
                msg = "[ERRORE] Salvataggio del gioco non riuscito!";
            }
        }
        return msg;
    }
}
