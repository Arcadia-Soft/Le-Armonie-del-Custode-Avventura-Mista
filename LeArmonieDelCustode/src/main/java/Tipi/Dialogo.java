package tipi;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import other.GestioneDB;

/**
 * Classe Dialogo
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

    public Dialogo(int idCasella) {
        this.idCasella = idCasella;
        this.dialogo = "";
        this.risposta = "";
        this.messaggioRispCorretta = "";
        this.messaggioRispErrata = "";
    }

    public void setInfoDialogo(String dialogo, String risposta, String messaggioRispCorretta,
            String messaggioRispErrata) {
        this.dialogo = dialogo;
        this.risposta = risposta;
        this.messaggioRispCorretta = messaggioRispCorretta;
        this.messaggioRispErrata = messaggioRispErrata;
    }

    public String getDialogo() {
        return dialogo;
    }

    public boolean valutaRisposta(String risposta) {
        return this.risposta.toLowerCase().replaceAll(" ", "").equals(risposta.toLowerCase().replaceAll(" ", ""));
    }

    public int getIdCasella() {
        return idCasella;
    }

    public String getMessaggioRispCorretta() {
        return messaggioRispCorretta;
    }

    public String getMessaggioRispErrata() {
        return messaggioRispErrata;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setTestoDialogo(String testo) {
        dialogo = testo;
    }

    public void changeDialogo() {
        try {
            GestioneDB.getInstance().changeDialogo(Dialogo.this);
        } catch (Exception e) {
            Logger.getLogger(Dialogo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
