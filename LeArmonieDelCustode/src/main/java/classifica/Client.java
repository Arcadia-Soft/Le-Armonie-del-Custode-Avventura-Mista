package classifica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import eccezioni.GetClassificaException;
import eccezioni.SendRecordException;

/**
 * Classe Client che gestisce la comunicazione con il server per l'invio e la ricezione
 * dei dati relativi alla classifica di gioco.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private final int serverPort = 6666;

    /**
     * Invia un record al server per aggiungerlo alla classifica.
     * 
     * @param record Il record da inviare al server
     * @throws Exception Se si verifica un errore durante l'invio del record
     */
    public void sendRecord(Record record) throws Exception {
        try (Socket socket = new Socket(SERVER_ADDRESS, serverPort);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("ADD_RECORD");
            oos.writeObject(record);
            String response = (String) ois.readObject();
            System.out.println("Server risposta: " + response);

        } catch (IOException | ClassNotFoundException e) {
            throw new SendRecordException();
        }
    }

    /**
     * Richiede la classifica completa dal server.
     * 
     * @return La classifica ricevuta dal server
     * @throws Exception Se si verifica un errore durante la richiesta della classifica
     */
    public Classifica requestClassifica() throws Exception {
        Classifica recordTracker = new Classifica();
        try (Socket socket = new Socket(SERVER_ADDRESS, serverPort);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("GET_CLASSIFICA");
            recordTracker = (Classifica) ois.readObject();
            System.out.println("Classifica ricevuta: " + recordTracker);
            if (recordTracker != null)
                System.out.println("Numero di record in classifica: " + recordTracker.getRecords().size());

        } catch (IOException | ClassNotFoundException e) {
            throw new GetClassificaException();
        }
        return recordTracker;
    }

    /**
     * Invia un comando di terminazione al server.
     * 
     * @throws IOException Se si verifica un errore di I/O durante la comunicazione
     * @throws ClassNotFoundException Se la classe dell'oggetto ricevuto non è trovata
     */
    public void end() throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(SERVER_ADDRESS, serverPort);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            oos.writeObject("END");
            System.out.println(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }
    }
}
