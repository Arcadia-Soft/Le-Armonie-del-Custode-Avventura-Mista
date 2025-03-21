package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaMorte extends javax.swing.JFrame {
    private final Color BLACK = new Color(32, 32, 35);
    private final Color WHITE = new Color(250, 249, 246);
    private final JFrame parentFrame;

    public InterfacciaMorte(JFrame parentFrame) {
        initComponents();

        this.parentFrame = parentFrame;
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
        underPanel = new javax.swing.JPanel();
        labWriting = new javax.swing.JLabel();
        labImage = new javax.swing.JLabel();
        theEnd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Le Armonie del Custode - Bad Ending :(");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setResizable(false);
        setBackground(BLACK);

        macroPanel.setLayout(new java.awt.BorderLayout());
        macroPanel.setBackground(BLACK);

        labWriting.setIcon(new ImageIcon(new ImageIcon("resource/img/writing_death.png").getImage()
                .getScaledInstance(800, 300, Image.SCALE_DEFAULT)));
        macroPanel.add(labWriting, java.awt.BorderLayout.CENTER);

        labImage.setIcon(new ImageIcon(new ImageIcon("resource/img/death_pixeled.png").getImage()
                .getScaledInstance(250, 450, Image.SCALE_DEFAULT)));
        labImage.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 10, 10));
        macroPanel.add(labImage, java.awt.BorderLayout.WEST);

        theEnd.setBackground(BLACK);
        theEnd.setForeground(WHITE);
        theEnd.setText("The End");
        theEnd.setBorder(null);
        theEnd.setBorderPainted(false);
        theEnd.setFocusPainted(false);
        theEnd.setPreferredSize(new java.awt.Dimension(72, 40));
        theEnd.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                theEndClose(evt);
            }
        });

        underPanel.setBackground(BLACK);

        underPanel.add(theEnd, BorderLayout.CENTER);
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
    private javax.swing.JLabel labWriting;
    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel underPanel;
    private javax.swing.JButton theEnd;
}