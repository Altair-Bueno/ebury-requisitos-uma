package gui.login;

import database.HibernateStartUp;
import database.tables.LoginEntity;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;

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
                case User -> new gui.user.Main(result);
            };
            var frame = new JFrame(panel.getTitleBarName());
            frame.setMenuBar(panel.getMenuBar());
            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setSize(frame.getSize());
            frame.setVisible(true);
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