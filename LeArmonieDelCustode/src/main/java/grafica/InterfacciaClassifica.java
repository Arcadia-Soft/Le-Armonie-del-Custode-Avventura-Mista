package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import classifica.Classifica;
import classifica.Client;
import eccezioni.GetClassificaException;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class InterfacciaClassifica extends javax.swing.JFrame {
    private final Color BACKGROUND = new Color(237, 232, 208);
    private final Color BACKGROUND_BLACK = new Color(54, 69, 79);
    private final Color TEXT = new Color(06, 06, 06);
    private final Color WHITE = new Color(250, 249, 246);
    private final String[] column = { "Nome Giocatore", "Tempo", "Data" };
    private JTable classifica;
    private DefaultTableModel nonEditableModel;

    public InterfacciaClassifica() throws Exception {
        initComponents();
        getData();
    }

    private void initComponents() {
        macroPanel = new javax.swing.JPanel();
        underPanel = new javax.swing.JPanel();
        close = new javax.swing.JButton();

        setTitle("Le Armonie del Custode - classifica");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setPreferredSize(new Dimension(600, 550));
        getContentPane().setBackground(BACKGROUND_BLACK);
        setLocationRelativeTo(null);
        setResizable(false);

        // Creo un modello di tabella non editabile
        nonEditableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nonEditableModel.setDataVector(new String[0][3], column);
        classifica = new JTable();
        classifica.setModel(nonEditableModel);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(classifica.getModel());
        classifica.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        // sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        classifica.getTableHeader().setResizingAllowed(false); // Disabilito il ridimensionamento delle colonne
        classifica.getTableHeader().setReorderingAllowed(false); // Disabilito lo spostamento delle colonne

        classifica.setBackground(BACKGROUND);
        classifica.setForeground(TEXT);
        classifica.setRowHeight(30);
        classifica.setGridColor(WHITE);
        classifica.setShowGrid(true);
        classifica.setShowVerticalLines(true);
        classifica.setShowHorizontalLines(true);
        classifica.getColumnModel().getColumn(0).setPreferredWidth(280);
        classifica.getColumnModel().getColumn(1).setPreferredWidth(70);
        classifica.getColumnModel().getColumn(2).setPreferredWidth(100);
        classifica.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        macroPanel.setBackground(BACKGROUND_BLACK);
        macroPanel.add(new JScrollPane(classifica)); // Necessario aggiungerlo ad uno JScrollPane per visualizzare anche
                                                     // i nomi delle colonne

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

        pack();
    }

    private void getData() throws GetClassificaException {
        Client client = new Client();
        try {
            Classifica recordTracker = client.requestClassifica();
            String[][] data = recordTracker.getRecordsAsMatrix();
            for (int i = nonEditableModel.getRowCount() - 1; i >= 0; i--) {
                nonEditableModel.removeRow(i);
            }
            if (data.length > 0) {
                for (String[] row : data) {
                    nonEditableModel.addRow(row);
                }
            }

        } catch (Exception e) {
            throw new GetClassificaException();
        }
    }

    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel underPanel;
    private javax.swing.JButton close;
}
