package gui.user;

import database.tables.LoginEntity;
import gui.Frame;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel implements Frame {
    LoginEntity login;

    public Main(LoginEntity login) {
        this.login = login;
    }

    @Override
    public String getTitleBarName() {
        // TODO
        return "Bienvenido " + login.getUser();
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }
}
