import com.formdev.flatlaf.FlatIntelliJLaf;
import gui.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        var frame = new JFrame("Ebury");
        frame.setSize(700,400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var l = new Login();
        var lcont = new LoginController(l);
        frame.setContentPane(l);
        frame.setVisible(true);
    }
}
