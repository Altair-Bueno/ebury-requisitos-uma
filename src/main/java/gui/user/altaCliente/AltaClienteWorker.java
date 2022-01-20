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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AltaClienteWorker extends SwingWorker<Void, Void> {
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
    protected Void doInBackground() throws Exception {
        setPanelEnabled(cliente, false);
        cliente.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try(Session session = HibernateStartUp.getSessionFactory().openSession()){
            var transaction = session.beginTransaction();

            var format = new SimpleDateFormat("dd-MM-yyyy");
            var inputFormat = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputFormat.parse(cliente.fMM.getSelectedItem().toString()));
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM");
            var parsed = format.parse(
                    String.format("%02d", Integer.parseInt(cliente.fDD.getValue().toString()))
                    + "-" + outputFormat.format(cal.getTime())
                    + "-" + cliente.fYYYY.getValue().toString()
            );

            var date = new java.util.Date();
            var client = new ClientEntity(
                    Status.Active,
                    cliente.tNIF.getText(),
                    cliente.tNombre.getText() + " " + cliente.tSegundoNombre.getText(),
                    cliente.tPrimerAp.getText(),
                    cliente.tSegundoAp.getText(),
                    new java.sql.Date(parsed.getTime()),
                    new java.sql.Date(date.getTime())
            );
            var login = new LoginEntity(cliente.tNIF.getText(), cliente.tPassword.getText(), Rol.User, null);

            var address = new AddressEntity(
                    client,
                    cliente.tCalle.getText(),
                    cliente.tNumero.getText(),
                    cliente.tCiudad.getText(),
                    cliente.tCP.getText(),
                    cliente.countries.get(cliente.cPais.getSelectedItem().toString())
            );
            session.save(client);
            session.save(address);
            session.save(login);

            transaction.commit();
            session.close();

            var m = "Registro completado con éxito.";
            if(JOptionPane.showOptionDialog(null, m, "Éxito.", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION){
                cliente.back2Login();
            }
        } catch (Exception e){
            var m = "Ha ocurrido un error en la operación de registro. Inténtelo de nuevo.";
            JOptionPane.showMessageDialog(this.cliente, m, m, JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            setPanelEnabled(cliente, true);
            cliente.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }
}
