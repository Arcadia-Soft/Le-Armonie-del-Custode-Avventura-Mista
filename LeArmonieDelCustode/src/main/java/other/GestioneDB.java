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

public class GestioneDB {

    private static Connection conn;
    private Properties proprietaDB;
    private static GestioneDB instance = null;

    /**
     * Costruttore della classe GestioneDB
     * 
     * @throws SQLException
     */
    private GestioneDB() throws SQLException {
        connect();
    }

    public static GestioneDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new GestioneDB();
        }
        return instance;
    }

    /**
     * Metodo per connettersi al database
     * 
     * @throws SQLException
     */
    public void connect() throws SQLException {
        proprietaDB = new Properties();
        proprietaDB.setProperty("user", "kevin");
        proprietaDB.setProperty("password", "prova");
        conn = DriverManager.getConnection("jdbc:h2:./database", proprietaDB);
    }

    /**
     * Esegue una funzione SQL con tentativi di ripetizione in caso di errore.
     * 
     * @param function La funzione SQL da eseguire
     * @return Il risultato della funzione
     * @throws SQLException Se si verifica un errore durante l'esecuzione della
     *                      funzione dopo il numero massimo di tentativi
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
     * Metodo per creare il database
     * 
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void crea() throws SQLException, FileNotFoundException {
        RunScript.execute(conn, new FileReader("src\\main\\java\\Other\\caselleDB.sql"));
    }

    /**
     * Metodo per caricare la mappa
     * 
     * @return Casella
     * @throws FileNotFoundException
     * @throws SQLException
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

    private Casella creaCasellaDaResultSet(ResultSet rs) throws SQLException {
        Casella casella = new Casella(rs.getInt(1));
        casella.setNome(rs.getString(2));
        casella.setDescrizioneIniziale(rs.getString("descrizioneIniziale"));
        casella.setDescrizioneAggiuntiva(rs.getString("descrizioneAggiuntiva"));
        casella.setDescrizioneAggiornata(rs.getString("descrizioneAggiornata"));
        casella.setEnterable(rs.getBoolean("enterable"));
        return casella;
    }

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

    private Item creaItemDaResultSet(ResultSet rs) throws SQLException {
        Item item = new Item(rs.getString("nome"));
        item.setDescription(rs.getString("descrizione"));
        item.setAlias(getAlias(rs.getInt("id")));
        item.setPickable(rs.getBoolean("pickable"));
        item.setVisible(rs.getBoolean("visible"));
        return item;
    }

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

    public void close() throws SQLException {
        conn.close();
    }

    @FunctionalInterface
    /**
     * Interfaccia funzionale che rappresenta un'operazione SQL che restituisce
     * un valore di tipo T.
     *
     * @param <T> il tipo del valore restituito
     */
    private interface SqlFunction<T> {
        /**
         * Applica l'operazione SQL e restituisce il risultato.
         *
         * @return il risultato dell'operazione SQL
         * @throws SQLException se si verifica un errore durante l'operazione SQL
         */
        T apply() throws SQLException;
    }
}
