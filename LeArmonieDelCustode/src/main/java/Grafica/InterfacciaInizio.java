package grafica;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import classifica.Client;
import other.GestioneDB;
import other.Musica;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaInizio extends javax.swing.JFrame {
    private final Color BACKGROUND = new Color(237, 232, 208);
    private final Color TEXT = new Color(06, 06, 06);
    private final Color WHITE = new Color(250, 249, 246);
    private final Musica musica = new Musica();

    public InterfacciaInizio() throws SQLException {
        initComponents();
        GestioneDB.getInstance().connect();
    }

    private void initComponents() {
        musica.playMusic("resource/other/menu_soundtrack.wav");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                if (!musica.isPlaying())
                    musica.playMusic("resource/other/menu_soundtrack.wav");
            }
        });

        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                if (!musica.isPlaying())
                    musica.playMusic("resource/other/menu_soundtrack.wav");
            }

            @Override
            public void windowLostFocus(java.awt.event.WindowEvent evt) {

            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Client c = new Client();
                try {
                    c.end();
                    GestioneDB.getInstance().close();
                } catch (IOException | ClassNotFoundException ex) {
                    // Viene catturata questa eccezione quando si avvia solamente il maind di
                    // InterfacciaInizio
                    Logger.getLogger(InterfacciaInizio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException e) {
                    Logger.getLogger(InterfacciaInizio.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });

        background = new javax.swing.JLabel();
        background.setIcon(new ImageIcon(new ImageIcon("resource/img/main_menu_Luca_pixeled.png").getImage()
                .getScaledInstance(1100, 700, Image.SCALE_DEFAULT)));
        macroPanel = new javax.swing.JPanel();
        start = new javax.swing.JButton();
        load = new javax.swing.JButton();
        leaderboard = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        fileChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Le Armonie del Custode - Start Menu");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setResizable(false);
        getContentPane().add(background, java.awt.BorderLayout.CENTER);

        macroPanel.setBackground(BACKGROUND);

        start.setBackground(BACKGROUND);
        start.setForeground(TEXT);
        start.setText("Inizia Gioco");
        start.setPreferredSize(new java.awt.Dimension(140, 23));
        start.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/start_icon.png").getImage().getScaledInstance(20,
                20, Image.SCALE_DEFAULT)));
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                musica.stopMusica();
                startGame(evt);
            }
        });
        macroPanel.add(start);

        load.setBackground(BACKGROUND);
        load.setForeground(TEXT);
        load.setText("Carica Partita");
        load.setPreferredSize(new java.awt.Dimension(140, 23));
        load.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/load_icon.png").getImage().getScaledInstance(20,
                20, Image.SCALE_DEFAULT)));

        fileChooser.setDialogTitle("Caricamento Salvataggio");
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Files .dat", "dat"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        load.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                int returnVal = fileChooser.showOpenDialog(InterfacciaInizio.this);
                if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                    try {
                        java.io.File file = fileChooser.getSelectedFile();
                        InterfacciaGioco gioco = new InterfacciaGioco(InterfacciaInizio.this, file);
                        gioco.setVisible(true);
                        musica.stopMusica();
                        setVisible(false);
                    } catch (Exception e) {
                        musica.playMusic("resource/other/menu_soundtrack.wav");
                        JOptionPane.showMessageDialog(InterfacciaInizio.this, e.getMessage(), "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        macroPanel.add(load);

        leaderboard.setBackground(BACKGROUND);
        leaderboard.setForeground(TEXT);
        leaderboard.setText("classifica");
        leaderboard.setPreferredSize(new java.awt.Dimension(140, 23));
        leaderboard.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/leaderboard_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        leaderboard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InterfacciaClassifica classifica;
                try {
                    classifica = new InterfacciaClassifica();
                    classifica.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(InterfacciaInizio.this, e.getMessage(), "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        macroPanel.add(leaderboard);

        exit.setBackground(BACKGROUND);
        exit.setForeground(TEXT);
        exit.setText("Esci");
        exit.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/exit_icon.png").getImage().getScaledInstance(20,
                20, Image.SCALE_DEFAULT)));
        exit.setPreferredSize(new java.awt.Dimension(140, 23));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                musica.stopMusica();
                closeWindow(evt);
            }
        });
        macroPanel.add(exit);
        macroPanel.setBackground(WHITE);

        getContentPane().add(macroPanel, java.awt.BorderLayout.PAGE_END);

        getAccessibleContext().setAccessibleName("Le Armonie del Custode");

        pack();
        setLocationRelativeTo(null);
    }

    private void closeWindow(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    private void startGame(java.awt.event.MouseEvent evt) {
        InterfacciaGioco gioco;
        try {
            gioco = new InterfacciaGioco(this);
            gioco.setVisible(true);
            setVisible(false);
        } catch (Exception e) {
            musica.playMusic("resource/other/menu_soundtrack.wav");
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfacciaInizio.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new InterfacciaInizio().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private javax.swing.JLabel background;
    private javax.swing.JButton exit;
    private javax.swing.JButton load;
    private javax.swing.JPanel macroPanel;
    private javax.swing.JButton start;
    private javax.swing.JButton leaderboard;
    private javax.swing.JFileChooser fileChooser;
}
