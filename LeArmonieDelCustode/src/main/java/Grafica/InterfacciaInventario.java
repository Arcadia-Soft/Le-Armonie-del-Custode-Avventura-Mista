package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import tipi.Inventario;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaInventario extends javax.swing.JFrame {
    private final Color BACKGROUND = new Color(237, 232, 208);
    private final Color BACKGROUND_BLACK = new Color(54, 69, 79);
    private final Color TEXT = new Color(06, 06, 06);
    private final Color WHITE = new Color(250, 249, 246);

    public InterfacciaInventario(Inventario inv) {
        initComponents(inv);
    }

    private void initComponents(Inventario inv) {
        macroPanel = new javax.swing.JPanel();
        underPanel = new javax.swing.JPanel();
        close = new javax.swing.JButton();

        setTitle("Le Armonie del Custode - Inventario");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setPreferredSize(new Dimension(600, 550));
        getContentPane().setBackground(BACKGROUND_BLACK);
        setLocationRelativeTo(null);
        setResizable(false);

        String[][] data = inv.getInventarioToJTableData();

        // Creo un modello di tabella non editabile
        DefaultTableModel nonEditableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] column = { "Oggetti", "Quantità" };
        nonEditableModel.setDataVector(data, column);
        oggetti = new javax.swing.JTable(data, column);
        oggetti.setModel(nonEditableModel);

        oggetti.getTableHeader().setResizingAllowed(false); // Disabilito il ridimensionamento delle colonne
        oggetti.getTableHeader().setReorderingAllowed(false); // Disabilito lo spostamento delle colonne

        oggetti.setBackground(BACKGROUND);
        oggetti.setForeground(TEXT);
        oggetti.setRowHeight(30);
        oggetti.setGridColor(WHITE);
        oggetti.setShowGrid(true);
        oggetti.setShowVerticalLines(true);
        oggetti.setShowHorizontalLines(true);
        oggetti.getColumnModel().getColumn(0).setPreferredWidth(380);
        oggetti.getColumnModel().getColumn(1).setPreferredWidth(70);
        oggetti.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        macroPanel.setBackground(BACKGROUND_BLACK);
        macroPanel.add(new JScrollPane(oggetti)); // Necessario aggiungerlo ad uno JScrollPane per visualizzare anche i
                                                  // nomi delle colonne

        close.setText("Chiudi");
        close.setBackground(BACKGROUND);
        close.setForeground(TEXT);
        close.setPreferredSize(new Dimension(72, 30));

        close.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dispose();
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

        underPanel.add(close, BorderLayout.CENTER);
        getContentPane().add(macroPanel, BorderLayout.NORTH);
        getContentPane().add(underPanel, BorderLayout.SOUTH);

        pack(); // Rende la finestra della dimensione minima necessaria per visualizzare tutti i
                // componenti
    }

    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel underPanel;
    private javax.swing.JButton close;
    private javax.swing.JTable oggetti;
}