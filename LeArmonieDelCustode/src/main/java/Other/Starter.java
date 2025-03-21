package other;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import classifica.Server;
import grafica.InterfacciaInizio;

public class Starter {
    public static void main(String[] args) {
        try {
            InterfacciaInizio interfacciaInizio = new InterfacciaInizio();
            interfacciaInizio.setVisible(true);
            Server server = new Server();
            boolean running = false;
            for (int i = 0; i < 5 && !running; i++) {
                try {
                    server.start();
                    running = true;
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e1) {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, e1);
        }

    }
}
