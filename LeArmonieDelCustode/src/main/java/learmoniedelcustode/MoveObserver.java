package learmoniedelcustode;

import javax.swing.ImageIcon;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import mondo.Casella;
import parser.ParserOutput;

public class MoveObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        Integer totalMoves = 0, maxMoves;
        CommandType cmdt = parserOutput.getCommand().getType();
        if (cmdt == CommandType.EAST || cmdt == CommandType.WEST || cmdt == CommandType.SOUTH
                || cmdt == CommandType.NORTH) {
            // System.out.println("MoveObserver direction: " + cmdt);
            Casella toMove;
            Object arg = parserOutput.getParams();

            if (arg == null) {
                maxMoves = 1;
            } else {
                try {
                    String argString = (String) arg;
                    maxMoves = Integer.valueOf(argString.replaceFirst("[x | X]", ""));
                } catch (NumberFormatException e) {
                    msg = "Il numero di caselle da muovere non è valido.";
                    return msg;
                }
            }

            while (totalMoves < maxMoves) {
                toMove = null;
                switch (cmdt) {
                    case EAST -> toMove = description.getCurrentCasella().getEast();
                    case WEST -> toMove = description.getCurrentCasella().getWest();
                    case SOUTH -> toMove = description.getCurrentCasella().getSouth();
                    case NORTH -> toMove = description.getCurrentCasella().getNorth();
                    default -> {
                    }
                }
                if (toMove == null && totalMoves == 0) {
                    // Controllo sulla zona per l'errore di direzione
                    String defaultDescrizione = Integer.toString(description.getCurrentCasella().getId());
                    msg = switch (defaultDescrizione.charAt(0)) {
                        case '1' -> "Ci sono le case degli abitanti di Arcadia, eviterei di entrarci.";
                        case '2' -> "La foresta è troppo fitta per andare oltre.";
                        case '3' -> "La pietra della caverna è troppo scivolosa per andare oltre.";
                        case '4' -> "Non ti conviene scavalcare il recinto del Cimitero.";
                        case '5' -> "Non puoi andare oltre alle mura del Tempio.";
                        case '9' -> "Gli alberi sono troppi per andare oltre.";
                        default -> "Non puoi andare in quella direzione.";
                    };
                    totalMoves = maxMoves;
                } else if (toMove == null || !toMove.isEnterable()) {
                    if (msg.length() == 0) {
                        msg = "Il passaggio in quella direzione al momento è bloccato, prova a fare altro.\n"
                                + description.getCurrentCasella().getDescrizione();
                    }
                    if (totalMoves != 0) {
                        msg += "\nSei riuscito a muoverti verso "
                                + cmdt.toString().toLowerCase()
                                + " di solo "
                                + totalMoves + " casella rispetto alle " + maxMoves + " indicate.";
                    }
                    totalMoves = maxMoves;
                } else {
                    totalMoves++;
                    description.setCurrentCasella(toMove);
                    msg = toMove.getDescrizione();
                }
            }

            int id = description.getCurrentCasella().getId();
            String imagePath = Integer.toString(id);
            switch (imagePath.charAt(0)) {
                case '1' -> {
                    if (id == 107)
                        imagePath = "resource/img/scena_drago_pixeled.png";
                    else if (id == 110)
                        imagePath = "resource/img/liuther_pixeled.png";
                    else if (id == 112)
                        imagePath = "resource/img/blacksmith_pixeled.png";
                    else
                        imagePath = "resource/img/Arcadia_kingdom_pixeled.png";
                }
                case '2' -> {
                    if (id == 209 && !description.getCurrentCasella().isUpdated())
                        imagePath = "resource/img/bardo_pixeled.png";
                    else
                        imagePath = "resource/img/forest_pixeled.png";
                }
                case '3' -> {
                    if (id == 329 && !description.getCurrentCasella().isUpdated())
                        imagePath = "resource/img/liuto_pixeled.png";
                    else
                        imagePath = "resource/img/caverna_pixeled.png";
                }
                case '4' -> {
                    if (id == 410 && !description.getCurrentCasella().isUpdated())
                        imagePath = "resource/img/scheletro_pixeled.png";
                    else
                        imagePath = "resource/img/cimitero_pixeled.png";
                }
                case '5' -> {
                    if (id == 529 && !description.getCurrentCasella().isUpdated())
                        imagePath = "resource/img/legendary_staff_pixeled.png";
                    else
                        imagePath = "resource/img/tempio_pixeled.png";
                }
                case '9' -> {
                    if (id == 903)
                        imagePath = "resource/img/mendicante_pixeled.png";
                    else if (id == 908)
                        imagePath = "resource/img/guardia_pixeled.png";
                    else
                        imagePath = "resource/img/pg_Luca_pixeled.png";
                }
                default -> imagePath = "resource/img/pg_Luca_pixeled.png";
            }
            parserOutput.getInterfacciaGioco().changeImageViewer(new ImageIcon(imagePath));
        }
        // System.out.println(description.getCurrentCasella().getId());
        return msg;
    }

}
