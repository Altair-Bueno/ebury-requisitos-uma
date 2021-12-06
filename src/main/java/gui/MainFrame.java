package gui;

import logic.LoginController;

import javax.swing.*;

public class MainFrame extends JFrame {
    JComponent comp;

    public MainFrame(){
        super("Ebury");
        super.setSize(700,400);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        comp = new Login();
        super.setContentPane(comp);
        super.setVisible(true);
    }

    public void updateView(JComponent c){
        comp = c;
        super.removeAll();
        super.add(c);
        super.revalidate();
        super.repaint();
        super.pack();
    }

    public JComponent getComp(){
        return comp;
    }
}
