package gui.alemania;

import gui.Frame;

import javax.swing.*;
import java.awt.*;

public class Informe extends JPanel implements Frame {
    private JButton primerInformeButton;
    private JTable csvPreviewTable;
    private JButton informeSemanalButton;
    private JButton enviarSFTPButton;
    private JButton exportarButton;
    private JScrollPane scrollPane;
    private JPanel root;

    public Informe() {
        add(root);
    }

    @Override
    public String getTitleBarName() {
        return "Informe Alemania";
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }
}
