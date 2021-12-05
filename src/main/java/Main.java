import com.formdev.flatlaf.FlatIntelliJLaf;
import gui.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        var frame = new JFrame("Ebury");
        var l = new Login();
        frame.setContentPane(l);
        frame.setVisible(true);
    }
}
