package gui.alemania;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;

import javax.swing.*;
import java.io.File;

class SFTPWorker extends GuardarCSVWorker {
    String ip;
    String username;
    String pass;
    String destination;

    public SFTPWorker(Informe informe, File temp, String ip, String username, String pass, String destination) {
        super(informe, temp);
        this.ip = ip;
        this.username = username;
        this.pass = pass;
        this.destination = destination;
    }

    @Override
    protected Void doInBackground() throws Exception {
        super.doInBackground();
        var frame = new JFrame("Send");
        var sending = new JLabel("Sending file");
        frame.add(sending);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(informe);
        frame.setVisible(true);

        try {
            var jsch = new JSch();
            var session = jsch.getSession(username, ip);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(pass);
            session.connect();
            var channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.put(file.getAbsolutePath(), destination);
            channel.exit();
            var m = "Archivo enviado";
            JOptionPane.showMessageDialog(informe, m, m, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            var m = ex.getMessage();
            JOptionPane.showMessageDialog(informe, m, m, JOptionPane.ERROR_MESSAGE);
        } finally {
            frame.dispose();
        }
        return null;
    }
}
