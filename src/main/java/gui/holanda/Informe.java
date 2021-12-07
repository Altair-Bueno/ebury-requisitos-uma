package gui.holanda;

import gui.Frame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Informe extends JPanel implements Frame {
    private JPanel root;
    private JTextArea jsonPreviewArea;
    private JButton generarButton;
    private JButton exportarButton;
    private JTabbedPane filterTabbedPane;
    private JPanel filtroProductoPanel;
    private JPanel filtroClientePanel;
    private JPanel healthCheckPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JComboBox tipoComboBox;
    private JTextField numeroProductoTextField;
    private JTextField primerNombreTextField;
    private JTextField segundoNombreTextField;
    private JTextField ciudadTextField;
    private JTextField calleTextField;
    private JTextField numeroTextField;
    private JTextField postalTextField;
    private JLabel nombreLabel;
    private JLabel direccionLabel;

    public Informe() {
        add(root);
        exportarButton.addActionListener(e->{
            var filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.setMultiSelectionEnabled(false);
            var result = filechooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                var folder = filechooser.getSelectedFile();
                var file = new File(folder,"export.json");
                var guardar = new GuardarJSONWorker(this,file);
                guardar.execute();
            }
        });
    }
    private void lockUI() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        generarButton.setEnabled(false);
        exportarButton.setEnabled(false);
        filterTabbedPane.setEnabled(false);
        tipoComboBox.setEnabled(false);
        numeroProductoTextField.setEnabled(false);
        primerNombreTextField.setEnabled(false);
        segundoNombreTextField.setEnabled(false);
        ciudadTextField.setEnabled(false);
        calleTextField.setEnabled(false);
        numeroTextField.setEnabled(false);
        postalTextField.setEnabled(false);
    }
    private void unlockUI() {
        setCursor(Cursor.getDefaultCursor());
        generarButton.setEnabled(true);
        exportarButton.setEnabled(true);
        filterTabbedPane.setEnabled(true);
        tipoComboBox.setEnabled(true);
        numeroProductoTextField.setEnabled(true);
        primerNombreTextField.setEnabled(true);
        segundoNombreTextField.setEnabled(true);
        ciudadTextField.setEnabled(true);
        calleTextField.setEnabled(true);
        numeroTextField.setEnabled(true);
        postalTextField.setEnabled(true);
    }
    @Override
    public String getTitleBarName() {
        // TODO
        return "Informe Holanda";
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }

    private class GenerarWorker extends SwingWorker<String,Void> {
        Informe informe;

        public GenerarWorker(Informe informe) {
            this.informe = informe;
        }

        @Override
        protected String doInBackground() throws Exception {
            lockUI();
            return switch (filterTabbedPane.getSelectedIndex()) {
                case 0 -> producto();
                case 1 -> cliente();
                case 2 -> healthcheck();
                default -> throw new RuntimeException("Invalid tabbed panel selected");
            };
        }

        private String healthcheck() {
            // TODO
            return null;
        }

        private String cliente() {
            // TODO
            return null;
        }

        private String producto() {
            // TODO
            return null;
        }

        @Override
        protected void done() {
            try {
                jsonPreviewArea.setText(get());
            } catch (InterruptedException | ExecutionException e) {
                var m = e.getMessage();
                JOptionPane.showMessageDialog(informe,m,m,JOptionPane.ERROR_MESSAGE);
            }finally {
                unlockUI();
            }
        }
    }

    private class GuardarJSONWorker extends SwingWorker<Void,Void> {
        Informe informe;
        File file;

        public GuardarJSONWorker(Informe informe, File file) {
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
            try (var json = new FileWriter(file)){
                json.write(jsonPreviewArea.getText());
                progress.setValue(100);
                var m = "Correcto";
                JOptionPane.showMessageDialog(frame,m,m,JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                var m = ex.getMessage();
                JOptionPane.showMessageDialog(frame,m,m,JOptionPane.ERROR_MESSAGE);
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
}
