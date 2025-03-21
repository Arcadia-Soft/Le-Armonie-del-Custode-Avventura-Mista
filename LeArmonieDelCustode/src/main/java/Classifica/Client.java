package classifica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import eccezioni.GetClassificaException;
import eccezioni.SendRecordException;

/**
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private final int serverPort = 6666;

    /**
     * 
     * @param record
     * @throws Exception
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
     * 
     * @return
     * @throws Exception
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
     * 
     * @throws IOException
     * @throws ClassNotFoundException
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
