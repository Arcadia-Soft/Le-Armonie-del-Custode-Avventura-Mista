package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import classifica.Client;
import classifica.Record;
import other.Musica;
import other.StampaTesto;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaFinale extends javax.swing.JFrame {
    private final Color BLACK = new Color(32, 32, 35);
    private final Color WHITE = new Color(250, 249, 246);
    private final Color BACKGROUND_BEIGE = new Color(237, 232, 208);
    private final Color TEXT = new Color(06, 06, 06);
    private final Font FONT = new Font("Serif", 0, 20);

    private final JFrame parentFrame;

    private Musica music = new Musica();

    public InterfacciaFinale(JFrame parentFrame, long time) {
        initComponents();

        this.parentFrame = parentFrame;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());

        String giocatore = System.getProperty("user.name");

        Client client = new Client();
        Record record = new Record(giocatore, time, formatted);
        try {
            client.sendRecord(record);
            music.playMusic("resource/other/the_end_music.wav");
        } catch (Exception e) {
            JButton okButton = new JButton("Ok");
            okButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    dispose();
                    music.playMusic("resource/other/the_end_music.wav");
                }
            });
            JOptionPane info = new JOptionPane(e.getMessage(), JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[] { okButton });
            info.createDialog(InterfacciaFinale.this, "Info").setVisible(true);

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

        macroPanel = new javax.swing.JPanel();
        labImage = new javax.swing.JLabel();
        theEnd = new javax.swing.JButton();
        textArea = new javax.swing.JTextArea();
        underPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Le Armonie del Custode - Good Ending! :)");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setResizable(false);
        setSize(1000, 600);
        setBackground(BLACK);

        macroPanel.setLayout(new java.awt.BorderLayout());
        macroPanel.setBackground(BLACK);

        StampaTesto stampa = StampaTesto.getInstance(new JTextField(), textArea, new JButton());
        stampa.stampaFinale(
                "\n\tCon le armonie suonate dal liuto scritte sul pentagramma, il Drago si calma, riposando nel Castello. \n\tHai liberato Arcadia dalla minaccia del Drago, è festa in tutto il Regno, tutti festeggiano \n\tLuca, il nuovo Custode delle Armonie.\n\t\t\t\tRICONOSCIMENTI \n\t\t\tGrazie per aver giocato a Le Armonie del Custode!\n\t\t\t       Alessandro Pellegrino e Kevin Saracino");

        labImage.setIcon(new ImageIcon(new ImageIcon("resource/img/scena_the_end_pixeled.png").getImage()
                .getScaledInstance(1000, 650, Image.SCALE_DEFAULT)));
        labImage.setBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        macroPanel.add(labImage, BorderLayout.CENTER);

        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(BACKGROUND_BEIGE);
        textArea.setColumns(20);
        textArea.setForeground(TEXT);
        textArea.setLineWrap(true);
        textArea.setRows(8);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.setFont(FONT);
        textArea.setBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        textArea.setMinimumSize(new java.awt.Dimension(800, 200));
        textArea.setMaximumSize(new java.awt.Dimension(800, 200));

        theEnd.setBackground(BACKGROUND_BEIGE);
        theEnd.setForeground(BLACK);
        theEnd.setText("The End");
        theEnd.setBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        theEnd.setFocusPainted(false);
        theEnd.setPreferredSize(new java.awt.Dimension(90, 40));
        theEnd.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                music.stopMusica();
                theEndClose(evt);
            }
        });

        underPanel.setBackground(BLACK);

        underPanel.add(theEnd, BorderLayout.CENTER);
        macroPanel.add(textArea, BorderLayout.NORTH);
        macroPanel.add(underPanel, BorderLayout.PAGE_END);
        add(macroPanel, BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(macroPanel, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(macroPanel, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }

    private void theEndClose(java.awt.event.MouseEvent evt) {
        dispose();
        parentFrame.setVisible(true);
    }

    private javax.swing.JLabel labImage;
    private javax.swing.JPanel macroPanel;
    private javax.swing.JButton theEnd;
    private javax.swing.JTextArea textArea;
    private javax.swing.JPanel underPanel;
}
