package gui.alemania;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import database.HibernateStartUp;
import database.tables.EburyAccountEntity;
import gui.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Informe extends JPanel implements Frame {
    private JButton primerInformeButton;
    private JTable csvPreviewTable;
    private JButton informeSemanalButton;
    private JButton enviarSFTPButton;
    private JButton exportarButton;
    private JScrollPane scrollPane;
    private JPanel root;

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
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.enviarSFTPButton.setEnabled(false);
        this.informeSemanalButton.setEnabled(false);
        this.primerInformeButton.setEnabled(false);
        this.exportarButton.setEnabled(false);

    }

    private void unlockUI() {
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
            var progress = new JProgressBar();
            frame.add(progress);
            frame.setUndecorated(true);
            frame.pack();
            frame.setLocationRelativeTo(informe);
            frame.setVisible(true);
            generateCSV(frame, progress);
            return null;
        }

        private void generateCSV(JFrame frame, JProgressBar progress) {
            var model = csvPreviewTable.getModel();
            progress.setMaximum(model.getRowCount());
            try (var csv = new FileWriter(file)) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    csv.write(model.getColumnName(i) + ",");
                }
                csv.write("\n");
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        csv.write(model.getValueAt(i, j).toString() + ",");
                    }
                    progress.setValue(i);
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

    private class GenerarPrimerInformeWorker extends SwingWorker<List<Object>, Void> {
        Informe informe;

        public GenerarPrimerInformeWorker(Informe informe) {
            this.informe = informe;
        }

        @Override
        protected List<Object> doInBackground() throws Exception {
            lockUI();
            try (var session = HibernateStartUp.getSessionFactory().openSession()) {
                // TODO querry
                return session.createQuery("from BankAccountEntity, EburyAccountEntity").list();
            }
        }

        @Override
        protected void done() {
            try {
                var result = get();
                csvPreviewTable.removeAll();
                for (var i : result) {
                    // TODO insertar datos dentro de la tabla
                    //var c = new TableColumn(i);
                    //csvPreviewTable.addColumn(c);
                }
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                var m = e.getMessage();
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                informe.unlockUI();
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
            lockUI();
            try (var session = HibernateStartUp.getSessionFactory().openSession()) {
                // TODO querry
                var list = session.createQuery("from EburyAccountEntity").list();
                return list;
            }
        }

        @Override
        protected void done() {
            try {
                var result = get();
                csvPreviewTable.removeAll();
                var tablemodel = new DefaultTableModel(new String[]{"IBAN", "Nombre", "Dirección", "NIF", "Fecha Nacimiento/Creación"}, 0);

                for (var i : result) {
                    // TODO insertar datos dentro de la tabla
                    var cuentabanc = i.getBankAccount();
                    var duenyo = i.getOwner();
                    tablemodel.addRow(
                            new Object[]{
                                    cuentabanc.getIban(),
                                    duenyo.fullName(),
                                    duenyo.getDireccion() == null ? "null" : duenyo.getDireccion().toString(),
                                    duenyo.getNif(),
                                    duenyo.getBirthDate() == null ? "null" : duenyo.getBirthDate().toString()
                            });
                    csvPreviewTable.setModel(tablemodel);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                var m = e.getMessage();
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                informe.unlockUI();
            }
        }
    }

}
