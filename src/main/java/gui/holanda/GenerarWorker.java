package gui.holanda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.ClientEntity;
import database.tables.EburyAccountEntity;
import jsonTypes.*;
import org.hibernate.Session;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

class GenerarWorker extends SwingWorker<String, Void> {
    Informe informe;

    public GenerarWorker(Informe informe) {
        this.informe = informe;
    }

    @Override
    protected String doInBackground() throws Exception {
        informe.jsonPreviewArea.setText("Cargando información...");
        informe.lockUI();
        return switch (informe.filterTabbedPane.getSelectedIndex()) {
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
        String queryclient;
        Boolean filtrocliente = false;

        queryclient = "FROM ClientEntity ";

        JTextField[] campos = new JTextField[]{
                informe.primerNombreTextField,
                informe.segundoNombreTextField,
                informe.tercerNombreTextField,
                informe.ciudadTextField,
                informe.calleTextField,
                informe.postalTextField
        };

        try (Session session = HibernateStartUp.getSessionFactory().openSession()) {

            List<ClientEntity> clienfil = (List<ClientEntity>) session.createQuery(queryclient).list();
            List<ClientEntity> aux = new ArrayList<>(), aux2 = new ArrayList<>(), aux3 = new ArrayList<>();

            var nombref = campos[0].getText() == null ? "" : campos[0].getText().toUpperCase(Locale.ROOT);
            var last1f = campos[1].getText() == null ? "" : campos[1].getText().toUpperCase(Locale.ROOT);
            var last2f = campos[2].getText() == null ? "" : campos[2].getText().toUpperCase(Locale.ROOT);
            var ciudadf = campos[3].getText() == null ? "" : campos[3].getText().toUpperCase(Locale.ROOT);
            var callef = campos[4].getText() == null ? "" : campos[4].getText().toUpperCase(Locale.ROOT);
            var cpf = campos[5].getText() == null ? "" : campos[5].getText().toUpperCase(Locale.ROOT);

            var filtronombre = !nombref.equals("");
            var filtroapellido1 = !last1f.equals("");
            var filtroapellido2 = !last2f.equals("");
            var filtrociudad = !ciudadf.equals("");
            var filtrocalle = !callef.equals("");
            var filtrocp = !cpf.equals("");

            if (!(filtronombre || filtroapellido1 || filtroapellido2 || filtrociudad || filtrocalle || filtrocp)) {
                aux.addAll(clienfil);
            } else {
                for (ClientEntity cl : clienfil) {
                    boolean add = false;

                    var nombrecl = cl.getName() == null ? "" : cl.getName().toUpperCase(Locale.ROOT);
                    //add = filtronombre && nombrecl.contains(nombref);
                    var last1cl = cl.getLastName1() == null ? "" : cl.getLastName1().toUpperCase(Locale.ROOT);
                    //add = filtroapellido1 && last1cl.contains(last1f);
                    var last2cl = cl.getLastName2() == null ? "" : cl.getLastName2().toUpperCase(Locale.ROOT);
                    //add = filtroapellido2 && last2cl.contains(last2f);
                    if (filtronombre ^ filtroapellido1 ^ filtroapellido2) {
                        add = nombrecl.contains(nombref) ^ last1cl.contains(last1f) ^ last2cl.contains(last2f);
                    }

                    for (AddressEntity a : cl.getDireccion()) {
                        var ciudadc = a.getCity().toUpperCase(Locale.ROOT);
                        //add = filtrociudad && ciudadc.contains(ciudadf);
                        var callec = a.getStreet().toUpperCase(Locale.ROOT);
                        //add = filtrocalle && callec.contains(callef);
                        var cpc = a.getPostalCode().toUpperCase(Locale.ROOT);
                        //add = filtrocp && cpc.contains(cpf);

                        if (filtrociudad ^ filtrocalle ^ filtrocp) {
                            add = ciudadc.contains(ciudadf) ^ callec.contains(callef) ^ cpc.contains(cpf);
                            System.out.println(ciudadc + ": " + add);
                        }
                        if (add) {
                            aux.add(cl);
                        }
                    }
                }
            }

            if (aux.isEmpty()) {
                JOptionPane.showMessageDialog(informe, "No hay información que corresponda con los filtros.");
                return "";
            } else {//
                // --------------------------------------------- //
                var list = new ArrayList<Client>();

                for (ClientEntity cl : aux) {
                    // Cuentas del cliente
                    List<EburyAccountEntity> prodclient = (List<EburyAccountEntity>) session.createQuery("FROM EburyAccountEntity WHERE owner = " + cl.getId()).list();

                    List<ProductClient> productos = new ArrayList<>();
                    for (EburyAccountEntity ea : prodclient) {
                        var producto = new ProductClient(
                                ea.getAccounttype(),
                                ea.getBankAccount().getIban(),
                                ea.getStatus());
                        productos.add(producto);
                    }

                    // Direcciones del cliente
                    List<AddressEntity> dirclien = (List<AddressEntity>) session.createQuery("FROM AddressEntity WHERE clientId = " + cl.getId()).list();

                    List<Address> addresses = new ArrayList<>();
                    for (AddressEntity ae : dirclien) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String producto() {
        var numProd = informe.numeroProductoTextField.getText();
        var statusIndex = informe.tipoComboBox.getSelectedIndex();
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
            if (listaCuentas.isEmpty()) {
                JOptionPane.showMessageDialog(informe, "No hay información que corresponda con los filtros.");
                return "";
            } else {

                var list = new ArrayList<Product>();
                for (EburyAccountEntity ac : listaCuentas) {
                    var listAddress = new ArrayList<Address>();
                    for (AddressEntity dir : ac.getOwner().getDireccion()) {
                        listAddress.add(new Address(
                                dir.getCity(),
                                dir.getStreet(),
                                dir.getNumber(),
                                dir.getPostalCode(),
                                dir.getCountry()));
                    }

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
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(informe, "No hay información para mostrar o ha habido algún error.");
            return "";
        }
    }

    @Override
    protected void done() {
        try {
            informe.jsonPreviewArea.setText(get());
        } catch (InterruptedException | ExecutionException e) {
            var m = e.getMessage();
            JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
        } finally {
            informe.unlockUI();
        }
    }
}
