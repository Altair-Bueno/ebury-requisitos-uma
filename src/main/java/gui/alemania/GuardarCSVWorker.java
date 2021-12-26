package gui.alemania;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class GuardarCSVWorker extends SwingWorker<Void, Void> {
    Informe informe;
    File file;

    public GuardarCSVWorker(Informe informe, File file) {
        this.informe = informe;
        this.file = file;
    }

    @Override
    protected Void doInBackground() throws Exception {
        informe.lockUI();
        var frame = new JFrame("Save");
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(informe);
        frame.setVisible(true);
        generateCSV(frame);
        return null;
    }

    private void generateCSV(JFrame frame) {
        var model = informe.csvPreviewTable.getModel();
        informe.progressBar1.setMaximum(model.getRowCount());
        try (var csv = new FileWriter(file)) {
            //TODO añade un montón de columnas que no deberían estar
            for (int i = 0; i < model.getColumnCount() - 1; i++) {
                csv.write(model.getColumnName(i) + ",");
            }
            csv.write(model.getColumnName(model.getColumnCount() - 1));
            csv.write("\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount() - 1; j++) {
                    var value = model.getValueAt(i, j).toString();
                    var safeValue = value.replace(",", "");
                    csv.write(safeValue + ",");
                }
                csv.write(model.getValueAt(i, model.getColumnCount() - 1).toString().replace(',', Character.MIN_VALUE));
                informe.progressBar1.setValue(i);
                csv.write("\n");
            }
            var m = "Exportación correcta";
            JOptionPane.showMessageDialog(frame, m, m, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            var m = ex.getMessage();
            JOptionPane.showMessageDialog(frame, m, m, JOptionPane.ERROR_MESSAGE);
        } finally {
            frame.dispose();
        }
    }

    @Override
    protected void done() {
        informe.unlockUI();
    }
}