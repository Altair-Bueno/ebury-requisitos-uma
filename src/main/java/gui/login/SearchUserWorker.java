package gui.login;

import database.HibernateStartUp;
import database.tables.LoginEntity;
import gui.user.MainPanel;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class SearchUserWorker extends SwingWorker<LoginEntity, Void> {
    String username;
    String password;
    Login login;

    public SearchUserWorker(String username, String password, Login login) {
        this.username = username;
        this.password = password;
        this.login = login;
    }

    @Override
    protected LoginEntity doInBackground() {
        login.usernameField.setEnabled(false);
        login.passwordField.setEnabled(false);
        login.loginButton.setEnabled(false);
        login.signUpButton.setEnabled(false);
        login.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try (Session session = HibernateStartUp.getSessionFactory().openSession()) {
            var query = session.createQuery("from LoginEntity where user = :u and password = :p");
            query.setParameter("u", username);
            query.setParameter("p", password);
            return (LoginEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void done() {
        try {
            var result = super.get();
            var panel = switch (result.getRol()) {
                case Regler -> new gui.alemania.Informe();
                case Regelgever -> new gui.holanda.Informe();
                case User -> new MainPanel(result);
            };
            var appFrame = login.getAppFrame();

            appFrame.setTitle(panel.getTitleBarName());
            appFrame.setMenuBar(panel.getMenuBar());
            appFrame.remove(login);
            appFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            appFrame.setLocationRelativeTo(null);
            appFrame.setSize(appFrame.getSize());
            appFrame.add(panel);

            appFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            appFrame.pack();
            appFrame.setVisible(true);
        } catch (NullPointerException e) {
            var m = "El usuario y/o contraseña no se reconocen";
            JOptionPane.showMessageDialog(login, m, m, JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            var m = e.getMessage();
            JOptionPane.showMessageDialog(login, m, m, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            login.usernameField.setEnabled(true);
            login.passwordField.setEnabled(true);
            login.loginButton.setEnabled(true);
            login.setCursor(Cursor.getDefaultCursor());
        }
    }
}