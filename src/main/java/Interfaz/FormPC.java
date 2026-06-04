package Interfaz;

import logica.administradores.SistemaForge;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import logica.modelo.Cliente;
import logica.modelo.Componente;
import logica.modelo.PC;

public class FormPC extends javax.swing.JPanel {

    private final List<Integer> idsComponentes = new ArrayList<>();
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private boolean guardado;

    public FormPC() {
        initComponents();
        listaComponentes.setModel(modeloLista);
        cargarDatos();
    }

    private void cargarDatos() {
        SistemaForge sistema = SistemaForge.getInstancia();
        cmbCliente.removeAllItems();
        for (Cliente c : sistema.getAdminClientes().listarOrdenados()) {
            cmbCliente.addItem(c.getId() + " - " + c.getApellido() + ", " + c.getNombre());
        }
        if (cmbCliente.getItemCount() == 0) {
            cmbCliente.addItem("(Sin clientes cargados)");
            cmbCliente.setEnabled(false);
        } else {
            cmbCliente.setEnabled(true);
        }
        idsComponentes.clear();
        modeloLista.clear();
        for (Componente comp : sistema.getAdminComponentes().listarTodos()) {
            idsComponentes.add(comp.getId());
            modeloLista.addElement(comp.getId() + " - " + comp.getModelo() + " (" + comp.getTipo() + ")");
        }
        listaComponentes.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public boolean isGuardado() {
        return guardado;
    }

    public PC getPCGuardada() {
        if (!cmbCliente.isEnabled() || cmbCliente.getSelectedIndex() < 0) {
            throw new IllegalArgumentException("No hay clientes. Agregue al menos uno en Clientes.");
        }
        int[] indices = listaComponentes.getSelectedIndices();
        if (indices.length == 0) {
            throw new IllegalArgumentException("Seleccione al menos un componente.");
        }
        String itemCliente = (String) cmbCliente.getSelectedItem();
        if (itemCliente == null || itemCliente.startsWith("(")) {
            throw new IllegalArgumentException("Seleccione un cliente valido.");
        }
        int idCliente = Integer.parseInt(itemCliente.split(" - ", 2)[0].trim());
        List<Integer> ids = new ArrayList<>();
        for (int indice : indices) {
            ids.add(idsComponentes.get(indice));
        }
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(spnFecha.getValue());
        return new PC(0, idCliente, fecha, ids);
    }

    private void guardar() {
        try {
            getPCGuardada();
            guardado = true;
            javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        guardado = false;
        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        spnFecha = new javax.swing.JSpinner();
        lblLista = new javax.swing.JLabel();
        scrollComponentes = new javax.swing.JScrollPane();
        listaComponentes = new javax.swing.JList<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Construir PC");
        add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 15, 200, 30));

        lblCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblCliente.setText("Cliente");
        add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 100, 28));

        cmbCliente.setBackground(new java.awt.Color(42, 38, 38));
        add(cmbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 260, 28));

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha armado");
        add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 98, 100, 28));

        spnFecha.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.YEAR));
        add(spnFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 98, 260, 28));

        lblLista.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLista.setForeground(new java.awt.Color(255, 255, 255));
        lblLista.setText("Componentes (Ctrl+clic)");
        add(lblLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 136, 160, 28));

        listaComponentes.setBackground(new java.awt.Color(42, 38, 38));
        listaComponentes.setForeground(new java.awt.Color(255, 255, 255));
        scrollComponentes.setViewportView(listaComponentes);

        add(scrollComponentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 390, 200));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 100, 32));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 100, 32));
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblLista;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<String> listaComponentes;
    private javax.swing.JScrollPane scrollComponentes;
    private javax.swing.JSpinner spnFecha;
    // End of variables declaration//GEN-END:variables
}
