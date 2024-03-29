package gui.alemania;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import gui.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class Informe extends JPanel implements Frame {
    final Long FIVE_YEARS = Long.parseLong("157784760000");
    final Long ONE_WEEK = Long.parseLong("604800000");
    JButton primerInformeButton;
    JTable csvPreviewTable;
    JButton informeSemanalButton;
    JButton enviarSFTPButton;
    JButton exportarButton;
    JScrollPane scrollPane;
    JPanel root;
    JProgressBar progressBar1;

    public Informe() {
        add(root);
        exportarButton.addActionListener(e -> {
            var filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.setMultiSelectionEnabled(false);
            var result = filechooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                var folder = filechooser.getSelectedFile();
                var file = new File(folder, "export.csv");
                var save = new GuardarCSVWorker(this, file);
                save.execute();
            }
        });
        informeSemanalButton.addActionListener(
                e -> new GenerarInformeSemanalWorker(this).execute()
        );
        primerInformeButton.addActionListener(
                e -> new GenerarPrimerInformeWorker(this).execute()
        );
        enviarSFTPButton.addActionListener(e -> {
            var ip = new JTextField();
            var username = new JTextField();
            var password = new JPasswordField();
            var destination = new JTextField();
            destination.setText("/");
            var components = new JComponent[]{
                    new JLabel("Host"),
                    ip,
                    new JLabel("Username"),
                    username,
                    new JLabel("Password"),
                    password,
                    new JLabel("Destino"),
                    destination
            };

            var res = JOptionPane.showConfirmDialog(this, components, "Enviar por SFTP", JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    var temporalFile = Files.createTempFile(new Date().toString(), ".csv");
                    var worker = new SFTPWorker(this,
                            temporalFile.toFile(),
                            ip.getText(),
                            username.getText(),
                            String.valueOf(password.getPassword()),
                            destination.getText());
                    worker.execute();
                } catch (IOException ex) {
                    var m = ex.getMessage();
                    JOptionPane.showMessageDialog(this, m, m, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    void lockUI() {
        this.progressBar1.setValue(0);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.enviarSFTPButton.setEnabled(false);
        this.informeSemanalButton.setEnabled(false);
        this.primerInformeButton.setEnabled(false);
        this.exportarButton.setEnabled(false);

    }

    void unlockUI() {
        this.progressBar1.setValue(this.progressBar1.getMaximum());
        setCursor(Cursor.getDefaultCursor());
        this.enviarSFTPButton.setEnabled(true);
        this.informeSemanalButton.setEnabled(true);
        this.primerInformeButton.setEnabled(true);
        this.exportarButton.setEnabled(true);
    }

    void setData(java.util.List<Object[]> data) {
        csvPreviewTable.removeAll();
        var tablemodel = new DefaultTableModel(
                new String[]{
                        "IBAN",
                        "Apellido",
                        "Nombre",
                        "Direcciones",
                        "NIF/CIF",
                        "Fecha Nacimiento/Creación"
                },
                0
        );
        progressBar1.setMaximum(data.size());
        var count = 0;
        for (var entry : data) {
            tablemodel.addRow(entry);
            progressBar1.setValue(count);
            count++;
        }
        csvPreviewTable.setModel(tablemodel);
        resizeColumnWidth(csvPreviewTable);
        progressBar1.setValue(progressBar1.getMaximum());

    }

    private void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Override
    public String getTitleBarName() {
        return "Informe Alemania";
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        root.setOpaque(true);
        root.setPreferredSize(new Dimension(1250, 500));
        primerInformeButton = new JButton();
        primerInformeButton.setText("Generar Primer Informe");
        root.add(primerInformeButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        informeSemanalButton = new JButton();
        informeSemanalButton.setText("Generar Informe Semanal");
        root.add(informeSemanalButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enviarSFTPButton = new JButton();
        enviarSFTPButton.setEnabled(true);
        enviarSFTPButton.setText("Enviar por SFTP");
        root.add(enviarSFTPButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exportarButton = new JButton();
        exportarButton.setEnabled(true);
        exportarButton.setText("Exportar...");
        root.add(exportarButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPane = new JScrollPane();
        root.add(scrollPane, new GridConstraints(0, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        csvPreviewTable = new JTable();
        csvPreviewTable.setAutoResizeMode(0);
        csvPreviewTable.setCellSelectionEnabled(false);
        csvPreviewTable.setEnabled(false);
        scrollPane.setViewportView(csvPreviewTable);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.add(panel1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        progressBar1 = new JProgressBar();
        panel1.add(progressBar1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}
