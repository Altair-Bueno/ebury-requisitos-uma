package gui.user.altaAsociadoEmpresa;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import database.HibernateStartUp;
import database.tables.*;
import database.types.Rol;
import org.hibernate.Session;

public class altaAsociadoEmpresaWorker extends SwingWorker<Void, Void> {
    private altaAsociadoEmpresa asociadoEmpresa;

    public altaAsociadoEmpresaWorker(altaAsociadoEmpresa aE){
        asociadoEmpresa = aE;
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


    protected Void doInBackground() throws Exception {
        setPanelEnabled(asociadoEmpresa,false);
        asociadoEmpresa.setCursor(Cursor.getPredefinedCursor((Cursor.WAIT_CURSOR)));

        try(Session session = HibernateStartUp.getSessionFactory().openSession()){
            var transaction = session.beginTransaction();

            var format = new SimpleDateFormat("dd-MM-yyyy");
            var inputFormat = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputFormat.parse(asociadoEmpresa.fMM.getSelectedItem().toString()));
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM");

            var parsed = format.parse(
                    String.format("%02d", (Integer) asociadoEmpresa.fDD.getSelectedItem())
                            + "-" + outputFormat.format(cal.getTime())
                            + "-" + asociadoEmpresa.fYYYY.getSelectedItem().toString()
            );

            var date = new java.util.Date();
            var login = new LoginEntity(asociadoEmpresa.tNIF.getText(), asociadoEmpresa.pwd.getText(), Rol.User, null);


            var asociadoEm = new AssociatedStaffEntity(
                    asociadoEmpresa.tNIF.getText(),
                    nombre,
                    asociadoEmpresa.primerAp.getText(),
                    asociadoEmpresa.segundoAp.getText(),
                    asociadoEmpresa.lTipo.getSelectedItem().toString()
            );

            var address = new AddressAssociatedStaffEntity(
                    asociadoEm,
                    asociadoEmpresa.calle.getText(),
                    asociadoEmpresa.num.getText(),
                    asociadoEmpresa.ciudad.getText(),
                    asociadoEmpresa.cp.getText(),
                    asociadoEmpresa.countries.get(asociadoEmpresa.cPais.getSelectedItem().toString())
            );

            var relation = new RelationEntity(
                    asociadoEm,
                    asociadoEmpresa.empresa.getEmpresa(),
                    new java.sql.Date(date.getTime())
            );

            session.save(asociadoEm);
            session.save(relation);
            session.save(address);
            session.save(login);

            transaction.commit();
            session.close();

            /*
            var m = "Registro completado con éxito.";
            if(JOptionPane.showOptionDialog(null, m, "Éxito.", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION){
                //TODO volver al login al dar finalizar.
            }

             */


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
            setPanelEnabled(asociadoEmpresa, true);
            asociadoEmpresa.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            asociadoEmpresa.populateTable();
        }
    }
}
