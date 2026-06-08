package Interfaz;

import javax.swing.JOptionPane;
import logica.modelo.Componente;
import logica.modelo.DiscoDuro;
import logica.modelo.Fuente;
import logica.modelo.Memoria;
import logica.modelo.PlacaMadre;
import logica.modelo.Procesador;
import logica.modelo.TarjetaRed;

public class FormComponentes extends javax.swing.JPanel {

    private Componente componenteEdicion;
    private boolean guardado;

    public FormComponentes() {
        initComponents();
        sincronizarTabConTipo();
    }

    private void sincronizarTabConTipo() {
        String tipo = (String) cmbTipo.getSelectedItem();
        int indice = switch (tipo) {
            case "Placa madre" -> 0;
            case "Procesador" -> 1;
            case "Memoria" -> 2;
            case "Tarjeta red" -> 3;
            case "Disco duro" -> 4;
            default -> 5;
        };
        tabExtras.setSelectedIndex(indice);
    }

    public void setComponente(Componente c) {
        componenteEdicion = c;
        if (c == null) {
            return;
        }
        txtModelo.setText(c.getModelo());
        txtPrecio.setText(String.valueOf(c.getPrecio()));
        txtDescripcion.setText(c.getDescripcion());
        switch (c.getTipo()) {
            case "PLACA" -> {
                PlacaMadre placa = (PlacaMadre) c;
                cmbTipo.setSelectedItem("Placa madre");
                txtRanuras.setText(String.valueOf(placa.getRanurasMemoria()));
                txtIdsCpu.setText(placa.getIdsProcesadores());
            }
            case "CPU" -> {
                Procesador cpu = (Procesador) c;
                cmbTipo.setSelectedItem("Procesador");
                txtGhz.setText(String.valueOf(cpu.getGhz()));
                txtCache.setText(String.valueOf(cpu.getCacheKb()));
            }
            case "MEM" -> {
                Memoria mem = (Memoria) c;
                cmbTipo.setSelectedItem("Memoria");
                txtTecnologia.setText(mem.getTecnologia());
                txtVelocidad.setText(String.valueOf(mem.getVelocidad()));
                txtTamano.setText(String.valueOf(mem.getTamanoGb()));
            }
            case "RED" -> {
                TarjetaRed red = (TarjetaRed) c;
                cmbTipo.setSelectedItem("Tarjeta red");
                txtVelTrans.setText(String.valueOf(red.getVelocidadTransmision()));
                txtMac.setText(red.getMac());
            }
            case "DISCO" -> {
                DiscoDuro disco = (DiscoDuro) c;
                cmbTipo.setSelectedItem("Disco duro");
                txtRpm.setText(String.valueOf(disco.getRpm()));
                txtCapacidad.setText(String.valueOf(disco.getCapacidadGb()));
                txtTipoDisco.setText(disco.getTipoDisco());
            }
            case "FUENTE" -> {
                Fuente fuente = (Fuente) c;
                cmbTipo.setSelectedItem("Fuente");
                txtWatts.setText(String.valueOf(fuente.getWatts()));
            }
            default -> {
            }
        }
        sincronizarTabConTipo();
    }

    public boolean isGuardado() {
        return guardado;
    }

    public Componente getComponenteGuardado() {
        String modelo = txtModelo.getText().trim();
        if (modelo.isEmpty()) {
            throw new IllegalArgumentException("El modelo es obligatorio.");
        }
        int id = componenteEdicion != null ? componenteEdicion.getId() : 0;
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        String desc = txtDescripcion.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();
        return switch (tipo) {
            case "Placa madre" -> new PlacaMadre(id, modelo, precio, desc,
                    Integer.parseInt(txtRanuras.getText().trim()), txtIdsCpu.getText().trim());
            case "Procesador" -> new Procesador(id, modelo, precio, desc,
                    Double.parseDouble(txtGhz.getText().trim()), Integer.parseInt(txtCache.getText().trim()));
            case "Memoria" -> new Memoria(id, modelo, precio, desc, txtTecnologia.getText().trim(),
                    Integer.parseInt(txtVelocidad.getText().trim()), Integer.parseInt(txtTamano.getText().trim()));
            case "Tarjeta red" -> new TarjetaRed(id, modelo, precio, desc,
                    Integer.parseInt(txtVelTrans.getText().trim()), txtMac.getText().trim());
            case "Disco duro" -> new DiscoDuro(id, modelo, precio, desc,
                    Integer.parseInt(txtRpm.getText().trim()), Integer.parseInt(txtCapacidad.getText().trim()),
                    txtTipoDisco.getText().trim());
            default -> new Fuente(id, modelo, precio, desc, Integer.parseInt(txtWatts.getText().trim()));
        };
    }

    private void guardar() {
        try {
            getComponenteGuardado();
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
        lblModelo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        tabExtras = new javax.swing.JTabbedPane();
        panelPlaca = new javax.swing.JPanel();
        lblRanuras = new javax.swing.JLabel();
        txtRanuras = new javax.swing.JTextField();
        lblIdsCpu = new javax.swing.JLabel();
        txtIdsCpu = new javax.swing.JTextField();
        panelCpu = new javax.swing.JPanel();
        lblGhz = new javax.swing.JLabel();
        txtGhz = new javax.swing.JTextField();
        lblCache = new javax.swing.JLabel();
        txtCache = new javax.swing.JTextField();
        panelMem = new javax.swing.JPanel();
        lblTecnologia = new javax.swing.JLabel();
        txtTecnologia = new javax.swing.JTextField();
        lblVelocidad = new javax.swing.JLabel();
        txtVelocidad = new javax.swing.JTextField();
        lblTamano = new javax.swing.JLabel();
        txtTamano = new javax.swing.JTextField();
        panelRed = new javax.swing.JPanel();
        lblVelTrans = new javax.swing.JLabel();
        txtVelTrans = new javax.swing.JTextField();
        lblMac = new javax.swing.JLabel();
        txtMac = new javax.swing.JTextField();
        panelDisco = new javax.swing.JPanel();
        lblRpm = new javax.swing.JLabel();
        txtRpm = new javax.swing.JTextField();
        lblCapacidad = new javax.swing.JLabel();
        txtCapacidad = new javax.swing.JTextField();
        lblTipoDisco = new javax.swing.JLabel();
        txtTipoDisco = new javax.swing.JTextField();
        panelFuente = new javax.swing.JPanel();
        lblWatts = new javax.swing.JLabel();
        txtWatts = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Formulario Componentes");
        add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 260, 30));

        lblModelo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("Modelo");
        add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, 28));

        txtModelo.setBackground(new java.awt.Color(42, 38, 38));
        add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 280, 28));

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(255, 255, 255));
        lblPrecio.setText("Precio");
        add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 88, 90, 28));

        txtPrecio.setBackground(new java.awt.Color(42, 38, 38));
        add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 88, 280, 28));

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setText("Descripcion");
        add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 126, 90, 28));

        txtDescripcion.setBackground(new java.awt.Color(42, 38, 38));
        add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 126, 280, 28));

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(255, 255, 255));
        lblTipo.setText("Tipo");
        add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 164, 90, 28));

        cmbTipo.setBackground(new java.awt.Color(42, 38, 38));
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Placa madre", "Procesador", "Memoria", "Tarjeta red", "Disco duro", "Fuente" }));
        cmbTipo.addActionListener(this::cmbTipoActionPerformed);
        add(cmbTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 164, 280, 28));

        tabExtras.setBackground(new java.awt.Color(45, 45, 45));

        panelPlaca.setBackground(new java.awt.Color(45, 45, 45));
        panelPlaca.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRanuras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRanuras.setForeground(new java.awt.Color(255, 255, 255));
        lblRanuras.setText("Ranuras memoria");
        panelPlaca.add(lblRanuras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 110, 25));

        txtRanuras.setBackground(new java.awt.Color(42, 38, 38));
        panelPlaca.add(txtRanuras, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 220, 28));

        lblIdsCpu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIdsCpu.setForeground(new java.awt.Color(255, 255, 255));
        lblIdsCpu.setText("IDs CPUs (1,2)");
        panelPlaca.add(lblIdsCpu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 110, 25));

        txtIdsCpu.setBackground(new java.awt.Color(42, 38, 38));
        panelPlaca.add(txtIdsCpu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, 220, 28));

        tabExtras.addTab("tab1", panelPlaca);

        panelCpu.setBackground(new java.awt.Color(45, 45, 45));
        panelCpu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblGhz.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblGhz.setForeground(new java.awt.Color(255, 255, 255));
        lblGhz.setText("GHz");
        panelCpu.add(lblGhz, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 110, 25));

        txtGhz.setBackground(new java.awt.Color(42, 38, 38));
        panelCpu.add(txtGhz, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 220, 28));

        lblCache.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCache.setForeground(new java.awt.Color(255, 255, 255));
        lblCache.setText("Cache KB");
        panelCpu.add(lblCache, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 110, 25));

        txtCache.setBackground(new java.awt.Color(42, 38, 38));
        panelCpu.add(txtCache, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, 220, 28));

        tabExtras.addTab("tab2", panelCpu);

        panelMem.setBackground(new java.awt.Color(45, 45, 45));
        panelMem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTecnologia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTecnologia.setForeground(new java.awt.Color(255, 255, 255));
        lblTecnologia.setText("Tecnologia");
        panelMem.add(lblTecnologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 110, 25));

        txtTecnologia.setBackground(new java.awt.Color(42, 38, 38));
        panelMem.add(txtTecnologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 220, 28));

        lblVelocidad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVelocidad.setForeground(new java.awt.Color(255, 255, 255));
        lblVelocidad.setText("Velocidad");
        panelMem.add(lblVelocidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 110, 25));

        txtVelocidad.setBackground(new java.awt.Color(42, 38, 38));
        panelMem.add(txtVelocidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, 220, 28));

        lblTamano.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTamano.setForeground(new java.awt.Color(255, 255, 255));
        lblTamano.setText("Tamano GB");
        panelMem.add(lblTamano, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 95, 110, 25));

        txtTamano.setBackground(new java.awt.Color(42, 38, 38));
        panelMem.add(txtTamano, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 95, 220, 28));

        tabExtras.addTab("tab3", panelMem);

        panelRed.setBackground(new java.awt.Color(45, 45, 45));
        panelRed.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVelTrans.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVelTrans.setForeground(new java.awt.Color(255, 255, 255));
        lblVelTrans.setText("Vel. transmision");
        panelRed.add(lblVelTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 110, 25));

        txtVelTrans.setBackground(new java.awt.Color(42, 38, 38));
        panelRed.add(txtVelTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 220, 28));

        lblMac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMac.setForeground(new java.awt.Color(255, 255, 255));
        lblMac.setText("MAC");
        panelRed.add(lblMac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 110, 25));

        txtMac.setBackground(new java.awt.Color(42, 38, 38));
        panelRed.add(txtMac, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, 220, 28));

        tabExtras.addTab("tab4", panelRed);

        panelDisco.setBackground(new java.awt.Color(45, 45, 45));
        panelDisco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRpm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRpm.setForeground(new java.awt.Color(255, 255, 255));
        lblRpm.setText("RPM");
        panelDisco.add(lblRpm, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 110, 25));

        txtRpm.setBackground(new java.awt.Color(42, 38, 38));
        panelDisco.add(txtRpm, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 220, 28));

        lblCapacidad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCapacidad.setForeground(new java.awt.Color(255, 255, 255));
        lblCapacidad.setText("Capacidad GB");
        panelDisco.add(lblCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 110, 25));

        txtCapacidad.setBackground(new java.awt.Color(42, 38, 38));
        panelDisco.add(txtCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, 220, 28));

        lblTipoDisco.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipoDisco.setForeground(new java.awt.Color(255, 255, 255));
        lblTipoDisco.setText("Tipo ssd/hdd");
        panelDisco.add(lblTipoDisco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 95, 110, 25));

        txtTipoDisco.setBackground(new java.awt.Color(42, 38, 38));
        panelDisco.add(txtTipoDisco, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 95, 220, 28));

        tabExtras.addTab("tab5", panelDisco);

        panelFuente.setBackground(new java.awt.Color(45, 45, 45));
        panelFuente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblWatts.setBackground(new java.awt.Color(255, 255, 255));
        lblWatts.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblWatts.setForeground(new java.awt.Color(255, 255, 255));
        lblWatts.setText("Watts");
        panelFuente.add(lblWatts, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 110, 25));

        txtWatts.setBackground(new java.awt.Color(42, 38, 38));
        panelFuente.add(txtWatts, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 220, 28));

        tabExtras.addTab("tab6", panelFuente);

        add(tabExtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 390, 150));

        btnGuardar.setBackground(new java.awt.Color(0, 204, 51));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 110, 40));

        btnCancelar.setBackground(new java.awt.Color(204, 51, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 110, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        sincronizarTabConTipo();
    }//GEN-LAST:event_cmbTipoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblCache;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblGhz;
    private javax.swing.JLabel lblIdsCpu;
    private javax.swing.JLabel lblMac;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblRanuras;
    private javax.swing.JLabel lblRpm;
    private javax.swing.JLabel lblTamano;
    private javax.swing.JLabel lblTecnologia;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTipoDisco;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVelTrans;
    private javax.swing.JLabel lblVelocidad;
    private javax.swing.JLabel lblWatts;
    private javax.swing.JPanel panelCpu;
    private javax.swing.JPanel panelDisco;
    private javax.swing.JPanel panelFuente;
    private javax.swing.JPanel panelMem;
    private javax.swing.JPanel panelPlaca;
    private javax.swing.JPanel panelRed;
    private javax.swing.JTabbedPane tabExtras;
    private javax.swing.JTextField txtCache;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtGhz;
    private javax.swing.JTextField txtIdsCpu;
    private javax.swing.JTextField txtMac;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtRanuras;
    private javax.swing.JTextField txtRpm;
    private javax.swing.JTextField txtTamano;
    private javax.swing.JTextField txtTecnologia;
    private javax.swing.JTextField txtTipoDisco;
    private javax.swing.JTextField txtVelTrans;
    private javax.swing.JTextField txtVelocidad;
    private javax.swing.JTextField txtWatts;
    // End of variables declaration//GEN-END:variables
}
