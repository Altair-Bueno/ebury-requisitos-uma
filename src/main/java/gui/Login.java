package gui;

import database.HibernateStartUp;
import database.tables.LoginEntity;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class Login extends JPanel implements Frame{
    private JPanel root;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel label1;
    private JLabel label2;
    private JLabel image;
    private JButton loginButton;

    public Login() {
        add(root);
        ActionListener loginListener = (e)-> {
            var username = usernameField.getText();
            var password = String.valueOf(passwordField.getPassword());
            var search = new SearchUser(username,password,this);
            search.execute();
        };
        loginButton.addActionListener(loginListener);
        usernameField.addActionListener(loginListener);
        passwordField.addActionListener(loginListener);
    }

    @Override
    public String getTitleBarName() {
        return "Bienvenido";
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }

    private class SearchUser extends SwingWorker<LoginEntity,Void> {
        String username;
        String password;
        Login login;

        public SearchUser(String username, String password, Login login) {
            this.username = username;
            this.password = password;
            this.login = login;
        }

        @Override
        protected LoginEntity doInBackground() throws Exception {
            usernameField.setEnabled(false);
            passwordField.setEnabled(false);
            loginButton.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try (Session session = HibernateStartUp.getSessionFactory().openSession()) {
                return (LoginEntity) session
                        .createQuery("FROM LoginEntity WHERE user = '" + username + "' AND password = '" + password + "'")
                        .list()
                        .get(0);
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
                    case "Regler" -> new gui.alemania.Informe();
                    case "Regelgever" -> new gui.holanda.Informe();
                    case "User" -> new gui.user.Main(result);
                    default -> throw new IllegalArgumentException();
                };
                var frame = new JFrame(panel.getTitleBarName());
                frame.setMenuBar(panel.getMenuBar());
                frame.add(panel);
                frame.pack();
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }catch (NullPointerException e) {
                var m = "El usuario y/o contraseña no se reconocen";
                JOptionPane.showMessageDialog(login,m,m,JOptionPane.ERROR_MESSAGE);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                usernameField.setEnabled(true);
                passwordField.setEnabled(true);
                loginButton.setEnabled(true);
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }
}