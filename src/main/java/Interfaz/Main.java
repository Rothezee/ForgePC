package Interfaz;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicacion.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu().setVisible(true));
    }
}
