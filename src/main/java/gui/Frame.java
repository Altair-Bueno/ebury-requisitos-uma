package gui;

import java.awt.*;

/**
 * Interfaz para todos los paneles de la aplicación
 */
public interface Frame {
    /**
     * @return Nombre del panel
     */
    String getTitleBarName();

    /**
     * @return Barra de menus de la aplicación
     */
    MenuBar getMenuBar();
}
