/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import other.StampaTesto;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaImpostazioni extends javax.swing.JFrame {
    private final Color BLACK = new Color(32, 32, 35);
    private final Color WHITE = new Color(250, 249, 246);
    private final Color BACKGROUND_BEIGE = new Color(237, 232, 208);
    private final Color TEXT = new Color(06, 06, 06);
    private final Color RED = new Color(238, 75, 43);
    private final Color GREEN = new Color(9, 121, 105);

    private final JFrame parentFrame;

    /**
     * Creates new form InterfacciaImpostazioni
     */
    public InterfacciaImpostazioni(JFrame parentFrame) {
        initComponents();
        this.parentFrame = parentFrame;

        if (!((InterfacciaGioco) parentFrame).getMusica().isPlaying()) {
            ((InterfacciaGioco) parentFrame).getMusica().pausaMusica();
            musicButton.setText("Play");
            musicButton.setForeground(GREEN);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        macroPanel = new javax.swing.JPanel();
        underPanel = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        musicButton = new javax.swing.JButton();
        labVolume = new javax.swing.JLabel();
        volumeUp = new javax.swing.JButton();
        volumeDown = new javax.swing.JButton();
        testoPanel = new javax.swing.JPanel();
        velocitaTesto = new javax.swing.JLabel();
        testoLento = new javax.swing.JButton();
        testoMedio = new javax.swing.JButton();
        testoVeloce = new javax.swing.JButton();

        setTitle("Impostazioni");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(BLACK);

        macroPanel.setLayout(new java.awt.BorderLayout());
        macroPanel.setBackground(BLACK);

        // Gestione pulsante musica
        musicButton.setText("Mute");
        musicButton.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/music_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        musicButton.setBackground(BACKGROUND_BEIGE);
        musicButton.setForeground(RED);
        musicButton.setPreferredSize(new java.awt.Dimension(72, 30));

        musicButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (((InterfacciaGioco) parentFrame).getMusica().isPlaying()) {
                    ((InterfacciaGioco) parentFrame).getMusica().pausaMusica();
                    musicButton.setText("Play");
                    musicButton.setForeground(GREEN);
                } else {
                    ((InterfacciaGioco) parentFrame).getMusica().riprendiMusica();
                    musicButton.setText("Mute");
                    musicButton.setForeground(RED);
                }
            }
        });

        labVolume.setText("Volume");
        labVolume.setForeground(WHITE);
        labVolume.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        labVolume.setVerticalAlignment(javax.swing.JLabel.CENTER);

        volumeUp.setText("+");
        volumeUp.setBackground(BLACK);
        volumeUp.setForeground(WHITE);
        volumeUp.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (((InterfacciaGioco) parentFrame).getMusica().isPlaying()
                        && ((InterfacciaGioco) parentFrame).getMusica().getVolume() + 5f < 6f)
                    ((InterfacciaGioco) parentFrame).getMusica()
                            .setVolume(((InterfacciaGioco) parentFrame).getMusica().getVolume() + 5f);
            }
        });

        volumeDown.setText("-");
        volumeDown.setBackground(BLACK);
        volumeDown.setForeground(WHITE);
        volumeDown.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (((InterfacciaGioco) parentFrame).getMusica().isPlaying()
                        && ((InterfacciaGioco) parentFrame).getMusica().getVolume() - 5f > -80f)
                    ((InterfacciaGioco) parentFrame).getMusica()
                            .setVolume(((InterfacciaGioco) parentFrame).getMusica().getVolume() - 5f);
            }
        });

        velocitaTesto.setText("Velocità testo: ");
        velocitaTesto.setForeground(WHITE);
        velocitaTesto.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        testoLento.setText("Lento");
        testoLento.setBackground(BLACK);
        testoLento.setForeground(WHITE);
        testoLento.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StampaTesto.getInstance().setAttesa(300);
            }
        });

        testoMedio.setText("Medio");
        testoMedio.setBackground(BLACK);
        testoMedio.setForeground(WHITE);
        testoMedio.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StampaTesto.getInstance().setAttesa(50);
            }
        });

        testoVeloce.setText("Veloce");
        testoVeloce.setBackground(BLACK);
        testoVeloce.setForeground(WHITE);
        testoVeloce.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StampaTesto.getInstance().setAttesa(10);
            }
        });

        testoPanel.setLayout(new java.awt.BorderLayout());
        testoPanel.setBackground(BLACK);
        testoPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        macroPanel.add(volumeDown, java.awt.BorderLayout.WEST);
        macroPanel.add(volumeUp, java.awt.BorderLayout.EAST);
        macroPanel.add(musicButton, java.awt.BorderLayout.NORTH);
        macroPanel.add(labVolume, java.awt.BorderLayout.CENTER);

        testoPanel.add(velocitaTesto, java.awt.BorderLayout.NORTH);
        testoPanel.add(testoLento, java.awt.BorderLayout.PAGE_END);
        testoPanel.add(testoMedio, java.awt.BorderLayout.PAGE_END);
        testoPanel.add(testoVeloce, java.awt.BorderLayout.PAGE_END);

        macroPanel.add(testoPanel, java.awt.BorderLayout.SOUTH);

        closeButton.setBackground(BACKGROUND_BEIGE);
        closeButton.setForeground(TEXT);
        closeButton.setText("Chiudi");
        closeButton.setPreferredSize(new java.awt.Dimension(72, 40));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                closeButtonClose(evt);
            }
        });

        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {

            }

            @Override
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                dispose();
            }
        });
        // macroPanel.add(closeButton, java.awt.BorderLayout.SOUTH);

        underPanel.setBackground(WHITE);

        underPanel.add(closeButton, BorderLayout.CENTER);
        add(macroPanel, java.awt.BorderLayout.CENTER);
        add(underPanel, java.awt.BorderLayout.PAGE_END);
        pack();
    }

    private void closeButtonClose(java.awt.event.MouseEvent evt) {
        this.dispose();
    }

    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel underPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton musicButton;
    private javax.swing.JLabel labVolume;
    private javax.swing.JButton volumeUp;
    private javax.swing.JButton volumeDown;
    private javax.swing.JPanel testoPanel;
    private javax.swing.JLabel velocitaTesto;
    private javax.swing.JButton testoLento;
    private javax.swing.JButton testoMedio;
    private javax.swing.JButton testoVeloce;

}