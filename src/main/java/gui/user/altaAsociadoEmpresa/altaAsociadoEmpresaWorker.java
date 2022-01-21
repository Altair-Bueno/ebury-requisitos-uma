package gui.user.altaAsociadoEmpresa;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
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
        var mes = asociadoEmpresa.fMM.getSelectedItem().toString();
        var dia = Integer.parseInt(asociadoEmpresa.fDD.getSelectedItem().toString());
        var anyo = Integer.parseInt(asociadoEmpresa.fYYYY.getSelectedItem().toString());

        try(Session session = HibernateStartUp.getSessionFactory().openSession()){
            var transaction = session.beginTransaction();

            var format = new SimpleDateFormat("dd-MM-yyyy");
            var inputFormat = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputFormat.parse(mes));
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM");

            var parsed = format.parse(
                    String.format("%02d", dia)
                            + "-" + outputFormat.format(cal.getTime())
                            + "-" + anyo
            );

            var date = new java.util.Date();
            
            var nombre = asociadoEmpresa.segundoNom.getText().isBlank() ? asociadoEmpresa.primerNom.getText() : asociadoEmpresa.primerNom.getText() + " " + asociadoEmpresa.segundoNom.getText();

            var asociadoEm = new AssociatedStaffEntity(
                    asociadoEmpresa.tNIF.getText(),
                    nombre,
                    asociadoEmpresa.primerAp.getText(),
                    asociadoEmpresa.segundoAp.getText(),
                    new Date(parsed.getTime()),
                    asociadoEmpresa.lTipo.getSelectedItem().toString()
            );

            var login = new LoginEntity(asociadoEmpresa.tNIF.getText(), asociadoEmpresa.pwd.getText(), Rol.User, asociadoEm);

            var address = new AddressAssociatedStaffEntity(
                    asociadoEm,
                    asociadoEmpresa.calle.getText(),
                    Integer.parseInt(asociadoEmpresa.num.getText()),
                    asociadoEmpresa.ciudad.getText(),
                    asociadoEmpresa.cp.getText(),
                    asociadoEmpresa.countries.get(asociadoEmpresa.cPais.getSelectedItem().toString()),
                    asociadoEmpresa.ppo.getText(),
                    asociadoEmpresa.cbDirActual.isSelected()
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
        } catch (NumberFormatException nfe){
            var m = "El número de calle debe ser un número válido.";
            JOptionPane.showMessageDialog(this.asociadoEmpresa, m,m,JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            e.printStackTrace();
            var m = "Ha ocurrido un error en la operación de registro. Inténtelo de nuevo.";
            if(JOptionPane.showOptionDialog(this.asociadoEmpresa, m, m, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null) == JOptionPane.OK_OPTION){
                asociadoEmpresa.back2Login();
            }
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
            asociadoEmpresa.clear();
            setPanelEnabled(asociadoEmpresa, true);
            asociadoEmpresa.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            asociadoEmpresa.populateTable();
        }
    }
}
