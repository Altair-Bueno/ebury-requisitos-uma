package gui.alemania;

import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.EburyAccountEntity;
import database.types.Status;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

class GenerarInformeSemanalWorker extends SwingWorker<List<Object[]>, Void> {
    Informe informe;

    public GenerarInformeSemanalWorker(Informe informe) {
        this.informe = informe;
    }

    @Override
    protected List<Object[]> doInBackground() {
        informe.progressBar1.setValue(30);
        informe.lockUI();
        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
            var oneWeek = new Date(new Date().getTime() - informe.ONE_WEEK);
            @SuppressWarnings("unchecked")
            var eburyAccounts = (Stream<EburyAccountEntity>) session.
                    createQuery("from EburyAccountEntity").stream();
            return eburyAccounts
                    .parallel()
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
                        var aux = new Object[]{
                                iban,
                                apellidos,
                                nombre,
                                direccion,
                                nif,
                                fechaNacimiento,
                        };
                        // Change null to "noexistente"
                        return Arrays.stream(aux)
                                .map(a -> a == null ? "noexistente" : a)
                                .toArray();
                    })
                    .toList();
        }

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
