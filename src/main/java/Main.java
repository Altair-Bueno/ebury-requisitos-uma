import com.formdev.flatlaf.FlatIntelliJLaf;
import gui.Login;

import javax.swing.*;

public class Main {
    public static void setUp() {
        if (System.getProperty("os.name").contains("Mac OS X")) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Ebury");
            System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.textantialiasing", "true");
            System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
        }
        FlatIntelliJLaf.setup();
    }

    public static void main(String[] args) {
        setUp();
        var login = new Login();
        var frame = new JFrame(login.getTitleBarName());
        frame.setMenuBar(login.getMenuBar());
        frame.add(login);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
