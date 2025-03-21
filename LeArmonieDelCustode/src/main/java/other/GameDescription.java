package other;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mondo.Casella;
import parser.ParserOutput;
import tipi.Command;
import tipi.CommandType;
import tipi.Dialogo;
import tipi.Inventario;
import tipi.Item;

public abstract class GameDescription implements Serializable {

    private final List<Casella> Caselle = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();
    private List<Dialogo> dialoghi;
    private Command lastCommand = new Command(CommandType.NULL_COMMAND, null);
    private final Inventario inventario = new Inventario();
    private Chrono chrono = new Chrono();

    private Casella currentCasella;

    public List<Casella> getCaselle() {
        return Caselle;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Casella getCurrentCasella() {
        return currentCasella;
    }

    public List<Dialogo> getDialoghi() {
        return dialoghi;
    }

    public void setDialoghi(List<Dialogo> dialoghi) {
        this.dialoghi = dialoghi;
    }

    public void setCurrentCasella(Casella currentCasella) {
        this.currentCasella = currentCasella;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public Set<Item> getInventarioSet() {
        return inventario.getOggetti();
    }

    public Command getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(Command lastCommand) {
        this.lastCommand = lastCommand;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
    }

    public abstract String getIntro();

    public abstract void init() throws Exception;

    public abstract void nextMove(ParserOutput p, StampaTesto out);
}
