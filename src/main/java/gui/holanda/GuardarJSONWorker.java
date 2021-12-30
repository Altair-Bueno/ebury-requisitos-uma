package gui.holanda;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class GuardarJSONWorker extends SwingWorker<Void, Void> {
    Informe informe;
    File file;

    public GuardarJSONWorker(Informe informe, File file) {
        this.informe = informe;
        this.file = file;
    }

    @Override
    protected Void doInBackground() {
        informe.lockUI();
        var frame = new JFrame("Save");
        var progress = new JProgressBar();
        frame.add(progress);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(informe);
        frame.setVisible(true);
        try (var json = new FileWriter(file)) {
            json.write(informe.jsonPreviewArea.getText());
            progress.setValue(100);
            var m = "Correcto";
            JOptionPane.showMessageDialog(frame, m, m, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            var m = ex.getMessage();
            JOptionPane.showMessageDialog(frame, m, m, JOptionPane.ERROR_MESSAGE);
        } finally {
            frame.dispose();
        }
        return null;
    }

    @Override
    protected void done() {
        informe.unlockUI();
    }
}
