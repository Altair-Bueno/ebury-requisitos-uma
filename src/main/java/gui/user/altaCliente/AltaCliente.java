package gui.user.altaCliente;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import gui.Frame;
import gui.login.Login;

import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Locale.getISOCountries;

public class AltaCliente extends JPanel implements Frame {
    JPanel root; //OK
    JTextField tNIF; //OK -- OBLIGATORIO
    JPasswordField tPassword; //OK -- OBLIGATORIO
    JPasswordField tRepPassword; //OK -- OBLIGATORIO
    JTextField tNombre; // OK -- OBLIGATORIO
    JTextField tSegundoNombre;
    JTextField tPrimerAp; // OK -- OBLIGATORIO
    JTextField tSegundoAp;
    JTextField tCiudad; // OK
    JTextField tCalle; //OK
    JTextField tNumero; //OK
    JTextField tCP; //OK
    JComboBox cPais;
    JButton cancelarButton;
    JButton aceptarButton;
    JPanel VSPACER;
    JPanel HSPACER;
    JPanel HSPACER2;
    JTextField tPPO; //OK
    JTextField tRegion; //OK
    private JPanel fechaN;
    JSpinner fDD;
    JComboBox fMM;
    JSpinner fYYYY;


    private SpinnerModel dayModel31 = new SpinnerNumberModel(1, 1, 31, 1); //default value,lower bound,upper bound,increment by
    private SpinnerModel dayModel30 = new SpinnerNumberModel(1, 1, 30, 1);
    private SpinnerModel dayModel28 = new SpinnerNumberModel(1, 1, 28, 1);
    private SpinnerModel dayModel29 = new SpinnerNumberModel(1, 1, 29, 1);
    private List<String> months31 = new ArrayList(Arrays.asList("Enero", "Marzo", "Mayo", "Julio", "Agosto", "Octubre", "Diciembre"));
    private List<String> months30 = new ArrayList<>(Arrays.asList("Abril", "Junio", "Septiembre", "Noviembre"));
    Map<String, String> countries = new HashMap<>();

    public AltaCliente() {
        countries.put("-", "");
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("es", iso);
            countries.put(l.getDisplayCountry(l), iso);
        }

        $$$setupUI$$$();
        add(root);

        ActionListener cancelar = (e) -> {
            var panel = new Login();
            var frame = getAppFrame();
            frame.setTitle(panel.getTitleBarName());
            frame.setMenuBar(panel.getMenuBar());

            frame.remove(this);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setSize(frame.getSize());

            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        };

        ActionListener aceptar = (e) -> {
            var ok = true;
            if (tNIF.getText().isBlank() || tNombre.getText().isBlank() || tPrimerAp.getText().isBlank() || tPassword.getText().isBlank() || tRepPassword.getText().isBlank()) {
                var m = "Rellene los campos obligatorios de la empresa.";
                JOptionPane.showMessageDialog(this, m, m, JOptionPane.WARNING_MESSAGE);
                ok = false;
            }

            if (tCalle.getText().isBlank() || tNumero.getText().isBlank() || tPPO.getText().isBlank() || tCiudad.getText().isBlank() || tCP.getText().isBlank() || cPais.getSelectedItem().toString().equals("-")) {
                var m = "Rellene los campos obligatorios de la dirección.";
                JOptionPane.showMessageDialog(this, m, m, JOptionPane.WARNING_MESSAGE);
                ok = false;
            }
            if (!Arrays.equals(tPassword.getPassword(), tRepPassword.getPassword())) {
                var m = "Las contraseñas no coinciden";
                JOptionPane.showMessageDialog(this, m, m, JOptionPane.WARNING_MESSAGE);
                ok = false;
            }

            if (ok) {
                var clienteWorker = new AltaClienteWorker(this);
                clienteWorker.execute();
            }

        };

        ActionListener monthChange = (e) -> {
            change();
        };

        cancelarButton.addActionListener(cancelar);
        aceptarButton.addActionListener(aceptar);
        fMM.addActionListener(monthChange);
        fYYYY.addChangeListener(e -> change());
    }

    private void change() {
        var mes = fMM.getSelectedItem().toString();
        var year = Integer.parseInt(fYYYY.getValue().toString());
        if (months31.contains(mes)) {
            fDD.setModel(dayModel31);
        } else if (months30.contains(mes)) {
            fDD.setModel(dayModel30);
        } else {
            if (year % 4 == 0) {
                fDD.setModel(dayModel29);
            } else {
                fDD.setModel(dayModel28);
            }

        }
    }

    private JFrame getAppFrame() {
        return (JFrame) this.getTopLevelAncestor();
    }

    @Override
    public String getTitleBarName() {
        return "Registro Persona Física";
    }

    @Override
    public MenuBar getMenuBar() {
        return null;
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        root = new JPanel();
        root.setLayout(new GridLayoutManager(12, 8, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Sobre el usuario");
        root.add(label1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Dirección");
        root.add(label2, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tCiudad = new JTextField();
        tCiudad.setToolTipText("");
        root.add(tCiudad, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tCalle = new JTextField();
        tCalle.setToolTipText("");
        root.add(tCalle, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Ciudad*");
        root.add(label3, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Calle*");
        root.add(label4, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tNumero = new JTextField();
        tNumero.setToolTipText("");
        root.add(tNumero, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Número*");
        root.add(label5, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        VSPACER = new JPanel();
        VSPACER.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.add(VSPACER, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        HSPACER = new JPanel();
        HSPACER.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.add(HSPACER, new GridConstraints(11, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        HSPACER2 = new JPanel();
        HSPACER2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.add(HSPACER2, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        aceptarButton = new JButton();
        aceptarButton.setText("Aceptar");
        root.add(aceptarButton, new GridConstraints(10, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelarButton = new JButton();
        cancelarButton.setText("Cancelar");
        root.add(cancelarButton, new GridConstraints(10, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("País*");
        root.add(label6, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Código Postal*");
        root.add(label7, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tCP = new JTextField();
        tCP.setToolTipText("");
        root.add(tCP, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Planta / Puerta / Oficina*");
        root.add(label8, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tPPO = new JTextField();
        tPPO.setText("");
        root.add(tPPO, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Región");
        root.add(label9, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tRegion = new JTextField();
        root.add(tRegion, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        root.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("NIF*");
        root.add(label10, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tNIF = new JTextField();
        tNIF.setText("");
        root.add(tNIF, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Contraseña*");
        root.add(label11, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tPassword = new JPasswordField();
        root.add(tPassword, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tRepPassword = new JPasswordField();
        root.add(tRepPassword, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Repetir Contraseña*");
        root.add(label12, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Fecha Nacimiento");
        root.add(label13, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tNombre = new JTextField();
        root.add(tNombre, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Nombre*");
        root.add(label14, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Segundo Nombre");
        root.add(label15, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tSegundoNombre = new JTextField();
        root.add(tSegundoNombre, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Primer Apellido*");
        root.add(label16, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Segundo Apellido");
        root.add(label17, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tPrimerAp = new JTextField();
        root.add(tPrimerAp, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tSegundoAp = new JTextField();
        root.add(tSegundoAp, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        fechaN = new JPanel();
        fechaN.setLayout(new GridLayoutManager(2, 6, new Insets(0, 0, 0, 0), -1, -1));
        root.add(fechaN, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        fechaN.add(spacer1, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("/");
        fechaN.add(label18, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        Font label19Font = this.$$$getFont$$$(null, -1, 10, label19.getFont());
        if (label19Font != null) label19.setFont(label19Font);
        label19.setHorizontalAlignment(0);
        label19.setHorizontalTextPosition(0);
        label19.setText("Dia");
        fechaN.add(label19, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        Font label20Font = this.$$$getFont$$$(null, -1, 10, label20.getFont());
        if (label20Font != null) label20.setFont(label20Font);
        label20.setText("Mes");
        fechaN.add(label20, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("/");
        fechaN.add(label21, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        Font label22Font = this.$$$getFont$$$(null, -1, 10, label22.getFont());
        if (label22Font != null) label22.setFont(label22Font);
        label22.setText("Año");
        fechaN.add(label22, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaN.add(fDD, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fMM = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Enero");
        defaultComboBoxModel1.addElement("Febrero");
        defaultComboBoxModel1.addElement("Marzo");
        defaultComboBoxModel1.addElement("Abril");
        defaultComboBoxModel1.addElement("Mayo");
        defaultComboBoxModel1.addElement("Junio");
        defaultComboBoxModel1.addElement("Julio");
        defaultComboBoxModel1.addElement("Agosto");
        defaultComboBoxModel1.addElement("Septiembre");
        defaultComboBoxModel1.addElement("Octubre");
        defaultComboBoxModel1.addElement("Noviembre");
        defaultComboBoxModel1.addElement("Diciembre");
        fMM.setModel(defaultComboBoxModel1);
        fechaN.add(fMM, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaN.add(fYYYY, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
        root.add(cPais, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

        fDD = new JSpinner();
        fDD.setModel(dayModel31);
        var yM = new SpinnerNumberModel(2022, 1900, 2022, 1);
        fYYYY = new JSpinner(yM);
        fYYYY.setEditor(new JSpinner.NumberEditor(fYYYY, "####"));
        JComponent comp = fYYYY.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        var keys = new TreeSet<>(countries.keySet());
        var c = new ArrayList<String>();
        for (String key : keys) {
            c.add(key);
        }
        cPais = new JComboBox(c.toArray());

    }
}
