package learmoniedelcustode;

import javax.swing.ImageIcon;

import mondo.Casella;
import other.GameDescription;
import other.GameObserver;
import parser.ParserOutput;
import tipi.CommandType;

public class MoveObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        Integer totalMoves = 0, maxMoves = 1;
        CommandType cmdt = parserOutput.getCommand().getType();
        if (cmdt == CommandType.EAST || cmdt == CommandType.WEST || cmdt == CommandType.SOUTH
                || cmdt == CommandType.NORTH) {
            Casella toMove;
            if (parserOutput.getInputString().split("\\s+").length == 2) {
                try {
                    maxMoves = Integer
                            .valueOf(parserOutput.getInputString().split("\\s+")[1].replaceFirst("[x | X]", ""));
                    System.out.println(maxMoves);
                } catch (NumberFormatException e) {
                    msg = "Il numero di caselle da muovere non è valido.";
                    return msg;
                }
            }
            if (maxMoves == 0) {
                msg = description.getCurrentCasella().getDescrizione();
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
                                + (parserOutput.getInputString().split(" ")[0]).toUpperCase()
                                + " di solo "
                                + totalMoves + " casella rispetto alle " + maxMoves + " indicate.";
                    }
                    totalMoves = maxMoves;
                } else {
                    totalMoves++;
                    description.setCurrentCasella(toMove);
                    msg = toMove.getDescrizione();
                    String imagePath;
                    switch (toMove.getId()) {
                        case 901, 902, 904, 905, 906, 907, 909, 911, 912, 913, 914, 916, 918, 919, 920, 922, 923 ->
                            imagePath = "resource/img/pg_Luca_pixeled.png";

                        case 903 ->
                            imagePath = "resource/img/mendicante_pixeled.png";

                        case 908 ->
                            imagePath = "resource/img/guardia_pixeled.png";

                        case 101, 102, 103, 104, 105, 106, 108, 109, 111 ->
                            imagePath = "resource/img/Arcadia_kingdom_pixeled.png";

                        case 107 ->
                            imagePath = "resource/img/scena_drago_pixeled.png";

                        case 110 ->
                            imagePath = "resource/img/liuther_pixeled.png";

                        case 112 ->
                            imagePath = "resource/img/blacksmith_pixeled.png";

                        case 201, 202, 203, 204, 205, 206, 207, 208 ->
                            imagePath = "resource/img/forest_pixeled.png";

                        case 209 -> {
                            if (!description.getCurrentCasella().isUpdated()) {
                                imagePath = "resource/img/bardo_pixeled.png";
                            } else {
                                imagePath = "resource/img/forest_pixeled.png";
                            }
                        }

                        case 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318,
                                319, 320, 321, 322, 323, 324, 325, 326, 327, 328 ->
                            imagePath = "resource/img/caverna_pixeled.png";

                        case 329 ->
                            imagePath = "resource/img/liuto_pixeled.png";

                        case 401, 402, 403, 404, 405, 406, 407, 408, 409, 411 ->
                            imagePath = "resource/img/cimitero_pixeled.png";

                        case 410 ->
                            imagePath = "resource/img/scheletro_pixeled.png";

                        case 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518,
                                519, 520, 521, 522, 523, 524, 525, 526, 527, 528 ->
                            imagePath = "resource/img/tempio_pixeled.png";

                        case 529 ->
                            imagePath = "resource/img/legendary_staff_pixeled.png";

                        default ->
                            imagePath = "resource/img/pg_Luca_pixeled.png";
                    }
                    parserOutput.getInterfacciaGioco().changeImageViewer(new ImageIcon(imagePath));
                }
            }

        }
        System.out.println(description.getCurrentCasella().getId());
        return msg;
    }

}
