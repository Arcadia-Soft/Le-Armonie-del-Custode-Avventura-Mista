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
            String command = parserOutput.getInputString().split(" ")[0] + "\\s+";
            String[] inputParsed = parserOutput.getInputString().split(command);
            if (inputParsed.length == 2) {
                String nameFile = inputParsed[1];
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
        }
        return msg;
    }
}
