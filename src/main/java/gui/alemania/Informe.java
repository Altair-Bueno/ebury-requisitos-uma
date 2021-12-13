package gui.alemania;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import database.HibernateStartUp;
import database.tables.EburyAccountEntity;
import database.tables.OperationEntity;
import gui.Frame;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Informe extends JPanel implements Frame {
    private final Long FIVE_YEARS = Long.parseLong("157784760000");
    private final Long ONE_WEEK = Long.parseLong("604800000");
    private JButton primerInformeButton;
    private JTable csvPreviewTable;
    private JButton informeSemanalButton;
    private JButton enviarSFTPButton;
    private JButton exportarButton;
    private JScrollPane scrollPane;
    private JPanel root;
    private JProgressBar progressBar1;

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
        informeSemanalButton.addActionListener(e -> {
            var g = new GenerarInformeSemanalWorker(this);
            g.execute();
        });
        primerInformeButton.addActionListener(e -> {
            var g = new GenerarPrimerInformeWorker(this);
            g.execute();
        });
        enviarSFTPButton.addActionListener(e -> {
            var ip = new JTextField();
            var username = new JTextField();
            var pass = new JPasswordField();
            var dest = new JTextField();
            dest.setText("/");
            var components = new JComponent[]{
                    new JLabel("Host"),
                    ip,
                    new JLabel("Username"),
                    username,
                    new JLabel("Password"),
                    pass,
                    new JLabel("Destino"),
                    dest
            };
            var res = JOptionPane.showConfirmDialog(this, components, "Enviar por SFTP", JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION) {
                Path temp = null;
                try {
                    temp = Files.createTempFile(new Date().toString(), ".csv");
                    var worker = new SFTPWorker(this,
                            temp.toFile(),
                            ip.getText(),
                            username.getText(),
                            String.valueOf(pass.getPassword()),
                            dest.getText());
                    worker.execute();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    var m = ex.getMessage();
                    JOptionPane.showMessageDialog(this, m, m, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void lockUI() {
        this.progressBar1.setValue(0);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.enviarSFTPButton.setEnabled(false);
        this.informeSemanalButton.setEnabled(false);
        this.primerInformeButton.setEnabled(false);
        this.exportarButton.setEnabled(false);

    }

    private void unlockUI() {
        this.progressBar1.setValue(this.progressBar1.getMaximum());
        setCursor(Cursor.getDefaultCursor());
        this.enviarSFTPButton.setEnabled(true);
        this.informeSemanalButton.setEnabled(true);
        this.primerInformeButton.setEnabled(true);
        this.exportarButton.setEnabled(true);
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

    private class SFTPWorker extends GuardarCSVWorker {
        String ip;
        String username;
        String pass;
        String dest;

        public SFTPWorker(Informe informe, File temp, String ip, String username, String pass, String dest) {
            super(informe, temp);
            this.ip = ip;
            this.username = username;
            this.pass = pass;
            this.dest = dest;
        }

        @Override
        protected Void doInBackground() throws Exception {
            super.doInBackground();
            var frame = new JFrame("Send");
            var sending = new JLabel("Sending file");
            frame.add(sending);
            frame.setUndecorated(true);
            frame.pack();
            frame.setLocationRelativeTo(informe);
            frame.setVisible(true);

            try {
                var jsch = new JSch();
                var session = jsch.getSession(username, ip);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(pass);
                session.connect();
                var channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();
                channel.put(file.getAbsolutePath(), dest);
                channel.exit();
                var m = "Archivo enviado";
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                var m = ex.getMessage();
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                frame.dispose();
            }
            return null;
        }

        @Override
        protected void done() {
            unlockUI();
        }
    }

    private class GuardarCSVWorker extends SwingWorker<Void, Void> {
        Informe informe;
        File file;

        public GuardarCSVWorker(Informe informe, File file) {
            this.informe = informe;
            this.file = file;
        }

        @Override
        protected Void doInBackground() throws Exception {
            lockUI();
            var frame = new JFrame("Save");
            frame.setUndecorated(true);
            frame.pack();
            frame.setLocationRelativeTo(informe);
            frame.setVisible(true);
            generateCSV(frame);
            return null;
        }

        private void generateCSV(JFrame frame) {
            var model = csvPreviewTable.getModel();
            progressBar1.setMaximum(model.getRowCount());
            try (var csv = new FileWriter(file)) {
                //TODO añade un montón de columnas que no deberían estar
                for (int i = 0; i < model.getColumnCount() - 1; i++) {
                    csv.write(model.getColumnName(i) + ",");
                }
                csv.write(model.getColumnName(model.getColumnCount() - 1));
                csv.write("\n");
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount() - 1; j++) {
                        var value = model.getValueAt(i, j).toString();
                        var safeValue = value.replace(",", "");
                        csv.write(safeValue + ",");
                    }
                    csv.write(model.getValueAt(i, model.getColumnCount() - 1).toString().replace(',', Character.MIN_VALUE));
                    progressBar1.setValue(i);
                    csv.write("\n");
                }
                var m = "Exportación correcta";
                JOptionPane.showMessageDialog(frame, m, m, JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                var m = ex.getMessage();
                JOptionPane.showMessageDialog(frame, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                frame.dispose();
            }
        }

        @Override
        protected void done() {
            unlockUI();
        }
    }

    private class GenerarPrimerInformeWorker extends SwingWorker<List<Object[]>, Void> {
        Informe informe;

        public GenerarPrimerInformeWorker(Informe informe) {
            this.informe = informe;
        }

        @Override
        protected List<Object[]> doInBackground() throws Exception {
            progressBar1.setMaximum(1000);
            lockUI();
            try (var session = HibernateStartUp.getSessionFactory().openSession()) {
                // TODO querry no es la correcta. Completar
                progressBar1.setValue(250);
                var b = session.createQuery("select op.id from OperationEntity op, EburyAccountEntity ac where not op.eburyAccount.id = 1", Object[].class).getResultList();
                System.out.println(b);
                var c = session.createQuery("select op.id, ac.status from OperationEntity op join op.eburyAccount ac", Object[].class).getResultList();
                System.out.println(c);
                var a = session.createQuery("select ac.bankAccount.iban, coalesce(op.amount, 'noexistente'), ac.registerdate, coalesce(ac.closedate, 'noexistente'), concat(ac.owner.name, ' ' , coalesce(ac.owner.lastName1, ''), ' ', coalesce(ac.owner.lastName2, '')), coalesce(ac.owner.birthDate, 'noexistente') , coalesce(op.beneficiary, 'noexistente') from OperationEntity op join op.eburyAccount ac", Object[].class).getResultList();
                System.out.println(a);
                return a;
            }
        }

        @Override
        protected void done() {
            try {
                var result = get();
                csvPreviewTable.removeAll();
                // TODO no está bien del todo. Mirar las diapositivas del profesor
                var tablemodel = new DefaultTableModel(new String[]{"IBAN", "Depósito", "Apertura", "Disolución", "Nombre", "Fecha Nacimiento/Creación", "Beneficiario"}, 0);
                progressBar1.setMaximum(result.size());
                for (int i = 0; i < result.size(); i++) {
                    //var fiveYearsAgo = new Date();
                    //fiveYearsAgo = new Date(fiveYearsAgo.getTime() - FIVE_YEARS);
                    //TODO Condición de los cinco años
                    if (true) {
                        tablemodel.addRow(new Object[]{
                                (String) result.get(i)[0],
                                (String) result.get(i)[1],
                                (String) result.get(i)[2],
                                (String) result.get(i)[3],
                                (String) result.get(i)[4],
                                (String) result.get(i)[5],
                                (String) result.get(i)[6],
                                (String) result.get(i)[7],
                        });
                    }
                    progressBar1.setValue(i);
                }
                csvPreviewTable.setModel(tablemodel);
                resizeColumnWidth(csvPreviewTable);
                progressBar1.setValue(progressBar1.getMaximum());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                var m = e.getMessage();
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                informe.unlockUI();
            }
        }

        public void resizeColumnWidth(JTable table) {
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
    }

    private class GenerarInformeSemanalWorker extends SwingWorker<List<EburyAccountEntity>, Void> {
        Informe informe;

        public GenerarInformeSemanalWorker(Informe informe) {
            this.informe = informe;
        }

        @Override
        protected List<EburyAccountEntity> doInBackground() throws Exception {
            progressBar1.setValue(0);
            progressBar1.setMaximum(1000);
            lockUI();
            try (var session = HibernateStartUp.getSessionFactory().openSession()) {
                progressBar1.setValue(25);
                return session.createQuery("from EburyAccountEntity").list();
            }
        }

        @Override
        protected void done() {
            try {
                var result = get();
                csvPreviewTable.removeAll();
                // TODO no está bien del todo. Mirar las diapositivas del profesor
                var tablemodel = new DefaultTableModel(new String[]{"IBAN", "Nombre", "Dirección", "NIF", "Fecha Nacimiento/Creación"}, 0);
                progressBar1.setMaximum(result.size());
                for (int i = 0; i < result.size(); i++) {
                    var cuentabanc = result.get(i).getBankAccount();
                    var duenyo = result.get(i).getOwner();
                    var weekAgo = new Date();
                    weekAgo = new Date(weekAgo.getTime() - ONE_WEEK);
                    if (weekAgo.getTime() <= duenyo.getRegisterDate().getTime()) {
                        tablemodel.addRow(
                                new Object[]{
                                        cuentabanc.getIban(),
                                        duenyo.fullName(),
                                        duenyo.getDireccion() == null ? "noexistente" : duenyo.getDireccion().toString(),
                                        duenyo.getNif(),
                                        duenyo.getBirthDate() == null ? "noexistente" : duenyo.getBirthDate().toString()
                                });
                    }
                    progressBar1.setValue(i);
                }
                csvPreviewTable.setModel(tablemodel);
                resizeColumnWidth(csvPreviewTable);
                progressBar1.setValue(progressBar1.getMaximum());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                var m = e.getMessage();
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                informe.unlockUI();
            }
        }

        public void resizeColumnWidth(JTable table) {
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
    }

}
