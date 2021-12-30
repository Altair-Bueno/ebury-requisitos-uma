package gui.alemania;

import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.EburyAccountEntity;
import database.types.Status;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.*;
import java.util.List;

class GenerarInformeSemanalWorker extends SwingWorker<List<Object[]>, Void> {
    Informe informe;

    public GenerarInformeSemanalWorker(Informe informe) {
        this.informe = informe;
    }

    @Override
    protected List<Object[]> doInBackground() throws Exception {
        informe.progressBar1.setValue(30);
        informe.lockUI();
        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
            var oneWeek = new Date(new Date().getTime() - informe.ONE_WEEK);
            var eburyAccounts = (List<EburyAccountEntity>) session.
                    createQuery("from EburyAccountEntity")
                    .getResultList();
            return eburyAccounts
                    .stream()
                    // Only last 5 years
                    .filter(a -> oneWeek.getTime() <= a.getRegisterdate().getTime())
                    // Only active
                    .filter(a -> a.getStatus() == Status.Active)
                    // Get each row
                    .map(eburyAccount -> {
                        var iban = eburyAccount.getBankAccount().getIban();
                        var apellidos = eburyAccount.getOwner().getLastName1();
                        var nombre = eburyAccount.getOwner().getName();
                        var direccion = eburyAccount
                                .getOwner()
                                .getDireccion()
                                .stream()
                                .max(Comparator.comparingInt(AddressEntity::getId))
                                .get();
                        var nif = eburyAccount.getOwner().getNif();
                        var fechaNacimiento = eburyAccount.getOwner().getBirthDate();
                        var aux = new Object[] {
                                iban,
                                apellidos,
                                nombre,
                                direccion,
                                nif,
                                fechaNacimiento,
                        };
                        // Change null to "noexistente"
                        return Arrays.stream(aux)
                                .map(a -> a == null ? "noexistente":a)
                                .toArray();
                    })
                    .toList();
/*
            // TODO direcciones puede haber varias. Este método no tiene en
            // cuenta eso
            informe.progressBar1.setValue(250);
            var ibans = session.createQuery("select ac.bankAccount.id from EburyAccountEntity ac order by ac.id").getResultList();
            var apellidos = session.createQuery("select op.owner.lastName1 from EburyAccountEntity op order by op.id").getResultList();
            var nombres = session.createQuery("select ac.owner.name from EburyAccountEntity ac order by ac.id").getResultList();
            var direcciones = session.createQuery("select ac.owner.direccion from EburyAccountEntity ac order by ac.id").getResultList();
            var nifs = session.createQuery("select ac.owner.nif from EburyAccountEntity ac order by ac.id").getResultList();
            var fechasNacimiento = session.createQuery("select ac.owner.birthDate from EburyAccountEntity ac order by ac.id").getResultList();
            var aperturas = session.createQuery("select ac.registerdate from EburyAccountEntity ac order by ac.id").getResultList();
            var result = new LinkedList<Object[]>();
            for (int i = 0; i < ibans.size(); i++) {
                var aux = new Object[]{
                        ibans.get(i),
                        apellidos.get(i),
                        nombres.get(i),
                        direcciones.get(i),
                        nifs.get(i),
                        fechasNacimiento.get(i),
                        aperturas.get(i)
                };
                result.add(aux);
            }
            return result;
*/
        }

/*
        informe.progressBar1.setValue(0);
        informe.progressBar1.setMaximum(1000);
        informe.lockUI();
        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
            informe.progressBar1.setValue(250);
            var ibans = session.createQuery("select ac.bankAccount.id from EburyAccountEntity ac order by ac.id").getResultList();
            var apellidos = session.createQuery("select op.owner.lastName1 from EburyAccountEntity op order by op.id").getResultList();
            var nombres = session.createQuery("select ac.owner.name from EburyAccountEntity ac order by ac.id").getResultList();
            var direcciones = session.createQuery("select ac.owner.direccion from EburyAccountEntity ac order by ac.id").getResultList();
            var nifs = session.createQuery("select ac.owner.nif from EburyAccountEntity ac order by ac.id").getResultList();
            var fechasNacimiento = session.createQuery("select ac.owner.birthDate from EburyAccountEntity ac order by ac.id").getResultList();
            var aperturas = session.createQuery("select ac.registerdate from EburyAccountEntity ac order by ac.id").getResultList();
            var result = new LinkedList<Object[]>();
            for (int i = 0; i < ibans.size(); i++) {
                var aux = new Object[]{ibans.get(i), apellidos.get(i), nombres.get(i), direcciones.get(i), nifs.get(i), fechasNacimiento.get(i), aperturas.get(i)};
                result.add(aux);
            }
            return result;
        }
*/
    }

    @Override
    protected void done() {
        try {
            informe.setData(get());
        } catch (Exception e) {
            e.printStackTrace();
            var m = e.getMessage();
            JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
        } finally {
            informe.unlockUI();
        }
    }
}
