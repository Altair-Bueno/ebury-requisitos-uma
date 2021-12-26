package gui.alemania;

import database.HibernateStartUp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class GenerarPrimerInformeWorker extends SwingWorker<List<Object[]>, Void> {
    Informe informe;

    public GenerarPrimerInformeWorker(Informe informe) {
        this.informe = informe;
    }

    @Override
    protected List<Object[]> doInBackground() throws Exception {
        informe.progressBar1.setMaximum(1000);
        informe.lockUI();
        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
            // TODO querry no es la correcta. Completars
            informe.progressBar1.setValue(250);
            //var result = session.createQuery("select op.bankAccountIban, op.amount, op.eburyAccount.registerdate, op.eburyAccount.closedate, concat(op.eburyAccount.owner.name, ' ', op.eburyAccount.owner.lastName1, ' ', op.eburyAccount.owner.lastName2), op.eburyAccount.owner.birthDate, op.beneficiary from OperationEntity op", Object[].class).getResultList();
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
            //var b = session.createQuery("select op.id from OperationEntity op, EburyAccountEntity ac where not op.eburyAccount.id = 1", Object[].class).getResultList();
            //System.out.println(b);
            //var c = session.createQuery("select op.id, ac.status from OperationEntity op join op.eburyAccount ac", Object[].class).getResultList();
            //System.out.println(c);
            //var a = session.createQuery("select ac.bankAccount.iban, coalesce(op.amount, 'noexistente'), ac.registerdate, coalesce(ac.closedate, 'noexistente'), concat(ac.owner.name, ' ' , coalesce(ac.owner.lastName1, ''), ' ', coalesce(ac.owner.lastName2, '')), coalesce(ac.owner.birthDate, 'noexistente') , coalesce(op.beneficiary, 'noexistente') from OperationEntity op join op.eburyAccount ac", Object[].class).getResultList();
            //System.out.println(a);
        }
    }

    @Override
    protected void done() {
        try {
            var result = get();
            informe.csvPreviewTable.removeAll();
            // TODO no está bien del todo. Mirar las diapositivas del profesor
            var tablemodel = new DefaultTableModel(new String[]{"IBAN", "Apellido", "Nombre", "Direcciones", "NIF/CIF", "Fecha Nacimiento/Creación"}, 0);
            informe.progressBar1.setMaximum(result.size());
            for (int i = 0; i < result.size(); i++) {
                var fiveYearsAgo = new Date();
                fiveYearsAgo = new Date(fiveYearsAgo.getTime() - informe.FIVE_YEARS);
                //TODO Condición de los cinco años
                if (fiveYearsAgo.getTime() <= ((Date) result.get(i)[6]).getTime()) {
                    tablemodel.addRow(new Object[]{
                            result.get(i)[0] == null ? "noexistente" : result.get(i)[0],
                            result.get(i)[1] == null ? "noexistente" : result.get(i)[1],
                            result.get(i)[2] == null ? "noexistente" : result.get(i)[2],
                            result.get(i)[3] == null ? "noexistente" : result.get(i)[3],
                            result.get(i)[4] == null ? "noexistente" : result.get(i)[4],
                            result.get(i)[5] == null ? "noexistente" : result.get(i)[5]
                    });
                }
                informe.progressBar1.setValue(i);
            }
            informe.csvPreviewTable.setModel(tablemodel);
            resizeColumnWidth(informe.csvPreviewTable);
            informe.progressBar1.setValue(informe.progressBar1.getMaximum());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            var m = e.getMessage();
            JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
        } finally {
            informe.unlockUI();
        }
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
