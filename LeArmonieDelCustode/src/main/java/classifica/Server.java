package classifica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import other.Starter;

/**
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Server {

    private static final String FILE_NAME = System.getProperty("user.dir") + "/resource/classifica/classifica.dat";
    private final Classifica classifica;
    private final ServerSocket serverSocket;
    private final int port = 6666;

    /**
     * 
     * @throws IOException
     */
    public Server() throws IOException {
        classifica = loadClassifica();
        serverSocket = new ServerSocket(port);
    }

    /**
     * 
     * @return
     */
    private Classifica loadClassifica() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Classifica) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Classifica();
        }
    }

    /**
     * 
     * @throws Exception
     */
    private void saveClassifica() throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(classifica);
        } catch (IOException e) {
            File f = new File(FILE_NAME);
            if (f.createNewFile())
                saveClassifica();
            else
                throw new Exception("Impossibile salvare la classifica");
        }
    }

    /**
     * 
     * @throws Exception
     */
    public void start() throws Exception {
        System.out.println("Server in ascolto...");
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
                String command = (String) ois.readObject();
                switch (command) {
                    case "ADD_RECORD" -> {
                        Record record = (Record) ois.readObject();
                        classifica.addRecord(record);
                        saveClassifica();
                        oos.writeObject("Record aggiunto: " + record);
                    }
                    case "GET_CLASSIFICA" -> oos.writeObject(loadClassifica());
                    case "END" -> {
                        oos.writeObject("Chiusura server...");
                        break;
                    }
                    default -> {
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
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
        }
    }
}
