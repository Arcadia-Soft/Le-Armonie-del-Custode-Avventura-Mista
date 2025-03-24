package other;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.h2.tools.RunScript;

import mondo.Casella;
import tipi.Dialogo;
import tipi.Item;

/**
 * Classe che gestisce tutte le operazioni di database del gioco.
 * Implementa il pattern Singleton per garantire una singola istanza della connessione al database.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class GestioneDB {

    private static Connection conn;
    private Properties proprietaDB;
    private static GestioneDB instance = null;

    /**
     * Costruttore privato della classe GestioneDB.
     * Inizializza la connessione al database.
     * 
     * @throws SQLException se si verifica un errore durante la connessione al database
     */
    private GestioneDB() throws SQLException {
        connect();
    }

    /**
     * Restituisce l'istanza singleton della classe GestioneDB.
     * Se l'istanza non esiste, ne crea una nuova.
     * 
     * @return l'istanza singleton di GestioneDB
     * @throws SQLException se si verifica un errore durante la creazione dell'istanza
     */
    public static GestioneDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new GestioneDB();
        }
        return instance;
    }

    /**
     * Stabilisce una connessione al database H2.
     * Configura le proprietà di connessione con username e password.
     * 
     * @throws SQLException se si verifica un errore durante la connessione
     */
    public void connect() throws SQLException {
        proprietaDB = new Properties();
        proprietaDB.setProperty("user", "kevin");
        proprietaDB.setProperty("password", "prova");
        conn = DriverManager.getConnection("jdbc:h2:./database", proprietaDB);
    }

    /**
     * Esegue una funzione SQL con meccanismo di retry in caso di errori.
     * Gestisce automaticamente la riconnessione e la ricreazione del database se necessario.
     * 
     * @param function La funzione SQL da eseguire
     * @return Il risultato della funzione
     * @throws SQLException Se si verifica un errore persistente dopo tutti i tentativi
     */
    private <T> T executeWithRetry(SqlFunction<T> function) throws SQLException {
        int retryCount = 0, max_retries = 5;
        while (retryCount < max_retries) {
            try {
                return function.apply();
            } catch (SQLException e) {
                if (e.getSQLState().equals("42S04")) {
                    try {
                        crea();
                    } catch (FileNotFoundException e_file) {
                        throw new SQLException("File not found", e_file);
                    }
                } else if (e.getSQLState().equals("08003")) {
                    connect();
                } else {
                    throw e;
                }
                retryCount++;
            }
        }
        throw new SQLException("Operation failed after " + max_retries + " attempts");
    }

    /**
     * Crea la struttura del database eseguendo uno script SQL.
     * 
     * @throws SQLException se si verifica un errore durante l'esecuzione dello script
     * @throws FileNotFoundException se il file dello script SQL non viene trovato
     */
    public void crea() throws SQLException, FileNotFoundException {
        RunScript.execute(conn, new FileReader("src\\main\\java\\Other\\caselleDB.sql"));
    }

    /**
     * Carica la mappa di gioco dal database.
     * Crea e connette tutte le caselle con i loro oggetti.
     * 
     * @param caselle Lista dove verranno memorizzate tutte le caselle caricate
     * @return La casella iniziale (ID 901)
     * @throws SQLException se si verifica un errore durante il caricamento
     */
    public Casella loadMappa(final List<Casella> caselle) throws SQLException {
        return executeWithRetry(() -> {
            Casella start = null;
            String query = "SELECT * FROM Casella";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Casella casella = creaCasellaDaResultSet(rs);
                    List<Item> items = getItems(casella.getId());
                    for (Item item : items) {
                        casella.addOggetto(item, 1);
                    }
                    caselle.add(casella);
                    if (casella.getId() == 901) {
                        start = casella;
                    }
                }
            }
            connectCaselle(caselle);

            return start;
        });
    }

    /**
     * Crea un oggetto Casella dai dati del ResultSet.
     * 
     * @param rs ResultSet contenente i dati della casella
     * @return Nuova istanza di Casella
     * @throws SQLException se si verifica un errore durante la lettura dei dati
     */
    private Casella creaCasellaDaResultSet(ResultSet rs) throws SQLException {
        Casella casella = new Casella(rs.getInt(1));
        casella.setNome(rs.getString(2));
        casella.setDescrizioneIniziale(rs.getString("descrizioneIniziale"));
        casella.setDescrizioneAggiuntiva(rs.getString("descrizioneAggiuntiva"));
        casella.setDescrizioneAggiornata(rs.getString("descrizioneAggiornata"));
        casella.setEnterable(rs.getBoolean("enterable"));
        return casella;
    }

    /**
     * Stabilisce le connessioni tra le caselle secondo le direzioni specificate nel database.
     * 
     * @param caselle Lista delle caselle da connettere
     * @throws SQLException se si verifica un errore durante la lettura delle connessioni
     */
    private void connectCaselle(List<Casella> caselle) throws SQLException {
        executeWithRetry(() -> {
            Map<Integer, Casella> MapCaselle = new HashMap<>();
            for (Casella c : caselle) {
                MapCaselle.put(c.getId(), c);
            }

            String query = "SELECT * FROM ConnessioniCaselle";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Casella c1 = MapCaselle.get(rs.getInt("casella_id1"));
                    Casella c2 = MapCaselle.get(rs.getInt("casella_id2"));
                    switch (rs.getString("direzione")) {
                        case "n":
                            c1.setNorth(c2);
                            c2.setSouth(c1);
                            break;
                        case "s":
                            c1.setSouth(c2);
                            c2.setNorth(c1);
                            break;
                        case "e":
                            c1.setEast(c2);
                            c2.setWest(c1);
                            break;
                        case "w":
                            c1.setWest(c2);
                            c2.setEast(c1);
                            break;
                        default:
                            break;
                    }
                }
            }

            return null;
        });
    }

    /**
     * Carica i dialoghi dal database in base alla priorità specificata.
     * 
     * @param priority true per dialoghi prioritari, false per quelli non prioritari
     * @return Lista dei dialoghi caricati
     * @throws SQLException se si verifica un errore durante il caricamento
     */
    public List<Dialogo> loadDialoghi(boolean priority) throws SQLException {
        return executeWithRetry(() -> {
            List<Dialogo> dialoghi = new ArrayList<>();
            String query = "SELECT * FROM Dialoghi WHERE priorita = " + priority + ";";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Dialogo d = new Dialogo(rs.getInt(1));
                    d.setInfoDialogo(rs.getString(3), rs.getString(6), rs.getString(4), rs.getString(5));
                    dialoghi.add(d);
                }
            }
            return dialoghi;
        });
    }

    /**
     * Aggiorna le informazioni di un dialogo con quelle presenti nel database.
     * 
     * @param dialogo Il dialogo da aggiornare
     * @throws SQLException se si verifica un errore durante l'aggiornamento
     */
    public void changeDialogo(Dialogo dialogo) throws SQLException {
        executeWithRetry(() -> {
            String query = "SELECT * FROM Dialoghi WHERE priorita = " + true + " AND id = " + dialogo.getIdCasella()
                    + ";";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    dialogo.setInfoDialogo(rs.getString(3), rs.getString(6), rs.getString(4), rs.getString(5));
                }
            }
            return null;
        });
    }

    /**
     * Recupera tutti gli oggetti associati a una casella specifica.
     * 
     * @param id ID della casella
     * @return Lista degli oggetti presenti nella casella
     * @throws SQLException se si verifica un errore durante il recupero
     */
    private List<Item> getItems(int id) throws SQLException {
        return executeWithRetry(() -> {
            List<Item> items = new ArrayList<>();
            String query = "SELECT * FROM Casella_Oggetto co JOIN Oggetto o ON oggetto_id = id WHERE casella_id = "
                    + id + ";";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Item item = creaItemDaResultSet(rs);
                    Integer quantity = rs.getInt("quantita");
                    for (int i = 0; i < quantity; i++)
                        items.add(item);
                }
                rs.close();
            }
            return items;
        });
    }

    /**
     * Crea un oggetto Item dai dati del ResultSet.
     * 
     * @param rs ResultSet contenente i dati dell'oggetto
     * @return Nuovo oggetto Item
     * @throws SQLException se si verifica un errore durante la lettura dei dati
     */
    private Item creaItemDaResultSet(ResultSet rs) throws SQLException {
        Item item = new Item(rs.getString("nome"));
        item.setDescription(rs.getString("descrizione"));
        item.setAlias(getAlias(rs.getInt("id")));
        item.setPickable(rs.getBoolean("pickable"));
        item.setVisible(rs.getBoolean("visible"));
        return item;
    }

    /**
     * Recupera tutti gli alias associati a un oggetto.
     * 
     * @param id ID dell'oggetto
     * @return Set di stringhe contenente gli alias dell'oggetto
     * @throws SQLException se si verifica un errore durante il recupero
     */
    private Set<String> getAlias(int id) throws SQLException {
        return executeWithRetry(() -> {
            String getAlias = "SELECT * FROM Alias WHERE id = " + id + ";";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(getAlias);
            Set<String> alias = new HashSet<>();
            while (rs.next()) {
                alias.add(rs.getString("alias").toLowerCase());
            }
            rs.close();
            return alias;
        });
    }

    /**
     * Chiude la connessione al database.
     * 
     * @throws SQLException se si verifica un errore durante la chiusura
     */
    public void close() throws SQLException {
        conn.close();
    }

    @FunctionalInterface
    /**
     * Interfaccia funzionale per operazioni SQL.
     * Permette di eseguire operazioni sul database in modo generico.
     *
     * @param <T> il tipo del valore restituito dall'operazione
     */
    private interface SqlFunction<T> {
        /**
         * Esegue l'operazione SQL e restituisce un risultato.
         *
         * @return il risultato dell'operazione SQL
         * @throws SQLException se si verifica un errore durante l'esecuzione
         */
        T apply() throws SQLException;
    }
}
