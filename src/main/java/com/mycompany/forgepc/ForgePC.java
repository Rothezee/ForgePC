/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.forgepc;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.UIManager;
import ui.VentanaPrincipal;

public class ForgePC {
    public static void main(String[] args) {
        // 1. Configuramos el diseño moderno ANTES de abrir cualquier ventana
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf()); // Activa el modo oscuro moderno
        } catch (Exception ex) {
            System.err.println("Error al inicializar el diseño.");
        }

        // 2. Ejecutamos la interfaz gráfica en el hilo correspondiente de Swing
        java.awt.EventQueue.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
