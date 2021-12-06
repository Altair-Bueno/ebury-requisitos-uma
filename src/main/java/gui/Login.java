package gui;

import gui.alemania.Informe;

import javax.swing.*;

public class Login extends JPanel {
    private JPanel root;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel label1;
    private JLabel label2;
    private JLabel image;
    private JButton loginButton;
    private JProgressBar progressBar;
    private JLabel cargando;

    public Login() {
        add(root);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField(){
        return usernameField;
    }

    public JPasswordField getPasswordField(){
        return passwordField;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getCargando() {
        return cargando;
    }

    public void failure(String errmsg){
        usernameField.setText("");
        passwordField.setText("");
        JOptionPane.showMessageDialog(this, errmsg);
    }

    public void successAlemania(){
        MainFrame parent = (MainFrame) this.getTopLevelAncestor();
        parent.updateView(new Informe());
    }

    public void successHolanda(){
        MainFrame parent = (MainFrame) this.getTopLevelAncestor();
        parent.updateView(new gui.holanda.Informe());
    }

    public void successUser(){
        MainFrame parent = (MainFrame) this.getTopLevelAncestor();
        parent.dispose();
    }

    public void loading(){
        cargando = new JLabel("Cargando...");
        progressBar = new JProgressBar();
        cargando.setVisible(true);
        progressBar.setVisible(true);
        add(cargando);
        add(progressBar);
        revalidate();
        repaint();
    }
}
