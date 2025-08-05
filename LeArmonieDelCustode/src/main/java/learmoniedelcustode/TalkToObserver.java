package learmoniedelcustode;

import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObserver;
import basegame.PlayerState;
import parser.ParserOutput;
import rest.QuizAPI;
import tipi.Dialogo;

public class TalkToObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.TALK_TO
                && parserOutput.getParams() != null) {
            msg = "Per parlare con qualcuno non devi aggiungere nulla alla parola 'Parla'! Formato: Parla (avvia dialogo) e successivamente Risposta.";
        } else if (parserOutput.getCommand().getType() == CommandType.TALK_TO) {
            description.setPlayerState(PlayerState.TALKING);
            Dialogo dialogo = description.getDialoghi().stream()
                    .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                    .findAny()
                    .orElse(null);
            if (dialogo != null) {
                if (description.getCurrentCasella().getId() == 410) {
                    dialogo = QuizAPI.getQuiz(dialogo);
                }
                msg = dialogo.getDialogo();
            } else {
                msg = "Il maniconimio è chiuso, basta parlare da solo...";
            }
        }
        return msg;
    }

}
