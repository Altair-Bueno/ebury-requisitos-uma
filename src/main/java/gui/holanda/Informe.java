package gui.holanda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import database.HibernateStartUp;
import database.tables.*;
import jsonTypes.*;
import org.hibernate.Session;


import gui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
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
    private JButton limpiarProductoButton;
    private JButton limpiarClienteButton;


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
        limpiarProductoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                numeroProductoTextField.setText("");
                tipoComboBox.setSelectedIndex(0);
            }
        });
        limpiarClienteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                primerNombreTextField.setText("");
                segundoNombreTextField.setText("");
                ciudadTextField.setText("");
                calleTextField.setText("");
                postalTextField.setText("");
                tercerNombreTextField.setText("");
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
        root.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        root.setPreferredSize(new Dimension(1000, 400));
        scrollPane = new JScrollPane();
        root.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(311, 17), null, 0, false));
        jsonPreviewArea = new JTextArea();
        jsonPreviewArea.setEditable(false);
        jsonPreviewArea.setFocusable(true);
        jsonPreviewArea.setText("");
        scrollPane.setViewportView(jsonPreviewArea);
        final Spacer spacer1 = new Spacer();
        root.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        root.add(buttonPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(311, 34), null, 0, false));
        generarButton = new JButton();
        generarButton.setText("Generar");
        buttonPanel.add(generarButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exportarButton = new JButton();
        exportarButton.setText("Exportar...");
        buttonPanel.add(exportarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filterTabbedPane = new JTabbedPane();
        root.add(filterTabbedPane, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        filtroProductoPanel = new JPanel();
        filtroProductoPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        filterTabbedPane.addTab("Producto", filtroProductoPanel);
        tipoComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("-");
        defaultComboBoxModel1.addElement("Activa");
        defaultComboBoxModel1.addElement("Inactiva");
        defaultComboBoxModel1.addElement("Bloqueada");
        defaultComboBoxModel1.addElement("Cerrada");
        tipoComboBox.setModel(defaultComboBoxModel1);
        tipoComboBox.setToolTipText("Tipo");
        filtroProductoPanel.add(tipoComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        filtroProductoPanel.add(spacer2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        numeroProductoTextField = new JTextField();
        numeroProductoTextField.setToolTipText("Número de producto");
        filtroProductoPanel.add(numeroProductoTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Numero de producto");
        filtroProductoPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Tipo");
        filtroProductoPanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        limpiarProductoButton = new JButton();
        limpiarProductoButton.setText("Limpiar");
        filtroProductoPanel.add(limpiarProductoButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filtroClientePanel = new JPanel();
        filtroClientePanel.setLayout(new GridLayoutManager(10, 2, new Insets(0, 0, 0, 0), -1, -1));
        filterTabbedPane.addTab("Cliente", filtroClientePanel);
        primerNombreTextField = new JTextField();
        primerNombreTextField.setName("name");
        primerNombreTextField.setToolTipText("Primer Nombre");
        filtroClientePanel.add(primerNombreTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        filtroClientePanel.add(spacer3, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        segundoNombreTextField = new JTextField();
        segundoNombreTextField.setName("last_name1");
        segundoNombreTextField.setToolTipText("Segundo Nombre");
        filtroClientePanel.add(segundoNombreTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ciudadTextField = new JTextField();
        ciudadTextField.setName("city");
        ciudadTextField.setText("");
        ciudadTextField.setToolTipText("Ciudad");
        filtroClientePanel.add(ciudadTextField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        calleTextField = new JTextField();
        calleTextField.setName("street");
        calleTextField.setToolTipText("Calle");
        filtroClientePanel.add(calleTextField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        postalTextField = new JTextField();
        postalTextField.setName("postal_code");
        postalTextField.setToolTipText("Código Postal");
        filtroClientePanel.add(postalTextField, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nombreLabel = new JLabel();
        nombreLabel.setText("Nombre completo");
        filtroClientePanel.add(nombreLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tercerNombreTextField = new JTextField();
        tercerNombreTextField.setName("last_name2");
        filtroClientePanel.add(tercerNombreTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Nombre");
        filtroClientePanel.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Apellido 1");
        filtroClientePanel.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Apellido 2");
        filtroClientePanel.add(label5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Ciudad");
        filtroClientePanel.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Calle");
        filtroClientePanel.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Codigo Postal");
        filtroClientePanel.add(label8, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        limpiarClienteButton = new JButton();
        limpiarClienteButton.setText("Limpiar");
        filtroClientePanel.add(limpiarClienteButton, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        direccionLabel = new JLabel();
        direccionLabel.setText("Dirección");
        filtroClientePanel.add(direccionLabel, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        healthCheckPanel = new JPanel();
        healthCheckPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        filterTabbedPane.addTab("HealthCheck", healthCheckPanel);
        resultadoCheck = new JLabel();
        resultadoCheck.setText("");
        healthCheckPanel.add(resultadoCheck, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

    private class GenerarWorker extends SwingWorker<String, Void> {
        Informe informe;

        public GenerarWorker(Informe informe) {
            this.informe = informe;
        }

        @Override
        protected String doInBackground() throws Exception {
            jsonPreviewArea.setText("Cargando información...");
            lockUI();
            return switch (filterTabbedPane.getSelectedIndex()) {
                case 0 -> producto();
                case 1 -> cliente();
                case 2 -> healthcheck();
                default -> throw new RuntimeException("Invalid tabbed panel selected");
            };
        }

        private String healthcheck() {
            try (Session session = HibernateStartUp.getSessionFactory().openSession()) {
                session.createQuery("FROM EburyAccountEntity");
            } catch (Exception e) {
                return "STATUS: ERROR.";
            }
            return "STATUS: CONNECTION OK.";
        }

        private String cliente() {
            String queryclient, queryaddress, queryebaccount, result = "";
            JsonBuilder jsonres = new JsonBuilder();

            queryclient = "FROM ClientEntity ";

            JTextField[] campos = new JTextField[]{
                    primerNombreTextField,
                    segundoNombreTextField,
                    tercerNombreTextField,
                    ciudadTextField,
                    calleTextField,
                    postalTextField
            };

            try (Session session = HibernateStartUp.getSessionFactory().openSession()) {

                // Clients query builder
                for (int i = 0; i < 3; i++) {
                    var texto = campos[i].getText();
                    var name = campos[i].getName();

                    if (!texto.equals("")) {
                        if (i == 0) {
                            queryclient += "WHERE";
                        } else {
                            queryclient += " AND";
                        }
                        queryclient += " " + name + " LIKE ('%" + texto + "%')";
                    }
                }

                List<ClientEntity> clienfil = (List<ClientEntity>) session.createQuery(queryclient).list();

                var list = new ArrayList<Client>();

                for(ClientEntity cl : clienfil){
                    // Cuentas del cliente
                    List<EburyAccountEntity> prodclient = (List<EburyAccountEntity>) session.createQuery("FROM EburyAccountEntity WHERE owner = " + cl.getId()).list();

                    List<ProductClient> productos = new ArrayList<>();
                    for(EburyAccountEntity ea : prodclient){
                        var producto = new ProductClient(
                                ea.getAccounttype(),
                                ea.getBankAccount().getIban(),
                                ea.getStatus());
                        productos.add(producto);
                    }

                    // Direcciones del cliente
                    List<AddressEntity> dirclien = (List<AddressEntity>) session.createQuery("FROM AddressEntity WHERE clientId = " + cl.getId()).list();

                    List<Address> addresses = new ArrayList<>();
                    for(AddressEntity ae : dirclien){
                        var address = new Address(
                                ae.getCity(),
                                ae.getStreet(),
                                ae.getNumber(),
                                ae.getPostalCode(),
                                ae.getCountry());
                        addresses.add(address);
                    }

                    // Nombre
                    list.add(new Client(
                            productos,
                            cl.getStatus().equals("Active"),
                            (cl.getBirthDate() == null ? "" : cl.getBirthDate().toString()),
                            new Name(cl.getName(), cl.getLastName1()),
                            addresses
                    ));

                }

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                var clients = new Clients(list);
                return gson.toJson(clients);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        private String producto() {
            var numProd = numeroProductoTextField.getText();
            var statusIndex = tipoComboBox.getSelectedIndex();
            var statusCuenta = switch (statusIndex) {
                case 0 -> "";
                case 1 -> "Active";
                case 2 -> "Inactive";
                case 3 -> "Blocked";
                case 4 -> "Closed";
                default -> throw new IllegalStateException("Unexpected value: " + statusIndex);
            };


            try (Session session = HibernateStartUp.getSessionFactory().openSession()) {
                StringBuilder result = new StringBuilder();
                List<EburyAccountEntity> listaCuentas = null;
                if (statusCuenta.equals("") && numProd.equals("")) { //No se ha aplicado ningún filtro:
                    listaCuentas = (List<EburyAccountEntity>) session.createQuery("FROM EburyAccountEntity").list();
                }

                if (!statusCuenta.equals("") && numProd.equals("")) { //Se filtra por tipo de cuenta:
                    listaCuentas = (List<EburyAccountEntity>) session.createQuery("FROM EburyAccountEntity WHERE status = '" + statusCuenta + "'").list();
                }

                if (statusCuenta.equals("") && !numProd.equals("")) { //Se filtra por número de IBAN
                    listaCuentas = (List<EburyAccountEntity>) session.createQuery("FROM EburyAccountEntity WHERE bankAccount = '" + numProd + "'").list();

                }

                if (!statusCuenta.equals("") && !numProd.equals("")) { //Se filtra por tipo de cuenta y numero de IBAN
                    listaCuentas = (List<EburyAccountEntity>) session.createQuery("FROM EburyAccountEntity WHERE bankAccount = '" + numProd + "' AND status = '" + statusCuenta + "'").list();
                }

                var list = new ArrayList<Product>();
                for (EburyAccountEntity ac : listaCuentas) {
                    var listAddress = new ArrayList<Address>();
                    listAddress.add(new Address(
                            ac.getOwner().getDireccion().getCity(),
                            ac.getOwner().getDireccion().getStreet(),
                            ac.getOwner().getDireccion().getNumber(),
                            ac.getOwner().getDireccion().getPostalCode(),
                            ac.getOwner().getDireccion().getCountry()
                    ));
                    var listAccountHolder = new ArrayList<AccountHolder>();
                    listAccountHolder.add(new AccountHolder(
                            ac.getStatus().equals("Active"),
                            "Individual",
                            new Name(
                                    ac.getOwner().getName(),
                                    ac.getOwner().getLastName1()
                            ),
                            listAddress
                    ));
                    Product p = new Product(
                            listAccountHolder,
                            ac.getAccounttype(),
                            ac.getBankAccount().getIban(),
                            ac.getStatus(),
                            ac.getRegisterdate() == null ? null : ac.getRegisterdate().toString(),
                            ac.getClosedate() == null ? null : ac.getClosedate().toString()
                    );
                    list.add(p);
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                var products = new Products(list);
                return gson.toJson(products);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(informe, "No hay información para mostrar o ha habido algún error.");
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
