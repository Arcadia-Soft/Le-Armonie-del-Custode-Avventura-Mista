package learmoniedelcustode;

import javax.swing.ImageIcon;

import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

public class HelpObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.HELP) {
            String imagePath = "resource/img/help_pixeled.png";
            msg = """
                    Comandi:
                        - Aiuto | Help: mostra questo script;
                        - Osserva: mostra una descrizione del luogo in cui ti trovi;
                        - Prendi <oggetto>: prende un oggetto descritto nella stanza;
                        - Usa <oggetto>: utilizza un oggetto in una stanza;
                        - Parla: parla con l'unico personaggio presente nella stanza, se presente (verrà chiesto successivamente un input per la risposta);
                        - Nord o N o North: ti muovi verso Nord;
                        - Sud o S o South: ti muovi verso Sud;
                        - Est o E o East: ti muovi verso Est;
                        - Ovest o O o West o W: ti muovi verso Ovest;
                        - Balla: balla, è divertente;
                        - Muori: scoprilo :);
                        - Prega: non credo che ti possa aiutare;
                        - Salva <nomefile>: salva i dati attuali su un file (specificare il nome del file, l'estensione verrà aggiunta automaticamente);
                    """;
            parserOutput.getInterfacciaGioco().changeImageViewer(new ImageIcon(imagePath));
        }

        return msg;
    }

}
