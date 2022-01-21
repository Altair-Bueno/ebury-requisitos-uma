package gui.user.altaAsociadoEmpresa;

import database.HibernateStartUp;
import database.tables.AddressAssociatedStaffEntity;
import database.tables.AssociatedStaffEntity;
import database.tables.LoginEntity;
import database.tables.RelationEntity;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class borrarAsociadoEmpresaWorker extends SwingWorker<Void,Void> {
    private altaAsociadoEmpresa asociadoEmpresa;

    public borrarAsociadoEmpresaWorker(altaAsociadoEmpresa a){ this.asociadoEmpresa = a;}

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

    protected Void doInBackground(){
        setPanelEnabled(asociadoEmpresa,false);
        asociadoEmpresa.setCursor(Cursor.getPredefinedCursor((Cursor.WAIT_CURSOR)));

        try(Session session = HibernateStartUp.getSessionFactory().openSession()){
            var transaction = session.beginTransaction();

            var NIFseleccionado = asociadoEmpresa.tablaAsociados.getValueAt(
                    asociadoEmpresa.tablaAsociados.getSelectedRow(),
                    1
            );

            var client = (AssociatedStaffEntity) session.createQuery("FROM AssociatedStaffEntity WHERE dni = '" + NIFseleccionado.toString() + "'").list().get(0);
            var login = (LoginEntity) session.createQuery("FROM LoginEntity WHERE asFk = '" + client.getDni() + "'").list().get(0);
            var address = (AddressAssociatedStaffEntity) session.createQuery("FROM AddressAssociatedStaffEntity WHERE AssociatedStaffDNI = '" + client.getDni() + "'").list().get(0);
            var relation = (RelationEntity) session.createQuery("FROM RelationEntity WHERE associatedStaffDni = '" + client.getDni() + "'").list().get(0);

            session.delete(login);
            session.delete(relation);
            session.delete(address);
            session.delete(client);

            transaction.commit();
            session.close();
        }

        return null;
    }

    protected void done(){
        try {
            get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            asociadoEmpresa.populateTable();
            setPanelEnabled(asociadoEmpresa, true);
            asociadoEmpresa.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            asociadoEmpresa.populateTable();
        }
    }
}
