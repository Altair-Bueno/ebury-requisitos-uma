package gui.user.altaEmpresa;

import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.ClientEntity;
import database.tables.LoginEntity;
import database.types.Rol;
import database.types.Status;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.tables.ClientEntity;

public class AltaEmpresaWorker extends SwingWorker<Void, Void> {
    AltaEmpresa empresa;


    public AltaEmpresaWorker(AltaEmpresa empresa){
        this.empresa = empresa;
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
        setPanelEnabled(empresa, false);
        empresa.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try(Session session = HibernateStartUp.getSessionFactory().openSession()){
            var transaction = session.beginTransaction();
            var login = new LoginEntity(
                    empresa.tCIF.getText(),
                    empresa.tPassword.getText(),
                    Rol.User,
                    null);
            var date = new java.util.Date();
            var client = new ClientEntity(
                    Status.Active,empresa.tCIF.getText(),
                    empresa.tNombre.getText(),
                    new java.sql.Date(date.getTime())
            );
            var address = new AddressEntity(
                    client,
                    empresa.tCalle.getText(),
                    empresa.tNumero.getText(),
                    empresa.tCiudad.getText(),
                    empresa.tCP.getText(),
                    empresa.countries.get(empresa.cPais.getSelectedItem().toString())
            );

            session.persist(client);
            session.persist(address);
            session.persist(login);

            transaction.commit();
            session.close();

            var m = "Registro completado con éxito.";
            if(JOptionPane.showOptionDialog(null, m, "Éxito.", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION){
                empresa.back2Login();
            }
        } catch (Exception ex){
            var m = "Ha ocurrido un error en la operación de registro. Inténtelo de nuevo.";
            JOptionPane.showMessageDialog(this.empresa, m, m, JOptionPane.WARNING_MESSAGE);
        }

        return null;
    }

    @Override
    protected void done() {
        setPanelEnabled(empresa,true);
        empresa.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
