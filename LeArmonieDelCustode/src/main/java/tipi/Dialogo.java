package tipi;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import other.GestioneDB;

/**
 * Classe che rappresenta un dialogo nel gioco, contenente il testo del dialogo,
 * la risposta attesa, e i messaggi di feedback per risposte corrette ed errate.
 * 
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Dialogo implements Serializable {
    private final int idCasella;
    private String dialogo;
    private String risposta;
    private String messaggioRispCorretta;
    private String messaggioRispErrata;

    /**
     * Costruttore che inizializza un nuovo dialogo con l'ID della casella specificata.
     * 
     * @param idCasella ID della casella associata al dialogo
     */
    public Dialogo(int idCasella) {
        this.idCasella = idCasella;
        this.dialogo = "";
        this.risposta = "";
        this.messaggioRispCorretta = "";
        this.messaggioRispErrata = "";
    }

    /**
     * Imposta tutte le informazioni del dialogo.
     * 
     * @param dialogo Testo del dialogo
     * @param risposta Risposta corretta
     * @param messaggioRispCorretta Messaggio per risposta corretta
     * @param messaggioRispErrata Messaggio per risposta errata
     */
    public void setInfoDialogo(String dialogo, String risposta, String messaggioRispCorretta,
            String messaggioRispErrata) {
        this.dialogo = dialogo;
        this.risposta = risposta;
        this.messaggioRispCorretta = messaggioRispCorretta;
        this.messaggioRispErrata = messaggioRispErrata;
    }

    /**
     * Restituisce il testo del dialogo.
     * 
     * @return Il testo del dialogo
     */
    public String getDialogo() {
        return dialogo;
    }

    /**
     * Valuta se la risposta fornita è corretta, ignorando maiuscole/minuscole e spazi.
     * 
     * @param risposta Risposta da valutare
     * @return true se la risposta è corretta, false altrimenti
     */
    public boolean valutaRisposta(String risposta) {
        return this.risposta.toLowerCase().replaceAll(" ", "").equals(risposta.toLowerCase().replaceAll(" ", ""));
    }

    /**
     * Restituisce l'ID della casella associata al dialogo.
     * 
     * @return ID della casella
     */
    public int getIdCasella() {
        return idCasella;
    }

    /**
     * Restituisce il messaggio per la risposta corretta.
     * 
     * @return Messaggio per risposta corretta
     */
    public String getMessaggioRispCorretta() {
        return messaggioRispCorretta;
    }

    /**
     * Restituisce il messaggio per la risposta errata.
     * 
     * @return Messaggio per risposta errata
     */
    public String getMessaggioRispErrata() {
        return messaggioRispErrata;
    }

    /**
     * Imposta la risposta corretta.
     * 
     * @param risposta Nuova risposta corretta
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     * Restituisce la risposta corretta.
     * 
     * @return La risposta corretta
     */
    public String getRisposta() {
        return risposta;
    }

    /**
     * Imposta il testo del dialogo.
     * 
     * @param testo Nuovo testo del dialogo
     */
    public void setTestoDialogo(String testo) {
        dialogo = testo;
    }

    /**
     * Aggiorna il dialogo nel database.
     */
    public void changeDialogo() {
        try {
            GestioneDB.getInstance().changeDialogo(Dialogo.this);
        } catch (Exception e) {
            Logger.getLogger(Dialogo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}