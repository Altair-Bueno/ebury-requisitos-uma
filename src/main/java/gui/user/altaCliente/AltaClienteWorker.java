package gui.user.altaCliente;

import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.ClientEntity;
import database.tables.LoginEntity;
import database.types.Rol;
import database.types.Status;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AltaClienteWorker extends SwingWorker<Boolean, Void> {
    private AltaCliente cliente;

    public AltaClienteWorker(AltaCliente ac){
        cliente = ac;
    }

    private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        setPanelEnabled(cliente, false);
        cliente.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try(Session session = HibernateStartUp.getSessionFactory().openSession()){
            var transaction = session.beginTransaction();

            var format = new SimpleDateFormat("dd-MM-yyyy");
            var parsed = format.parse(cliente.tFechaNac.getText());
            var date = new java.util.Date();
            var login = new LoginEntity(cliente.tNIF.getText(), cliente.tPassword.getText(), Rol.User, null);
            var client = new ClientEntity(
                    Status.Active,
                    cliente.tNIF.getText(),
                    cliente.tNombre.getText() + " " + cliente.tSegundoNombre.getText(),
                    cliente.tPrimerAp.getText(),
                    cliente.tSegundoAp.getText(),
                    new java.sql.Date(parsed.getTime()),
                    new java.sql.Date(date.getTime())
            );

            var address = new AddressEntity(
                    client,
                    cliente.tCalle.getText(),
                    cliente.tNumero.getText(),
                    cliente.tCiudad.getText(),
                    cliente.tCP.getText(),
                    cliente.tPais.getText()
            );
            session.save(client);
            session.save(address);
            session.save(login);

            //session.persist(address);
            //session.persist(client);
            //session.persist(login);

            transaction.commit();
            session.close();
        }
        return true;
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setPanelEnabled(cliente, true);
        cliente.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
