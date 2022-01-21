package gui.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDateTextField extends JFormattedTextField{

    private final String format;
    private final char datesep;
    private Component pare = null;

    /**
     * Establece el componente padre para los mensajes de dialogo.
     * <p>
     * @param pare
     */
    public void setPare(Component pare) {
        this.pare = pare;
    }

    public JDateTextField(final String format) {
        super(new SimpleDateFormat(format));
        this.format = format;
        setColumns(format.length());
        //if(format.contains("-")) datesep=KeyEvent.VK_MINUS;
        //else
        if (format.contains("/")) {
            datesep = KeyEvent.VK_SLASH;
        } else {
            datesep = KeyEvent.VK_MINUS;
        }

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Date date = getDate();
                } catch(ParseException ex) {
                    showError("Por favor introduzca una fecha válida.");
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String s = "" + c;
                if (s.matches("|[a-z]|i")){
                    showError("Carácter no válido detectado " + s);
                    e.consume();
                } else if(!((c >= '0') && (c <= '9')
                        ||(c == KeyEvent.VK_BACK_SPACE)
                        ||(c == KeyEvent.VK_DELETE)
                        ||(c == datesep)||(c == KeyEvent.VK_COLON))){
                    showError("Carácter no válido detectado " + c);
                    e.consume();
                }
                /*
                 * else{
                 * try{
                 * Date date=getDate();
                 * }catch(ParseException ex){
                 * showError("Por favor introduzca una fecha válida.");
                 * }
                 * }
                 */
            }
        });
        setValue(new Date());
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(pare, error + "\n\tEl patrón válido es: " + format, "Fecha NO válida", JOptionPane.ERROR_MESSAGE);
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat frt = new SimpleDateFormat(format);
        return frt.parse(getText());
    }
}