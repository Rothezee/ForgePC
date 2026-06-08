package Interfaz;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import logica.modelo.Cliente;

public class FormClientes extends javax.swing.JPanel {

    private Cliente clienteEdicion;
    private boolean guardado;

    public FormClientes() {
        initComponents();
    }

    public void setCliente(Cliente cliente) {
        this.clienteEdicion = cliente;
        if (cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtDni.setText(String.valueOf(cliente.getDni()));
            txtDireccion.setText(cliente.getDireccion());
            txtMail.setText(cliente.getMail());
            txtCuil.setText(String.valueOf(cliente.getCuil()));
            cmbNacionalidad.setSelectedItem(cliente.getNacionalidad());
            try {
                Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(cliente.getFechaNacimiento());
                spnFecha.setValue(fecha);
            } catch (Exception ex) {
                spnFecha.setValue(new Date());
            }
        }
    }

    public boolean isGuardado() {
        return guardado;
    }

    public Cliente getClienteGuardado() throws IllegalArgumentException {
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre y apellido son obligatorios.");
        }
        int dni = Integer.parseInt(txtDni.getText().trim());
        long cuil = Long.parseLong(txtCuil.getText().trim());
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(spnFecha.getValue());
        int id = clienteEdicion != null ? clienteEdicion.getId() : 0;
        return new Cliente(id, txtNombre.getText().trim(), txtApellido.getText().trim(), dni, fecha,
                txtDireccion.getText().trim(), txtMail.getText().trim(), cuil,
                (String) cmbNacionalidad.getSelectedItem());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblDni = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        spnFecha = new javax.swing.JSpinner();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblMail = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        lblCuil = new javax.swing.JLabel();
        txtCuil = new javax.swing.JTextField();
        lblNacionalidad = new javax.swing.JLabel();
        cmbNacionalidad = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(410, 450));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Formulario Clientes");
        add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 15, 240, 30));

        lblNombre.setBackground(new java.awt.Color(255, 255, 255));
        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("Nombre");
        add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 90, 28));

        txtNombre.setBackground(new java.awt.Color(42, 38, 38));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 280, 28));

        lblApellido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblApellido.setForeground(new java.awt.Color(255, 255, 255));
        lblApellido.setText("Apellido");
        add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 98, 90, 28));

        txtApellido.setBackground(new java.awt.Color(42, 38, 38));
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 98, 280, 28));

        lblDni.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDni.setForeground(new java.awt.Color(255, 255, 255));
        lblDni.setText("DNI");
        add(lblDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 136, 90, 28));

        txtDni.setBackground(new java.awt.Color(42, 38, 38));
        txtDni.setForeground(new java.awt.Color(255, 255, 255));
        add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 136, 280, 28));

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha Nac");
        add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 174, 90, 28));

        spnFecha.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.YEAR));
        add(spnFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 174, 280, 28));

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion.setText("Direccion");
        add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 212, 90, 28));

        txtDireccion.setBackground(new java.awt.Color(42, 38, 38));
        txtDireccion.setForeground(new java.awt.Color(255, 255, 255));
        add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 212, 280, 28));

        lblMail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMail.setForeground(new java.awt.Color(255, 255, 255));
        lblMail.setText("Mail");
        add(lblMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 90, 28));

        txtMail.setBackground(new java.awt.Color(42, 38, 38));
        txtMail.setForeground(new java.awt.Color(255, 255, 255));
        add(txtMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 280, 28));

        lblCuil.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCuil.setForeground(new java.awt.Color(255, 255, 255));
        lblCuil.setText("Cuil");
        add(lblCuil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 288, 90, 28));

        txtCuil.setBackground(new java.awt.Color(42, 38, 38));
        txtCuil.setForeground(new java.awt.Color(255, 255, 255));
        add(txtCuil, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 288, 280, 28));

        lblNacionalidad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNacionalidad.setForeground(new java.awt.Color(255, 255, 255));
        lblNacionalidad.setText("Nacionalidad");
        add(lblNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 326, 90, 28));

        cmbNacionalidad.setBackground(new java.awt.Color(42, 38, 38));
        cmbNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Argentina", "Paraguay", "Uruguay", "Chile", "Brasil", "Bolivia" }));
        add(cmbNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 326, 280, 28));

        btnGuardar.setBackground(new java.awt.Color(0, 204, 51));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 110, 40));

        btnCancelar.setBackground(new java.awt.Color(204, 51, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, 120, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void guardar() {
        try {
            getClienteGuardado();
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbNacionalidad;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCuil;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblNacionalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JSpinner spnFecha;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCuil;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
