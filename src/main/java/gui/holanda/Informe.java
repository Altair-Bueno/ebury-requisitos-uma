package gui.holanda;

import gui.Frame;

import javax.swing.*;
import java.awt.*;

public class Informe extends JPanel implements Frame {
    private JPanel root;
    private JTextArea jsonPreviewArea;
    private JButton generarButton;
    private JButton exportarButton;
    private JTabbedPane filterTabbedPane;
    private JPanel filtroProductoPanel;
    private JPanel filtroClientePanel;
    private JPanel healthCheckPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JComboBox tipoComboBox;
    private JTextField numeroProductoTextField;
    private JTextField primerNombreTextField;
    private JTextField segundoNombreTextField;
    private JTextField ciudadTextField;
    private JTextField calleTextField;
    private JTextField numeroTextField;
    private JTextField postalTextField;
    private JLabel nombreLabel;
    private JLabel direccionLabel;

    public Informe() {
        add(root);
    }

    @Override
    public String getTitleBarName() {
        // TODO
        return "Informe Holanda";
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }
}
