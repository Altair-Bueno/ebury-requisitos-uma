package gui.holanda;

import database.HibernateStartUp;
import database.tables.AssociatedStaffEntity;
import database.tables.BankAccountEntity;
import database.tables.EburyAccountEntity;
import org.hibernate.Session;


import gui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private JTextField postalTextField;
    private JTextField tercerNombreTextField;
    private JLabel nombreLabel;
    private JLabel direccionLabel;
    private JLabel resultadoCheck;


    public Informe() {
        add(root);
        exportarButton.addActionListener(e -> {
            var filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.setMultiSelectionEnabled(false);
            var result = filechooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                var folder = filechooser.getSelectedFile();
                var file = new File(folder, "export.json");
                var guardar = new GuardarJSONWorker(this, file);
                guardar.execute();
            }
        });
        ActionListener actionListener = e -> {
            var generar = new GenerarWorker(this);
            generar.execute();
        };
        generarButton.addActionListener(actionListener);
        numeroProductoTextField.addActionListener(actionListener);
        numeroProductoTextField.addActionListener(actionListener);
        primerNombreTextField.addActionListener(actionListener);
        segundoNombreTextField.addActionListener(actionListener);
        tercerNombreTextField.addActionListener(actionListener);
        ciudadTextField.addActionListener(actionListener);
        calleTextField.addActionListener(actionListener);
        postalTextField.addActionListener(actionListener);
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
        tercerNombreTextField.setEnabled(false);
        ciudadTextField.setEnabled(false);
        calleTextField.setEnabled(false);
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
        tercerNombreTextField.setEnabled(true);
        ciudadTextField.setEnabled(true);
        calleTextField.setEnabled(true);
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

    private class GenerarWorker extends SwingWorker<String, Void> {
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
            return "STATUS: CONNECTION OK.";
        }

        private String cliente() {
            JTextField[] campos = new JTextField[]{
                    primerNombreTextField,
                    segundoNombreTextField,
                    tercerNombreTextField,
                    ciudadTextField,
                    calleTextField,
                    postalTextField
                    };
            String [] filtersApplied = new String[6]; // "null" if not applied, value otherwise

            for(int i = 0; i<6; i++){
                var texto = campos[i].getText();
                var name = campos[i].getName();
                filtersApplied[i] = texto.equals("") ? "null" : (name + " = '" + texto + "'");
            }
            try(Session session = HibernateStartUp.getSessionFactory().openSession()){
                for(int i = 0; i<6; i++){
                    if(!filtersApplied[i].equals("null")){

                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }


        private String producto() {
            var numProd = numeroProductoTextField.getText();
            var statusIndex = tipoComboBox.getSelectedIndex();
            var statusCuenta = "";
            switch (statusIndex) {
                case 0 -> statusCuenta = "";
                case 1 -> statusCuenta = "Active";
                case 2 -> statusCuenta =  "Inactive";
                case 3 -> statusCuenta = "Blocked";
                case 4 -> statusCuenta = "Closed";
            }
            try(Session session = HibernateStartUp.getSessionFactory().openSession()){
                StringBuilder result = new StringBuilder();
                if(statusCuenta.equals("") && numProd.equals("")){ //No se ha aplicado ningún filtro:
                    List<EburyAccountEntity> listaCuentas = (List<EburyAccountEntity>)session.createQuery("FROM EburyAccountEntity").list();
                    for(EburyAccountEntity a : listaCuentas){
                        result.append(a.getInfo()).append(" \n");
                    }
                }

                if(!statusCuenta.equals("") && numProd.equals("")){ //Se filtra por tipo de cuenta:
                    List<EburyAccountEntity> listaCuentas = (List<EburyAccountEntity>)session.createQuery("SELECT bankAccount FROM EburyAccountEntity WHERE status = '" + statusCuenta + "'").list();
                    for(EburyAccountEntity a : listaCuentas){
                        result.append(a.getInfo()).append(" \n");
                    }
                }

                if(statusCuenta.equals("") && !numProd.equals("")){ //Se filtra por número de IBAN
                    List<EburyAccountEntity> listaCuentas = (List<EburyAccountEntity>)session.createQuery("FROM EburyAccountEntity WHERE bankAccount = '" + numProd + "'").list();

                    for(EburyAccountEntity a : listaCuentas){
                        result.append(a.getInfo()).append(" \n");
                    }
                }

                if(!statusCuenta.equals("") && !numProd.equals("")){ //Se filtra por tipo de cuenta y numero de IBAN
                    List<EburyAccountEntity> listaCuentas = (List<EburyAccountEntity>)session.createQuery("FROM EburyAccountEntity WHERE bankAccount = '" + numProd + "' AND status = '" + statusCuenta + "'").list();
                    for(EburyAccountEntity a : listaCuentas){
                        result.append(a.getInfo()).append(" \n");
                    }

                }
                return result.toString();
            } catch (Exception e){
                return "";
            }
        }

        @Override
        protected void done() {
            try {
                jsonPreviewArea.setText(get());
            } catch (InterruptedException | ExecutionException e) {
                var m = e.getMessage();
                JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
            } finally {
                unlockUI();
            }
        }
    }

    private class GuardarJSONWorker extends SwingWorker<Void, Void> {
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
            try (var json = new FileWriter(file)) {
                json.write(jsonPreviewArea.getText());
                progress.setValue(100);
                var m = "Correcto";
                JOptionPane.showMessageDialog(frame, m, m, JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                var m = ex.getMessage();
                JOptionPane.showMessageDialog(frame, m, m, JOptionPane.ERROR_MESSAGE);
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
