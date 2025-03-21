/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package other;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe che gestisce la stampa di un testo su un'area di testo.
 *
 * @author kelvinsrcn
 * @author pellegrinoAl
 */
public class StampaTesto implements Runnable {

    private String[] paroleDaStampare;
    private int currentParola;
    private int ATTESA = 50;
    private final JTextArea textAreaOutput;
    private final JTextField textFieldInput;
    private final JButton skipButton;
    private Thread thread;
    private static StampaTesto instance = null;
    private boolean isFinal;

    private StampaTesto(JTextField textFieldInput, JTextArea textAreaOutput, JButton skipButton) {
        this.textAreaOutput = textAreaOutput;
        this.textFieldInput = textFieldInput;
        this.skipButton = skipButton;
        this.isFinal = false;
    }

    public static StampaTesto getInstance(JTextField textFieldInput, JTextArea textAreaOutput, JButton skipButton) {
        instance = new StampaTesto(textFieldInput, textAreaOutput, skipButton);
        return instance;
    }

    public static StampaTesto getInstance() {
        return instance;

    }

    public void stampa(String testo) {
        this.paroleDaStampare = testo.split(" ");
        if (thread != null && thread.isAlive()) {
            interrupt();
        }
        thread = new Thread(this);
        textFieldInput.setEnabled(false);
        thread.start();
    }

    public void stampaFinale(String testo) {
        isFinal = true;
        stampa(testo);
    }

    public boolean isAlive() {
        return thread != null && thread.isAlive();
    }

    public void interrupt() {
        thread.interrupt();
    }

    public void printRemaining() {
        for (; currentParola < paroleDaStampare.length; currentParola++) {
            if (paroleDaStampare[currentParola].equals("\\n"))
                textAreaOutput.append("\n");
            else
                textAreaOutput.append(paroleDaStampare[currentParola] + " ");
        }
    }

    public void setAttesa(int attesa) {
        ATTESA = attesa;
    }

    /**
     * Metodo che gestisce l'esecuzione del thread e la lettura della stringa da
     * stampare.
     */
    @Override
    public void run() {
        textFieldInput.setEnabled(false);
        skipButton.setEnabled(true);

        for (currentParola = 0; currentParola < paroleDaStampare.length; currentParola++) {
            try {
                Thread.sleep(ATTESA);
                if (paroleDaStampare[currentParola].equals("\\n"))
                    textAreaOutput.append("\n");
                else
                    textAreaOutput.append(paroleDaStampare[currentParola] + " ");

                textAreaOutput.setCaretPosition(textAreaOutput.getDocument().getLength());
            } catch (InterruptedException ex) {
                printRemaining();
            }
        }
        if (!isFinal) {
            textAreaOutput.append("\n\nCosa vuoi fare?\n ➤ ");
            textFieldInput.setEnabled(true);
            skipButton.setEnabled(false);
            textFieldInput.requestFocus();
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StampaTesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
