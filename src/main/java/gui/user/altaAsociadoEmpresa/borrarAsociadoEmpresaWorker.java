package gui.user.altaAsociadoEmpresa;

import database.HibernateStartUp;
import database.tables.*;
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

            var NIFseleccionado = (String)asociadoEmpresa.tablaAsociados.getValueAt(
                    asociadoEmpresa.tablaAsociados.getSelectedRow(),
                    1
            );

            var queryClient = session.createQuery("FROM AssociatedStaffEntity WHERE dni = :dni");
            queryClient.setParameter("dni",NIFseleccionado);
            var client = (AssociatedStaffEntity) queryClient.getResultList().get(0);

            var loginQuery = session.createQuery("FROM LoginEntity WHERE asFk = :user");
            loginQuery.setParameter("user",client);
            var login = (LoginEntity) loginQuery.getResultList().get(0);

            var addressQuery = session.createQuery("FROM AddressAssociatedStaffEntity WHERE AssociatedStaffDNI =:dni");
            addressQuery.setParameter("dni",client);
            var address = (AddressAssociatedStaffEntity) addressQuery.getResultList().get(0);

            var relationQuery = session.createQuery("FROM RelationEntity WHERE associatedStaffDni=:dni");
            relationQuery.setParameter("dni",client);
            var relation = (RelationEntity) relationQuery.getResultList().get(0);

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
