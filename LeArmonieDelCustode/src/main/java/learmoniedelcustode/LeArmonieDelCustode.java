package learmoniedelcustode;

import java.util.ArrayList;
import java.util.List;

import basegame.Command;
import basegame.CommandType;
import basegame.GameDescription;
import basegame.GameObservable;
import basegame.GameObserver;
import basegame.PlayerState;
import mondo.Casella;
import other.GestioneDB;
import other.StampaTesto;
import parser.ParserOutput;

/**
 * Classe principale del gioco "Le Armonie Del Custode" che gestisce lo stato del gioco, i comandi e le interazioni con gli observer.
 * 
 * Implementa l'inizializzazione del gioco, la gestione dei comandi e il pattern observer per gli eventi di gioco.
 * Estende GameDescription e implementa GameObservable per fornire le meccaniche di base del gioco.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class LeArmonieDelCustode extends GameDescription implements GameObservable {
    private transient List<GameObserver> observer;
    private transient ParserOutput parserOutput;
    private transient List<String> messages;
    private Casella currentCasella;

    @Override
    public void init() throws Exception {
        setPlayerState(null);
        // Aggiunta comandi
        Command command = new Command(CommandType.DEATH, "muori");
        getCommands().add(command);
        command = new Command(CommandType.HELP, "help");
        command.setAlias(new String[] { "aiuto", "?" });
        getCommands().add(command);
        command = new Command(CommandType.LOOK_AT, "osserva");
        command.setAlias(new String[] { "guarda", "vedi" });
        getCommands().add(command);
        command = new Command(CommandType.EAST, "est");
        command.setAlias(new String[] { "e", "east" });
        getCommands().add(command);
        command = new Command(CommandType.WEST, "ovest");
        command.setAlias(new String[] { "o", "west", "w" });
        getCommands().add(command);
        command = new Command(CommandType.NORTH, "nord");
        command.setAlias(new String[] { "n", "north" });
        getCommands().add(command);
        command = new Command(CommandType.SOUTH, "sud");
        command.setAlias(new String[] { "s", "south" });
        getCommands().add(command);
        command = new Command(CommandType.PICK_UP, "prendi");
        command.setAlias(new String[] { "raccogli", "prendere" });
        getCommands().add(command);
        command = new Command(CommandType.PRAY, "prega");
        getCommands().add(command);
        command = new Command(CommandType.TALK_TO, "parla");
        getCommands().add(command);
        command = new Command(CommandType.USE, "usa");
        command.setAlias(new String[] { "utilizza" });
        getCommands().add(command);
        command = new Command(CommandType.SAVE, "salva");
        getCommands().add(command);
        command = new Command(CommandType.DANCE, "balla");
        command.setAlias(new String[] { "danza" });
        getCommands().add(command);

        // load caselle gioco
        GestioneDB db = GestioneDB.getInstance();
        currentCasella = db.loadMappa(getCaselle());
        setCurrentCasella(currentCasella);
        setDialoghi(db.loadDialoghi(false));

        attachObeservers();
    }

    public void attachObeservers() {
        observer = new ArrayList<>();
        messages = new ArrayList<>();
        // aggiunta observer
        attach(new DeathObserver());
        attach(new HelpObserver());
        attach(new LookAtObserver());
        attach(new MoveObserver());
        attach(new PickUpObserver());
        attach(new PrayObserver());
        attach(new TalkToObserver());
        attach(new UseObserver());
        attach(new DanceObserver());
        attach(new SaveObserver());
        attach(new TheEndObserver());
        attach(new ResponseObserver());
    }

    @Override
    public void nextMove(ParserOutput p, StampaTesto out) {
        if (p == null || getPlayerState() != PlayerState.TALKING && p.getCommand().getType() == CommandType.NULL_COMMAND) {
            out.stampa("Non ho capito cosa intendi...");
        } else {
            parserOutput = p;
            messages.clear();
            notifyObservers();
            if (!messages.isEmpty()) {
                for (String s : messages) {
                    if (s.length() > 0) {
                        out.stampa(s);
                    }
                }
            } else {
                out.stampa("Non ho capito cosa intendi...");
            }
            setLastCommand(p.getCommand());
        }
    }

    @Override
    public void attach(GameObserver o) {
        if (!observer.contains(o)) {
            observer.add(o);
        }
    }

    @Override
    public void detach(GameObserver o) {
        if (observer.contains(o)) {
            observer.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (GameObserver o : observer) {
            String msg = o.update(this, parserOutput);
            if (msg.length() != 0)
                messages.add(msg);
        }
    }

    @Override
    public final String getIntro() {
        return getCurrentCasella().getDescrizione();
    }
}
