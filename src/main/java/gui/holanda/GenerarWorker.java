package gui.holanda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.ClientEntity;
import database.tables.EburyAccountEntity;
import database.types.Status;
import jsonTypes.*;
import org.hibernate.Session;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
            var result = session
                    .createQuery("FROM EburyAccountEntity")
                    .getResultList();
            assert result != null;
        } catch (Exception e) {
            return "STATUS: ERROR.";
        }
        return "STATUS: CONNECTION OK.";
    }
    private String cliente() {
        var availableFilters = new String [] {
                informe.primerNombreTextField.getText(),
                informe.segundoNombreTextField.getText(),
                informe.tercerNombreTextField.getText(),
                informe.ciudadTextField.getText(),
                informe.calleTextField.getText(),
                informe.postalTextField.getText(),
        };
        var list = new ArrayList<Predicate<String>>();
        for (var availableFilter : availableFilters)
            list.add(
                    availableFilter.equals("")?
                    (a -> true):
                    (a-> a.contains(availableFilter))
            );

        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
                var clientEntityStream = (Stream<ClientEntity>) session.createQuery("from ClientEntity")
                        .getResultList()
                        .stream();
                var data = clientEntityStream
                        // Primer nombre
                        .filter(a -> list.get(0).test(a.getName()))
                        // Segundo nombre
                        .filter(a -> list.get(1).test(a.getLastName1()))
                        // Tercer nombre
                        .filter(a -> list.get(2).test(a.getLastName2()))
                        // Ciudad calle y postal
                        .filter(a -> {
                            var direcciones = a.getDireccion();
                            var max = direcciones
                                    .stream()
                                    .max(Comparator.comparingInt(AddressEntity::getId))
                                    .get();
                            return list.get(3).test(max.getCity()) &&
                                    list.get(4).test(max.getStreet()) &&
                                    list.get(5).test(max.getPostalCode());
                        })
                        .map(clientEntity -> clientEntityToClient(clientEntity,session))
                        .toList();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                var clients = new Clients(data);
                return gson.toJson(clients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private Client clientEntityToClient(ClientEntity clientData,Session session) {
        var querry = session.createQuery("from EburyAccountEntity where owner = :owner");
        querry.setParameter("owner",clientData);
        var querryStream = (Stream<EburyAccountEntity>)querry.stream();
        var products = querryStream
                .map(account ->
                        new ProductClient(account.getAccounttype(),
                                account.getBankAccount().getIban(),
                                account.getStatus())
                ).toList();

        Boolean activeCustomer = clientData.getStatus() == Status.Active;
        String dateOfBirth =clientData.getBirthDate() == null ? null : clientData.getBirthDate().toString();
        Name name = new Name(clientData.getName(),clientData.getLastName1());
        List<Address> address = addressEntityToAddress(clientData.getDireccion());
        return new Client(products,activeCustomer,dateOfBirth,name,address);
    }

    private List<Address> addressEntityToAddress(List<AddressEntity> addressEntities) {
        return addressEntities
                .stream()
                .map(d -> new Address(
                        d.getCity(),
                        d.getStreet(),
                        d.getNumber(),
                        d.getPostalCode(),
                        d.getCountry()
                ))
                .toList();
    }

    private String producto() {
        var textoNumProducto = informe.numeroProductoTextField.getText();
        Predicate<String> filtroNumProducto = textoNumProducto.equals("") ?
                (a -> true):
                (a -> a.contains(textoNumProducto));
        var indexStatus = informe.tipoComboBox.getSelectedIndex();
        var selectedStatus = switch (indexStatus) {
            case 1 -> Status.Active;
            case 2 -> Status.Inactive;
            case 3 -> Status.Blocked;
            case 4 -> Status.Closed;
            default -> null;
        };
        Predicate<Status> filtroStatus = indexStatus == 0 ?
                (a -> true):
                (a -> a == selectedStatus);

        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
            var sessionStream = (Stream<EburyAccountEntity>) session.createQuery("from EburyAccountEntity")
                    .getResultList()
                    .stream();
            var list = sessionStream
                    .filter(a -> filtroNumProducto.test(a.getBankAccount().getIban()))
                    .filter(a -> filtroStatus.test(a.getStatus()))
                    .map(a -> eburyAccountToProduct(a))
                    .toList();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            var products = new Products(list);
            return gson.toJson(products);
        }
    }

    private Product eburyAccountToProduct(EburyAccountEntity eburyAccount){
        List<AccountHolder> listAccountHolder = new ArrayList<>();
        listAccountHolder.add(new AccountHolder(
                eburyAccount.getOwner().getStatus() == Status.Active,
                "Individual",
                new Name(eburyAccount.getOwner().getName(),eburyAccount.getOwner().getLastName1()),
                addressEntityToAddress(eburyAccount.getOwner().getDireccion())
        ));
        return new Product(
                listAccountHolder,
                eburyAccount.getAccounttype(),
                eburyAccount.getBankAccount().getIban(),
                eburyAccount.getStatus(),
                eburyAccount.getRegisterdate() == null ? null : eburyAccount.getRegisterdate().toString(),
                eburyAccount.getClosedate() == null ? null : eburyAccount.getClosedate().toString()
        );
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
