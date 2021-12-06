import com.formdev.flatlaf.FlatIntelliJLaf;
import gui.Login;
import gui.MainFrame;
import logic.LoginController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        MainFrame frame = new MainFrame();
        var lc = new LoginController((Login) frame.getComp());

    }
}
