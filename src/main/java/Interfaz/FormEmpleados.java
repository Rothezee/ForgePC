package Interfaz;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import logica.modelo.Administrativo;
import logica.modelo.Empleado;
import logica.modelo.Gerencial;
import logica.modelo.Operario;

public class FormEmpleados extends javax.swing.JPanel {

    private Empleado empleadoEdicion;
    private boolean guardado;

    public FormEmpleados() {
        initComponents();
        cmbTipo.addActionListener(e -> actualizarExtras());
        actualizarExtras();
    }

    private void actualizarExtras() {
        String tipo = (String) cmbTipo.getSelectedItem();
        lblExtra1.setText("Administrativo".equals(tipo) ? "Tarea" : "Operario".equals(tipo) ? "Sector" : "Cargo");
        lblExtra2.setVisible("Administrativo".equals(tipo));
        txtExtra2.setVisible("Administrativo".equals(tipo));
    }

    public void setEmpleado(Empleado empleado) {
        this.empleadoEdicion = empleado;
        if (empleado == null) {
            return;
        }
        txtNombre.setText(empleado.getNombre());
        txtApellido.setText(empleado.getApellido());
        txtDni.setText(String.valueOf(empleado.getDni()));
        txtDireccion.setText(empleado.getDireccion());
        txtAntiguedad.setText(String.valueOf(empleado.getAntiguedad()));
        txtLegajo.setText(String.valueOf(empleado.getLegajo()));
        try {
            spnFecha.setValue(new SimpleDateFormat("dd/MM/yyyy").parse(empleado.getFechaNacimiento()));
        } catch (Exception ex) {
            spnFecha.setValue(new Date());
        }
        switch (empleado.getTipo()) {
            case "ADMIN" -> {
                Administrativo a = (Administrativo) empleado;
                cmbTipo.setSelectedItem("Administrativo");
                txtExtra1.setText(a.getTarea());
                txtExtra2.setText(a.getArea());
            }
            case "OPER" -> {
                Operario o = (Operario) empleado;
                cmbTipo.setSelectedItem("Operario");
                txtExtra1.setText(o.getSector());
            }
            case "GEREN" -> {
                Gerencial g = (Gerencial) empleado;
                cmbTipo.setSelectedItem("Gerencial");
                txtExtra1.setText(g.getCargo());
            }
            default -> {
            }
        }
        actualizarExtras();
    }

    public boolean isGuardado() {
        return guardado;
    }

    public Empleado getEmpleadoGuardado() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        if (nombre.isEmpty() || apellido.isEmpty()) {
            throw new IllegalArgumentException("Nombre y apellido son obligatorios.");
        }
        int id = empleadoEdicion != null ? empleadoEdicion.getId() : 0;
        int dni = Integer.parseInt(txtDni.getText().trim());
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(spnFecha.getValue());
        int antiguedad = Integer.parseInt(txtAntiguedad.getText().trim());
        int legajo = Integer.parseInt(txtLegajo.getText().trim());
        String tipo = (String) cmbTipo.getSelectedItem();
        if ("Administrativo".equals(tipo)) {
            return new Administrativo(id, nombre, apellido, dni, fecha, txtDireccion.getText().trim(),
                    antiguedad, legajo, txtExtra1.getText().trim(), txtExtra2.getText().trim());
        }
        if ("Operario".equals(tipo)) {
            return new Operario(id, nombre, apellido, dni, fecha, txtDireccion.getText().trim(),
                    antiguedad, legajo, txtExtra1.getText().trim());
        }
        return new Gerencial(id, nombre, apellido, dni, fecha, txtDireccion.getText().trim(),
                antiguedad, legajo, txtExtra1.getText().trim());
    }

    private void guardar() {
        try {
            getEmpleadoGuardado();
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
        lblAntiguedad = new javax.swing.JLabel();
        txtAntiguedad = new javax.swing.JTextField();
        lblLegajo = new javax.swing.JLabel();
        txtLegajo = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblExtra1 = new javax.swing.JLabel();
        txtExtra1 = new javax.swing.JTextField();
        lblExtra2 = new javax.swing.JLabel();
        txtExtra2 = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(410, 505));
        setPreferredSize(new java.awt.Dimension(410, 505));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Formulario Empleados");
        add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 15, 240, 30));

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

        lblAntiguedad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAntiguedad.setForeground(new java.awt.Color(255, 255, 255));
        lblAntiguedad.setText("Antiguedad");
        add(lblAntiguedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 90, 28));

        txtAntiguedad.setBackground(new java.awt.Color(42, 38, 38));
        txtAntiguedad.setForeground(new java.awt.Color(255, 255, 255));
        add(txtAntiguedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 280, 28));

        lblLegajo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLegajo.setForeground(new java.awt.Color(255, 255, 255));
        lblLegajo.setText("Legajo");
        add(lblLegajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 288, 90, 28));

        txtLegajo.setBackground(new java.awt.Color(42, 38, 38));
        txtLegajo.setForeground(new java.awt.Color(255, 255, 255));
        add(txtLegajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 288, 280, 28));

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(255, 255, 255));
        lblTipo.setText("Tipo");
        add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 326, 90, 28));

        cmbTipo.setBackground(new java.awt.Color(42, 38, 38));
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrativo", "Operario", "Gerencial" }));
        add(cmbTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 326, 280, 28));

        lblExtra1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblExtra1.setForeground(new java.awt.Color(255, 255, 255));
        lblExtra1.setText("Tarea");
        add(lblExtra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 364, 90, 28));

        txtExtra1.setBackground(new java.awt.Color(42, 38, 38));
        txtExtra1.setForeground(new java.awt.Color(255, 255, 255));
        add(txtExtra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 364, 280, 28));

        lblExtra2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblExtra2.setForeground(new java.awt.Color(255, 255, 255));
        lblExtra2.setText("Area");
        add(lblExtra2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 402, 90, 28));

        txtExtra2.setBackground(new java.awt.Color(42, 38, 38));
        txtExtra2.setForeground(new java.awt.Color(255, 255, 255));
        add(txtExtra2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 402, 280, 28));

        btnGuardar.setBackground(new java.awt.Color(0, 204, 51));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 110, 40));

        btnCancelar.setBackground(new java.awt.Color(204, 51, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, 110, 40));
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
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblAntiguedad;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblExtra1;
    private javax.swing.JLabel lblExtra2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblLegajo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JSpinner spnFecha;
    private javax.swing.JTextField txtAntiguedad;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtExtra1;
    private javax.swing.JTextField txtExtra2;
    private javax.swing.JTextField txtLegajo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
