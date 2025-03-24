/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.text.DefaultCaret;

import eccezioni.GameFileException;
import eccezioni.GameNotAvailableException;
import other.Chrono;
import other.GameDescription;
import other.Musica;
import other.StampaTesto;
import other.Utils;
import parser.Parser;
import parser.ParserOutput;
import learmoniedelcustode.LeArmonieDelCustode;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaGioco extends javax.swing.JFrame {
    private final Font FONT = new Font("Serif", Font.PLAIN, 20);
    private final String LUCAPG = "resource/img/inizio_Luca_pixeled.png";

    private final Color BACKGROUND_BEIGE = new Color(237, 232, 208);
    private final Color BACKGROUND_BLACK = new Color(54, 69, 79);
    private final Color TEXT = new Color(06, 06, 06);
    private final Color WHITE = new Color(250, 249, 246);
    private final Color RED = new Color(238, 75, 43);
    private final Color GREEN = new Color(9, 121, 105);

    Musica music = new Musica();

    private Parser parser = null;

    private GameDescription game;

    private final StampaTesto stampa;

    private Chrono chrono = new Chrono();

    private final JFrame parentFrame;

    public InterfacciaGioco(JFrame parentFrame) throws Exception {
        initComponents();
        this.parentFrame = parentFrame;
        stampa = StampaTesto.getInstance(textBox, textArea, skipButton);
        mainComponents(false, null);

        // DEBUG per finire il gioco
        // game.getInventario().addOggetto(new tipi.Item("liuto leggendario"), 1);
        // game.getInventario().addOggetto(new tipi.Item("pentagramma armonico"), 1);
        // game.getInventario().addOggetto(new tipi.Item("chiave del tempio"), 1);
        // game.getCaselle().stream()
        // .filter(c -> c.getId() == 106 || c.getId() == 107)
        // .forEach(c -> {
        //     if (c.getId() == 106)
        //         c.setUpdated(true);
        //     else if (c.getId() == 107)
        //         c.setEnterable(true);
        // });
    }

    public InterfacciaGioco(JFrame parentFrame, File f) throws Exception {
        initComponents();
        this.parentFrame = parentFrame;
        stampa = StampaTesto.getInstance(textBox, textArea, skipButton);
        mainComponents(true, f);
    }

    public void mainComponents(boolean loadGame, File f) throws Exception {
        game = new LeArmonieDelCustode();
        game.setChrono(chrono);

        try {

            try {
                Set<String> stopwords = Utils.loadFileListInSet(new File("resource/other/stopwords"));
                parser = new Parser(stopwords, game.getCommands());
            } catch (IOException ex) {
                throw ex;
            }

            if (!loadGame) {
                game.init();
            } else {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    LeArmonieDelCustode temp = (LeArmonieDelCustode) ois.readObject();
                    temp.attachObeservers();
                    game = temp;
                    chrono = game.getChrono();
                    chrono.startAgain(chrono.getElapsedTime());
                    ois.close();
                } catch (Exception ex) {
                    throw new GameFileException();
                }
            }
            textArea.append(
                    """
                            \t\t┌──────────────────────────────────┐
                            \t\t│  Benvenuto in "Le Armonie del Custode"! │
                            \t\t└──────────────────────────────────┘\n\n
                                    """);
            stampa.stampa(game.getIntro());
        } catch (GameFileException ex) {
            music.stopMusica();
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(InterfacciaGioco.class.getName()).log(Level.SEVERE, null, ex);
            music.stopMusica();
            throw new GameNotAvailableException();
        }
    }

    private void initComponents() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                parentFrame.setVisible(true);
                dispose();
            }
        });

        confermaChiusura = new javax.swing.JFrame();
        panel = new javax.swing.JPanel();
        imageDeathLabel = new javax.swing.JLabel();
        buttonPanelExit = new javax.swing.JPanel();
        jTextArea2 = new javax.swing.JTextArea();
        macroPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        textArea = new javax.swing.JTextArea();
        scrollPane.scrollRectToVisible(textArea.getVisibleRect());
        imageViewer = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        underPanel = new javax.swing.JPanel();
        textBox = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        skipButton = new javax.swing.JButton();
        inventarioButton = new javax.swing.JButton();
        esciButton = new javax.swing.JButton();
        musicButton = new javax.swing.JButton();

        tendina = new javax.swing.JMenu();
        cronometro = new javax.swing.JLabel();
        impostazioniItem = new javax.swing.JMenuItem();

        confermaChiusura.setIconImage(
                Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        confermaChiusura.setResizable(false);
        confermaChiusura.setSize(new java.awt.Dimension(700, 400));
        confermaChiusura.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                continueGame(evt);
            }
        });

        panel.setBackground(BACKGROUND_BLACK);
        panel.setLayout(new java.awt.BorderLayout());

        imageDeathLabel.setBackground(BACKGROUND_BEIGE);
        imageDeathLabel.setIcon(new ImageIcon(new ImageIcon("resource/img/exit_pixeled.png").getImage()
                .getScaledInstance(240, 300, Image.SCALE_DEFAULT)));
        imageDeathLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(imageDeathLabel, java.awt.BorderLayout.WEST);

        buttonPanelExit.setBackground(WHITE);

        jButton1.setBackground(BACKGROUND_BEIGE);
        jButton1.setText("Si");
        jButton1.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/yes_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1goToMenu(evt);
            }
        });
        buttonPanelExit.add(jButton1);

        jButton2.setBackground(BACKGROUND_BEIGE);
        jButton2.setText("No");
        jButton2.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/no_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2dontClose(evt);
            }
        });
        buttonPanelExit.add(jButton2);

        panel.add(buttonPanelExit, java.awt.BorderLayout.SOUTH);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(FONT);
        jTextArea2.setRows(5);
        jTextArea2.setText("\n\n\n\n\n                  Sei sicuro di voler abbandonare? \n\t                  :(");
        jTextArea2.setFocusable(false);
        jTextArea2.setBorder(null);
        panel.add(jTextArea2, java.awt.BorderLayout.CENTER);

        confermaChiusura.getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Le Armonie del Custode");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setResizable(false);

        macroPanel.setBackground(BACKGROUND_BLACK);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(900, 700));

        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(BACKGROUND_BEIGE);
        textArea.setColumns(20);
        textArea.setForeground(TEXT);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.setFont(FONT);
        textArea.setBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        scrollPane.setViewportView(textArea);

        imageViewer.setBackground(WHITE);

        imageLabel.setIcon(getScaledImage(new ImageIcon(LUCAPG)));
        imageViewer.add(imageLabel);

        underPanel.setBackground(WHITE);
        underPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        textBox.setPreferredSize(new java.awt.Dimension(650, 30));
        textBox.addActionListener(this::elaborateInput);
        underPanel.add(textBox);

        skipButton.setBackground(BACKGROUND_BEIGE);
        skipButton.setForeground(TEXT);
        skipButton.setText("Skip");
        skipButton.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/skip_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        skipButton.setPreferredSize(new java.awt.Dimension(90, 30));
        skipButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                skipButtonMouseReleased(evt);
            }
        });
        underPanel.add(skipButton);

        inventarioButton.setBackground(BACKGROUND_BEIGE);
        inventarioButton.setForeground(TEXT);
        inventarioButton.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/backpack_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        inventarioButton.setText("Inventario");
        inventarioButton.setPreferredSize(new java.awt.Dimension(150, 30));
        inventarioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventarioMouseClicked(evt);
            }
        });
        underPanel.add(inventarioButton);

        impostazioniItem.setBackground(BACKGROUND_BEIGE);
        impostazioniItem.setForeground(TEXT);
        impostazioniItem.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/options_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        impostazioniItem.setText("Impostazioni");
        impostazioniItem.setPreferredSize(new java.awt.Dimension(105, 30));
        impostazioniItem.addActionListener(this::impostazioniMouseClicked);

        // JMenu tendina = new JMenu("Opzioni");
        tendina.setText("Opzioni");
        tendina.setPreferredSize(new java.awt.Dimension(90, 30));
        tendina.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/menu_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        tendina.setBorder(new javax.swing.border.LineBorder(WHITE, 4));
        tendina.setBackground(BACKGROUND_BEIGE);
        tendina.setForeground(TEXT);
        tendina.add(impostazioniItem);

        chrono.start();

        // JLabel cronometro = new JLabel(chrono.getTimeFormatted());
        cronometro.setText(chrono.getTimeFormatted());
        cronometro.setForeground(TEXT);
        cronometro.setBackground(WHITE);
        cronometro.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        cronometro.setVerticalAlignment(javax.swing.JLabel.CENTER);
        cronometro.setPreferredSize(new java.awt.Dimension(90, 30));

        // Ogni secondo, aggiorna il cronometro con il tempo trascorso
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            cronometro.setText(chrono.getTimeFormatted());
        }, 0, 1, TimeUnit.SECONDS);

        /*
         * Equivalente con Thread primitivo
         * new Thread(() -> {
         * while (true) {
         * try {
         * Thread.sleep(1000);
         * cronometro.setText(chrono.getTimeFormatted());
         * } catch (InterruptedException ex) {
         * Logger.getLogger(InterfacciaGioco.class.getName()).log(Level.SEVERE, null,
         * ex);
         * }
         * }
         * }).start();
         */

        menuBar.add(tendina);
        menuBar.add(javax.swing.Box.createHorizontalGlue());
        menuBar.add(cronometro);

        menuBar.setBackground(WHITE);
        menuBar.setForeground(TEXT);
        setJMenuBar(menuBar);

        // Avvio musica
        music.playMusic("resource/other/background_music.wav");

        // Gestione pulsante musica
        musicButton.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/music_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        musicButton.setText("Mute");
        musicButton.setBackground(BACKGROUND_BEIGE);
        musicButton.setForeground(RED);
        musicButton.setPreferredSize(new java.awt.Dimension(105, 30));

        musicButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (music.isPlaying()) {
                    music.pausaMusica();
                    musicButton.setText("Play");
                    musicButton.setForeground(GREEN);
                } else {
                    music.riprendiMusica();
                    musicButton.setText("Mute");
                    musicButton.setForeground(RED);
                }
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                music.stopMusica();
            }
        });

        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                if (music.isPlaying()) {
                    musicButton.setText("Mute");
                    musicButton.setForeground(RED);
                } else {
                    musicButton.setText("Play");
                    musicButton.setForeground(GREEN);
                }
            }

            @Override
            public void windowLostFocus(java.awt.event.WindowEvent evt) {

            }
        });

        underPanel.add(musicButton);

        esciButton.setBackground(BACKGROUND_BEIGE);
        esciButton.setForeground(TEXT);
        esciButton.setText("Esci");
        esciButton.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/exit_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        esciButton.setPreferredSize(new java.awt.Dimension(90, 30));
        esciButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                esciButtonMouseClicked(evt);
            }
        });
        underPanel.add(esciButton);

        javax.swing.GroupLayout macroPanelLayout = new javax.swing.GroupLayout(macroPanel);
        macroPanel.setLayout(macroPanelLayout);
        macroPanelLayout.setHorizontalGroup(
                macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, macroPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(macroPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(underPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(macroPanelLayout.createSequentialGroup()
                                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 900,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(imageViewer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap()));
        macroPanelLayout.setVerticalGroup(
                macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(macroPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(macroPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(imageViewer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(underPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        getContentPane().add(macroPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void inventarioMouseClicked(java.awt.event.MouseEvent evt) {
        InterfacciaInventario inventario = new InterfacciaInventario(game.getInventario());
        inventario.setVisible(true);
    }

    private void continueGame(java.awt.event.ComponentEvent evt) {
        setEnabled(true);
        setVisible(true);
        confermaChiusura.setVisible(false);
    }

    private void elaborateInput(java.awt.event.ActionEvent evt) {
        String input = textBox.getText().toLowerCase().trim();
        if (!input.isBlank()) {
            textArea.append(input + "\n");
            textBox.setText("");
            ParserOutput p = parser.parse(input.toLowerCase(), game.getInventario().getOggetti(), game.getCurrentCasella().getOggetti(), this);
            game.nextMove(p, stampa);
        }
    }

    private void impostazioniMouseClicked(ActionEvent evt) {
        InterfacciaImpostazioni impostazioni = new InterfacciaImpostazioni(InterfacciaGioco.this);
        impostazioni.setVisible(true);
    }

    private void skipButtonMouseReleased(java.awt.event.MouseEvent evt) {
        if (skipButton.isEnabled()) {
            stampa.interrupt();
            skipButton.setEnabled(false);
        }
    }

    private void jButton1goToMenu(java.awt.event.MouseEvent evt) {
        parentFrame.setVisible(true);
        confermaChiusura.dispose();
        dispose();
    }

    private void jButton2dontClose(java.awt.event.MouseEvent evt) {
        setEnabled(true);
        confermaChiusura.setVisible(false);
    }

    private void esciButtonMouseClicked(java.awt.event.MouseEvent evt) {
        setEnabled(false);
        confermaChiusura.setLocationRelativeTo(null);
        confermaChiusura.setTitle("Le Armonie del Custode - Exit Menu");
        confermaChiusura.setVisible(true);
    }

    public void endGame() {
        InterfacciaFinale interfacciaFinale = new InterfacciaFinale(parentFrame, game.getChrono().getElapsedTime());
        interfacciaFinale.setVisible(true);
        dispose();
    }

    private ImageIcon getScaledImage(ImageIcon image) {
        return new ImageIcon(image.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
    }

    public void changeImageViewer(ImageIcon image) {
        imageLabel.setIcon(getScaledImage(image));
    }

    public final Musica getMusica() {
        return music;
    }

    public Chrono getChrono() {
        return chrono;
    }

    private javax.swing.JPanel buttonPanelExit;
    private javax.swing.JFrame confermaChiusura;
    private javax.swing.JButton esciButton;
    private javax.swing.JLabel imageDeathLabel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel imageViewer;
    private javax.swing.JButton inventarioButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton skipButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField textBox;
    private javax.swing.JPanel underPanel;
    private javax.swing.JButton musicButton;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu tendina;
    private javax.swing.JMenuItem impostazioniItem;
    private javax.swing.JLabel cronometro;
}