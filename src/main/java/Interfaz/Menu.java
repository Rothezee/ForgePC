/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaz;

import logica.administradores.SistemaForge;
import excepciones.IdDuplicadoException;
import excepciones.PersistenciaException;
import excepciones.RegistroNoEncontradoException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.modelo.Cliente;
import logica.modelo.Componente;
import logica.modelo.DiscoDuro;
import logica.modelo.Empleado;
import logica.modelo.Fuente;
import logica.modelo.Memoria;
import logica.modelo.PC;
import logica.modelo.PlacaMadre;
import logica.modelo.Procesador;
import logica.modelo.TarjetaRed;

/**
 * Ventana principal del sistema ForgePCs.
 */
public class Menu extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;

    private final SistemaForge sistema = SistemaForge.getInstancia();

    @SuppressWarnings("this-escape")
    public Menu() {
        initComponents();
        if (!java.beans.Beans.isDesignTime()) {
            try {
                sistema.cargarTodo();
            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Carga de datos", JOptionPane.WARNING_MESSAGE);
            }
            refrescarContadoresInicio();
            refrescarTablaClientes();
            refrescarTablaEmpleados();
            refrescarTablaComponentes();
            refrescarTablaPCs();
            mostrarPanel("inicio");
        }
    }

    private void mostrarPanel(String nombre) {
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, nombre);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void irAComponentes() {
        mostrarPanel("componentes");
        refrescarTablaComponentes();
    }

    private void persistir() {
        try {
            sistema.guardarTodo();
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarContadoresInicio() {
        lblContadorClientesValor.setText(String.valueOf(sistema.getAdminClientes().cantidad()));
        lblContadorEmpleadosValor.setText(String.valueOf(sistema.getAdminEmpleados().cantidad()));
        lblContadorComponentesValor.setText(String.valueOf(sistema.getAdminComponentes().cantidad()));
    }

    private void limpiarTabla(javax.swing.JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
    }

    private void refrescarTablaClientes() {
        limpiarTabla(tablaClientes);
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        for (Cliente c : sistema.getAdminClientes().listarOrdenados()) {
            modelo.addRow(new Object[]{
                c.getId(), c.getNombre(), c.getApellido(), c.getDni(), c.getFechaNacimiento(),
                c.getDireccion(), c.getMail(), c.getCuil(), c.getNacionalidad()
            });
        }
    }

    private void refrescarTablaEmpleados() {
        limpiarTabla(tablaEmpleados);
        DefaultTableModel modelo = (DefaultTableModel) tablaEmpleados.getModel();
        for (Empleado e : sistema.getAdminEmpleados().listarTodos()) {
            modelo.addRow(new Object[]{
                e.getId(), e.getNombre(), e.getApellido(), e.getDni(), e.getDireccion(),
                e.getAntiguedad(), e.getFechaNacimiento(), e.getLegajo()
            });
        }
    }

    private void refrescarTablaComponentes() {
        limpiarTabla(tablaComponentes);
        DefaultTableModel modelo = (DefaultTableModel) tablaComponentes.getModel();
        modelo.setColumnIdentifiers(new String[]{"id", "Tipo", "Modelo", "Precio", "Descripcion", "Extra 1", "Extra 2"});
        for (Componente c : sistema.getAdminComponentes().listarTodos()) {
            Object[] fila = extrasComponente(c);
            modelo.addRow(new Object[]{c.getId(), c.getTipo(), c.getModelo(), c.getPrecio(), c.getDescripcion(),
                fila[0], fila[1]});
        }
    }

    private Object[] extrasComponente(Componente c) {
        return switch (c.getTipo()) {
            case "PLACA" -> {
                PlacaMadre placa = (PlacaMadre) c;
                yield new Object[]{placa.getRanurasMemoria(), placa.getIdsProcesadores()};
            }
            case "CPU" -> {
                Procesador cpu = (Procesador) c;
                yield new Object[]{cpu.getGhz(), cpu.getCacheKb()};
            }
            case "MEM" -> {
                Memoria mem = (Memoria) c;
                yield new Object[]{mem.getTecnologia(), mem.getTamanoGb() + " GB"};
            }
            case "RED" -> {
                TarjetaRed red = (TarjetaRed) c;
                yield new Object[]{red.getVelocidadTransmision(), red.getMac()};
            }
            case "DISCO" -> {
                DiscoDuro disco = (DiscoDuro) c;
                yield new Object[]{disco.getRpm(), disco.getCapacidadGb() + " " + disco.getTipoDisco()};
            }
            case "FUENTE" -> {
                Fuente fuente = (Fuente) c;
                yield new Object[]{fuente.getWatts() + " W", ""};
            }
            default -> new Object[]{"", ""};
        };
    }

    private void refrescarTablaPCs() {
        limpiarTabla(tablaPCs);
        DefaultTableModel modelo = (DefaultTableModel) tablaPCs.getModel();
        for (PC pc : sistema.getAdminPCs().listar()) {
            String ids = pc.getIdsComponentes().toString().replace("[", "").replace("]", "");
            modelo.addRow(new Object[]{pc.getId(), pc.getIdCliente(), pc.getFechaArmado(), ids});
        }
    }

    private Integer idSeleccionado(javax.swing.JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return null;
        }
        return (Integer) tabla.getValueAt(fila, 0);
    }

    private void abrirDialogo(javax.swing.JPanel form, String titulo, int ancho, int alto) { //la idea era mostrar un titulo diferente segun la operacion. 
        JDialog dialog = new JDialog(this, titulo, true);
        dialog.getContentPane().add(form);
        dialog.pack();
        dialog.setSize(ancho, alto);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void abrirFormCliente(Cliente cliente) {
        FormClientes form = new FormClientes();
        form.setCliente(cliente);
        abrirDialogo(form, cliente == null ? "Nuevo cliente" : "Editar cliente", 420, 520);
        if (form.isGuardado()) {
            try {
                Cliente guardado = form.getClienteGuardado();
                if (cliente == null) {
                    sistema.getAdminClientes().alta(guardado);
                } else {
                    sistema.getAdminClientes().actualizar(guardado);
                }
                persistir();
                refrescarTablaClientes();
                refrescarContadoresInicio();
            } catch (IdDuplicadoException | RegistroNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirFormEmpleado(Empleado empleado) {
        FormEmpleados form = new FormEmpleados();
        form.setEmpleado(empleado);
        abrirDialogo(form, empleado == null ? "Nuevo empleado" : "Editar empleado", 400, 480);
        if (form.isGuardado()) {
            try {
                Empleado guardado = form.getEmpleadoGuardado();
                if (empleado == null) {
                    sistema.getAdminEmpleados().alta(guardado);
                } else {
                    sistema.getAdminEmpleados().actualizar(guardado);
                }
                persistir();
                refrescarTablaEmpleados();
                refrescarContadoresInicio();
            } catch (IdDuplicadoException | RegistroNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirFormComponente(Componente componente) {
        FormComponentes form = new FormComponentes();
        form.setComponente(componente);
        abrirDialogo(form, componente == null ? "Nuevo componente" : "Editar componente", 420, 520);
        if (form.isGuardado()) {
            try {
                Componente guardado = form.getComponenteGuardado();
                if (componente == null) {
                    sistema.getAdminComponentes().alta(guardado);
                } else {
                    sistema.getAdminComponentes().actualizar(guardado);
                }
                persistir();
                refrescarTablaComponentes();
                refrescarContadoresInicio();
            } catch (IdDuplicadoException | RegistroNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirFormPC() {
        if (sistema.getAdminClientes().listarOrdenados().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe registrar al menos un cliente antes de armar una PC.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (sistema.getAdminComponentes().listarTodos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe registrar al menos un componente antes de armar una PC.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        FormPC form = new FormPC();
        abrirDialogo(form, "Construir PC", 450, 480);
        if (form.isGuardado()) {
            try {
                sistema.getAdminPCs().construir(form.getPCGuardada(),
                        sistema.getAdminClientes(), sistema.getAdminComponentes());
                persistir();
                refrescarTablaPCs();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuLateral = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnInicio = new javax.swing.JButton();
        btnClientes1 = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnComponentes = new javax.swing.JButton();
        btnPCs = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panelContenido = new javax.swing.JPanel();
        cardInicio = new javax.swing.JPanel();
        lblInicioTitulo = new javax.swing.JLabel();
        lblInicioSubtitulo = new javax.swing.JLabel();
        sepInicio = new javax.swing.JSeparator();
        lblInicioTexto = new javax.swing.JLabel();
        lblAccesosTitulo = new javax.swing.JLabel();
        panelAccesosRapidos = new javax.swing.JPanel();
        btnAccClientes = new javax.swing.JButton();
        btnAccEmpleados = new javax.swing.JButton();
        btnAccComponentes = new javax.swing.JButton();
        btnAccSalir = new javax.swing.JButton();
        lblResumenTitulo = new javax.swing.JLabel();
        panelResumenContadores = new javax.swing.JPanel();
        panelContadorClientes = new javax.swing.JPanel();
        lblContadorClientesNombre = new javax.swing.JLabel();
        lblContadorClientesValor = new javax.swing.JLabel();
        lblContadorClientesDetalle = new javax.swing.JLabel();
        panelContadorEmpleados = new javax.swing.JPanel();
        lblContadorEmpleadosNombre = new javax.swing.JLabel();
        lblContadorEmpleadosValor = new javax.swing.JLabel();
        lblContadorEmpleadosDetalle = new javax.swing.JLabel();
        panelContadorComponentes = new javax.swing.JPanel();
        lblContadorComponentesNombre = new javax.swing.JLabel();
        lblContadorComponentesValor = new javax.swing.JLabel();
        lblContadorComponentesDetalle = new javax.swing.JLabel();
        cardClientes = new javax.swing.JPanel();
        lblClientesTitulo = new javax.swing.JLabel();
        sepClientes = new javax.swing.JSeparator();
        scrollClientes = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnAñadirCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        cardEmpleados = new javax.swing.JPanel();
        scrollEmpleados = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        sepEmpleados = new javax.swing.JSeparator();
        lblEmpleadosTitulo = new javax.swing.JLabel();
        btnEditarEmpleado = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        btnAñadirEmpleado = new javax.swing.JButton();
        cardComponentes = new javax.swing.JPanel();
        scrollComponentes = new javax.swing.JScrollPane();
        tablaComponentes = new javax.swing.JTable();
        btnAñadirComponente = new javax.swing.JButton();
        btnEditarComponente = new javax.swing.JButton();
        btnEliminarComponente = new javax.swing.JButton();
        sepComponentes = new javax.swing.JSeparator();
        lblComponentesTitulo = new javax.swing.JLabel();
        cardPCs = new javax.swing.JPanel();
        lblPCsTitulo = new javax.swing.JLabel();
        sepPCs = new javax.swing.JSeparator();
        btnConstruirPC = new javax.swing.JButton();
        scrollPCs = new javax.swing.JScrollPane();
        tablaPCs = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuLateral.setBackground(java.awt.Color.gray);
        menuLateral.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jLabel1.setBackground(java.awt.Color.gray);
        jLabel1.setFont(new java.awt.Font("Cooper Black", 3, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ForgePCs");
        jLabel1.setOpaque(true);

        jSeparator1.setBackground(new java.awt.Color(60, 60, 60));
        jSeparator1.setForeground(new java.awt.Color(60, 60, 60));

        btnInicio.setBackground(new java.awt.Color(102, 102, 102));
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("Inicio");
        btnInicio.setBorder(null);
        btnInicio.setFocusPainted(false);
        btnInicio.setOpaque(true);
        btnInicio.addActionListener(this::btnInicioActionPerformed);

        btnClientes1.setBackground(new java.awt.Color(102, 102, 102));
        btnClientes1.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes1.setText("Clientes");
        btnClientes1.setBorder(null);
        btnClientes1.setFocusPainted(false);
        btnClientes1.setOpaque(true);
        btnClientes1.addActionListener(this::btnClientes1ActionPerformed);

        btnEmpleados.setBackground(new java.awt.Color(102, 102, 102));
        btnEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnEmpleados.setText("Empleados");
        btnEmpleados.setBorder(null);
        btnEmpleados.setFocusPainted(false);
        btnEmpleados.setOpaque(true);
        btnEmpleados.addActionListener(this::btnEmpleadosActionPerformed);

        btnComponentes.setBackground(new java.awt.Color(102, 102, 102));
        btnComponentes.setForeground(new java.awt.Color(255, 255, 255));
        btnComponentes.setText("Componentes");
        btnComponentes.setBorder(null);
        btnComponentes.setFocusPainted(false);
        btnComponentes.setOpaque(true);
        btnComponentes.addActionListener(this::btnComponentesActionPerformed);

        btnPCs.setBackground(new java.awt.Color(102, 102, 102));
        btnPCs.setForeground(new java.awt.Color(255, 255, 255));
        btnPCs.setText("PCs");
        btnPCs.setBorder(null);
        btnPCs.setFocusPainted(false);
        btnPCs.setOpaque(true);
        btnPCs.addActionListener(this::btnPCsActionPerformed);

        btnSalir.setBackground(new java.awt.Color(102, 102, 102));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.setBorder(null);
        btnSalir.setFocusPainted(false);
        btnSalir.setOpaque(true);
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        javax.swing.GroupLayout menuLateralLayout = new javax.swing.GroupLayout(menuLateral);
        menuLateral.setLayout(menuLateralLayout);
        menuLateralLayout.setHorizontalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
            .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClientes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnComponentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPCs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        menuLateralLayout.setVerticalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLateralLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPCs, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 347, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        getContentPane().add(menuLateral, java.awt.BorderLayout.LINE_START);

        panelContenido.setBackground(new java.awt.Color(51, 51, 51));
        panelContenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelContenido.setLayout(new java.awt.CardLayout());

        cardInicio.setBackground(new java.awt.Color(51, 51, 51));
        cardInicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lblInicioTitulo.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblInicioTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblInicioTitulo.setText("ForgePCs");

        lblInicioSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInicioSubtitulo.setText("Sistema de gestion");

        sepInicio.setBackground(new java.awt.Color(70, 70, 70));
        sepInicio.setForeground(new java.awt.Color(70, 70, 70));

        lblInicioTexto.setForeground(new java.awt.Color(102, 102, 102));
        lblInicioTexto.setText("<html><div style='width:520px;'>Fabricacion de componentes y armado de computadoras personalizadas.</div></html>");

        lblAccesosTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblAccesosTitulo.setText("Accesos rapidos");

        panelAccesosRapidos.setOpaque(false);
        panelAccesosRapidos.setLayout(new java.awt.GridLayout(2, 2, 12, 12));

        btnAccClientes.setBackground(new java.awt.Color(102, 102, 102));
        btnAccClientes.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAccClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnAccClientes.setText("Clientes");
        btnAccClientes.setBorder(null);
        btnAccClientes.setFocusPainted(false);
        btnAccClientes.setOpaque(true);
        btnAccClientes.addActionListener(this::btnAccClientesActionPerformed);
        panelAccesosRapidos.add(btnAccClientes);

        btnAccEmpleados.setBackground(new java.awt.Color(102, 102, 102));
        btnAccEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAccEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnAccEmpleados.setText("Empleados");
        btnAccEmpleados.setBorder(null);
        btnAccEmpleados.setFocusPainted(false);
        btnAccEmpleados.setOpaque(true);
        btnAccEmpleados.addActionListener(this::btnAccEmpleadosActionPerformed);
        panelAccesosRapidos.add(btnAccEmpleados);

        btnAccComponentes.setBackground(new java.awt.Color(102, 102, 102));
        btnAccComponentes.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAccComponentes.setForeground(new java.awt.Color(255, 255, 255));
        btnAccComponentes.setText("Componentes");
        btnAccComponentes.setBorder(null);
        btnAccComponentes.setFocusPainted(false);
        btnAccComponentes.setOpaque(true);
        btnAccComponentes.addActionListener(this::btnAccComponentesActionPerformed);
        panelAccesosRapidos.add(btnAccComponentes);

        btnAccSalir.setBackground(new java.awt.Color(102, 102, 102));
        btnAccSalir.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAccSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnAccSalir.setText("Salir");
        btnAccSalir.setBorder(null);
        btnAccSalir.setFocusPainted(false);
        btnAccSalir.setOpaque(true);
        btnAccSalir.addActionListener(this::btnAccSalirActionPerformed);
        panelAccesosRapidos.add(btnAccSalir);

        lblResumenTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblResumenTitulo.setText("Resumen del sistema");

        panelResumenContadores.setOpaque(false);
        panelResumenContadores.setLayout(new java.awt.GridLayout(1, 3, 16, 0));

        panelContadorClientes.setBackground(new java.awt.Color(40, 40, 40));
        panelContadorClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 80, 80)));
        panelContadorClientes.setLayout(new java.awt.BorderLayout());

        lblContadorClientesNombre.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContadorClientesNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorClientesNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesNombre.setText("Clientes");
        panelContadorClientes.add(lblContadorClientesNombre, java.awt.BorderLayout.PAGE_START);

        lblContadorClientesValor.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorClientesValor.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorClientesValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesValor.setText("--");
        panelContadorClientes.add(lblContadorClientesValor, java.awt.BorderLayout.CENTER);

        lblContadorClientesDetalle.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorClientesDetalle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesDetalle.setText("Registrados");
        panelContadorClientes.add(lblContadorClientesDetalle, java.awt.BorderLayout.PAGE_END);

        panelResumenContadores.add(panelContadorClientes);

        panelContadorEmpleados.setBackground(new java.awt.Color(40, 40, 40));
        panelContadorEmpleados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 80, 80)));
        panelContadorEmpleados.setLayout(new java.awt.BorderLayout());

        lblContadorEmpleadosNombre.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContadorEmpleadosNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorEmpleadosNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorEmpleadosNombre.setText("Empleados");
        panelContadorEmpleados.add(lblContadorEmpleadosNombre, java.awt.BorderLayout.PAGE_START);

        lblContadorEmpleadosValor.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorEmpleadosValor.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorEmpleadosValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorEmpleadosValor.setText("--");
        panelContadorEmpleados.add(lblContadorEmpleadosValor, java.awt.BorderLayout.CENTER);

        lblContadorEmpleadosDetalle.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorEmpleadosDetalle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorEmpleadosDetalle.setText("Activos");
        panelContadorEmpleados.add(lblContadorEmpleadosDetalle, java.awt.BorderLayout.PAGE_END);

        panelResumenContadores.add(panelContadorEmpleados);

        panelContadorComponentes.setBackground(new java.awt.Color(40, 40, 40));
        panelContadorComponentes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 80, 80)));
        panelContadorComponentes.setLayout(new java.awt.BorderLayout());

        lblContadorComponentesNombre.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContadorComponentesNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorComponentesNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorComponentesNombre.setText("Componentes");
        panelContadorComponentes.add(lblContadorComponentesNombre, java.awt.BorderLayout.PAGE_START);

        lblContadorComponentesValor.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorComponentesValor.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorComponentesValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorComponentesValor.setText("--");
        panelContadorComponentes.add(lblContadorComponentesValor, java.awt.BorderLayout.CENTER);

        lblContadorComponentesDetalle.setForeground(new java.awt.Color(255, 255, 255));
        lblContadorComponentesDetalle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorComponentesDetalle.setText("En stock");
        panelContadorComponentes.add(lblContadorComponentesDetalle, java.awt.BorderLayout.PAGE_END);

        panelResumenContadores.add(panelContadorComponentes);

        javax.swing.GroupLayout cardInicioLayout = new javax.swing.GroupLayout(cardInicio);
        cardInicio.setLayout(cardInicioLayout);
        cardInicioLayout.setHorizontalGroup(
            cardInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInicioTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInicioSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sepInicio)
                    .addComponent(lblInicioTexto)
                    .addComponent(lblAccesosTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAccesosRapidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblResumenTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelResumenContadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cardInicioLayout.setVerticalGroup(
            cardInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInicioTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInicioSubtitulo)
                .addGap(18, 18, 18)
                .addComponent(sepInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblInicioTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblAccesosTitulo)
                .addGap(12, 12, 12)
                .addComponent(panelAccesosRapidos, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblResumenTitulo)
                .addGap(12, 12, 12)
                .addComponent(panelResumenContadores, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContenido.add(cardInicio, "inicio");

        cardClientes.setBackground(new java.awt.Color(51, 51, 51));
        cardClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lblClientesTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClientesTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblClientesTitulo.setText("Clientes");

        sepClientes.setBackground(new java.awt.Color(70, 70, 70));
        sepClientes.setForeground(new java.awt.Color(70, 70, 70));

        tablaClientes.setForeground(new java.awt.Color(0, 0, 0));
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nombre", "Apellido", "DNI", "Fecha de Nac", "Direccion", "Mail", "Cuil", "Nacionalidad"
            }
        ) {
            Class<?>[] types = new Class<?>[] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaClientes.setSelectionForeground(java.awt.Color.white);
        scrollClientes.setViewportView(tablaClientes);
        if (tablaClientes.getColumnModel().getColumnCount() > 0) {
            tablaClientes.getColumnModel().getColumn(0).setResizable(false);
            tablaClientes.getColumnModel().getColumn(1).setResizable(false);
            tablaClientes.getColumnModel().getColumn(2).setResizable(false);
            tablaClientes.getColumnModel().getColumn(3).setResizable(false);
            tablaClientes.getColumnModel().getColumn(4).setResizable(false);
            tablaClientes.getColumnModel().getColumn(5).setResizable(false);
            tablaClientes.getColumnModel().getColumn(6).setResizable(false);
            tablaClientes.getColumnModel().getColumn(7).setResizable(false);
            tablaClientes.getColumnModel().getColumn(8).setResizable(false);
        }

        btnAñadirCliente.setBackground(new java.awt.Color(0, 204, 51));
        btnAñadirCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAñadirCliente.setForeground(new java.awt.Color(0, 0, 0));
        btnAñadirCliente.setText("Añadir +");
        btnAñadirCliente.setBorder(null);
        btnAñadirCliente.addActionListener(this::btnAñadirClienteActionPerformed);

        btnEditarCliente.setBackground(new java.awt.Color(204, 204, 0));
        btnEditarCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditarCliente.setForeground(new java.awt.Color(0, 0, 0));
        btnEditarCliente.setText("Editar :");
        btnEditarCliente.setBorder(null);
        btnEditarCliente.addActionListener(this::btnEditarClienteActionPerformed);

        btnEliminarCliente.setBackground(new java.awt.Color(204, 51, 0));
        btnEliminarCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarCliente.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminarCliente.setText("Eliminar -");
        btnEliminarCliente.setBorder(null);
        btnEliminarCliente.addActionListener(this::btnEliminarClienteActionPerformed);

        javax.swing.GroupLayout cardClientesLayout = new javax.swing.GroupLayout(cardClientes);
        cardClientes.setLayout(cardClientesLayout);
        cardClientesLayout.setHorizontalGroup(
            cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardClientesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblClientesTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(cardClientesLayout.createSequentialGroup()
                        .addGroup(cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sepClientes)
                            .addGroup(cardClientesLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnAñadirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(scrollClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)))
        );
        cardClientesLayout.setVerticalGroup(
            cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardClientesLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lblClientesTitulo)
                .addGap(18, 18, 18)
                .addComponent(sepClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAñadirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        panelContenido.add(cardClientes, "clientes");

        cardEmpleados.setBackground(new java.awt.Color(51, 51, 51));
        cardEmpleados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nombre", "Apellido", "DNI", "Direccion", "Antiguedad", "Fecha de Nacimiento", "Legajo"
            }
        ) {
            Class<?>[] types = new Class<?>[] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollEmpleados.setViewportView(tablaEmpleados);
        if (tablaEmpleados.getColumnModel().getColumnCount() > 0) {
            tablaEmpleados.getColumnModel().getColumn(0).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(1).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(2).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(3).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(4).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(5).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(6).setResizable(false);
            tablaEmpleados.getColumnModel().getColumn(7).setResizable(false);
        }

        sepEmpleados.setBackground(new java.awt.Color(80, 80, 80));

        lblEmpleadosTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmpleadosTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpleadosTitulo.setText("Empleados");

        btnEditarEmpleado.setBackground(new java.awt.Color(204, 204, 0));
        btnEditarEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditarEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        btnEditarEmpleado.setText("Editar :");
        btnEditarEmpleado.setBorder(null);
        btnEditarEmpleado.addActionListener(this::btnEditarEmpleadoActionPerformed);

        btnEliminarEmpleado.setBackground(new java.awt.Color(204, 51, 0));
        btnEliminarEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminarEmpleado.setText("Eliminar -");
        btnEliminarEmpleado.setBorder(null);
        btnEliminarEmpleado.addActionListener(this::btnEliminarEmpleadoActionPerformed);

        btnAñadirEmpleado.setBackground(new java.awt.Color(0, 204, 51));
        btnAñadirEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAñadirEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        btnAñadirEmpleado.setText("Añadir +");
        btnAñadirEmpleado.setBorder(null);
        btnAñadirEmpleado.addActionListener(this::btnAñadirEmpleadoActionPerformed);

        javax.swing.GroupLayout cardEmpleadosLayout = new javax.swing.GroupLayout(cardEmpleados);
        cardEmpleados.setLayout(cardEmpleadosLayout);
        cardEmpleadosLayout.setHorizontalGroup(
            cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sepEmpleados)
                    .addComponent(scrollEmpleados)
                    .addGroup(cardEmpleadosLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cardEmpleadosLayout.createSequentialGroup()
                                .addComponent(lblEmpleadosTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(cardEmpleadosLayout.createSequentialGroup()
                                .addComponent(btnAñadirEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        cardEmpleadosLayout.setVerticalGroup(
            cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardEmpleadosLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblEmpleadosTitulo)
                .addGap(18, 18, 18)
                .addComponent(sepEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAñadirEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        panelContenido.add(cardEmpleados, "empleados");

        cardComponentes.setBackground(new java.awt.Color(51, 51, 51));
        cardComponentes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        tablaComponentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Tipo", "Modelo", "Precio", "Descripcion", "Extra 1", "Extra 2"
            }
        ) {
            Class<?>[] types = new Class<?>[] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaComponentes.setSelectionBackground(new java.awt.Color(30, 30, 30));
        scrollComponentes.setViewportView(tablaComponentes);
        if (tablaComponentes.getColumnModel().getColumnCount() > 0) {
            tablaComponentes.getColumnModel().getColumn(0).setResizable(false);
            tablaComponentes.getColumnModel().getColumn(1).setResizable(false);
            tablaComponentes.getColumnModel().getColumn(2).setResizable(false);
            tablaComponentes.getColumnModel().getColumn(3).setResizable(false);
            tablaComponentes.getColumnModel().getColumn(4).setResizable(false);
            tablaComponentes.getColumnModel().getColumn(5).setResizable(false);
            tablaComponentes.getColumnModel().getColumn(6).setResizable(false);
        }

        btnAñadirComponente.setBackground(new java.awt.Color(0, 204, 51));
        btnAñadirComponente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAñadirComponente.setForeground(new java.awt.Color(0, 0, 0));
        btnAñadirComponente.setText("Añadir +");
        btnAñadirComponente.setBorder(null);
        btnAñadirComponente.addActionListener(this::btnAñadirComponenteActionPerformed);

        btnEditarComponente.setBackground(new java.awt.Color(204, 204, 0));
        btnEditarComponente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditarComponente.setForeground(new java.awt.Color(0, 0, 0));
        btnEditarComponente.setText("Editar :");
        btnEditarComponente.setBorder(null);
        btnEditarComponente.addActionListener(this::btnEditarComponenteActionPerformed);

        btnEliminarComponente.setBackground(new java.awt.Color(204, 51, 0));
        btnEliminarComponente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarComponente.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminarComponente.setText("Eliminar -");
        btnEliminarComponente.setBorder(null);
        btnEliminarComponente.addActionListener(this::btnEliminarComponenteActionPerformed);

        sepComponentes.setBackground(new java.awt.Color(80, 80, 80));

        lblComponentesTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblComponentesTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblComponentesTitulo.setText("Componentes");

        javax.swing.GroupLayout cardComponentesLayout = new javax.swing.GroupLayout(cardComponentes);
        cardComponentes.setLayout(cardComponentesLayout);
        cardComponentesLayout.setHorizontalGroup(
            cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardComponentesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sepComponentes)
                    .addComponent(scrollComponentes)
                    .addGroup(cardComponentesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cardComponentesLayout.createSequentialGroup()
                                .addComponent(lblComponentesTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(cardComponentesLayout.createSequentialGroup()
                                .addComponent(btnAñadirComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(6, 6, 6))
        );
        cardComponentesLayout.setVerticalGroup(
            cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardComponentesLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblComponentesTitulo)
                .addGap(18, 18, 18)
                .addComponent(sepComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAñadirComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        panelContenido.add(cardComponentes, "componentes");

        cardPCs.setBackground(new java.awt.Color(51, 51, 51));
        cardPCs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lblPCsTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPCsTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblPCsTitulo.setText("PCs armadas");

        sepPCs.setBackground(new java.awt.Color(80, 80, 80));

        btnConstruirPC.setBackground(new java.awt.Color(0, 204, 51));
        btnConstruirPC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConstruirPC.setForeground(new java.awt.Color(0, 0, 0));
        btnConstruirPC.setText("Armar PC");
        btnConstruirPC.setBorder(null);
        btnConstruirPC.addActionListener(this::btnConstruirPCActionPerformed);

        tablaPCs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "idCliente", "Fecha armado", "Componentes"
            }
        ) {
            Class<?>[] types = new Class<?>[] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPCs.setViewportView(tablaPCs);
        if (tablaPCs.getColumnModel().getColumnCount() > 0) {
            tablaPCs.getColumnModel().getColumn(0).setResizable(false);
            tablaPCs.getColumnModel().getColumn(1).setResizable(false);
            tablaPCs.getColumnModel().getColumn(2).setResizable(false);
            tablaPCs.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout cardPCsLayout = new javax.swing.GroupLayout(cardPCs);
        cardPCs.setLayout(cardPCsLayout);
        cardPCsLayout.setHorizontalGroup(
            cardPCsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPCsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardPCsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPCsTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sepPCs)
                    .addComponent(scrollPCs, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(cardPCsLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnConstruirPC, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardPCsLayout.setVerticalGroup(
            cardPCsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPCsLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblPCsTitulo)
                .addGap(18, 18, 18)
                .addComponent(sepPCs, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConstruirPC, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPCs, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        panelContenido.add(cardPCs, "pcs");

        getContentPane().add(panelContenido, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        mostrarPanel("inicio");
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnClientes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientes1ActionPerformed
        mostrarPanel("clientes");
    }//GEN-LAST:event_btnClientes1ActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        mostrarPanel("empleados");
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnComponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComponentesActionPerformed
        irAComponentes();
    }//GEN-LAST:event_btnComponentesActionPerformed

    private void btnPCsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPCsActionPerformed
        mostrarPanel("pcs");
        refrescarTablaPCs();
    }//GEN-LAST:event_btnPCsActionPerformed

    private void btnConstruirPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstruirPCActionPerformed
        abrirFormPC();
    }//GEN-LAST:event_btnConstruirPCActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAccClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccClientesActionPerformed
        mostrarPanel("clientes");
    }//GEN-LAST:event_btnAccClientesActionPerformed

    private void btnAccEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccEmpleadosActionPerformed
        mostrarPanel("empleados");
    }//GEN-LAST:event_btnAccEmpleadosActionPerformed

    private void btnAccComponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccComponentesActionPerformed
        irAComponentes();
    }//GEN-LAST:event_btnAccComponentesActionPerformed

    private void btnAccSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnAccSalirActionPerformed

    private void btnEditarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEmpleadoActionPerformed
        Integer id = idSeleccionado(tablaEmpleados);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        abrirFormEmpleado(sistema.getAdminEmpleados().buscar(id));
    }//GEN-LAST:event_btnEditarEmpleadoActionPerformed

    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        Integer id = idSeleccionado(tablaEmpleados);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Eliminar empleado " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                sistema.getAdminEmpleados().eliminar(id);
                persistir();
                refrescarTablaEmpleados();
                refrescarContadoresInicio();
            } catch (RegistroNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed

    private void btnAñadirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirEmpleadoActionPerformed
        abrirFormEmpleado(null);
    }//GEN-LAST:event_btnAñadirEmpleadoActionPerformed

    private void btnAñadirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirClienteActionPerformed
        abrirFormCliente(null);
    }//GEN-LAST:event_btnAñadirClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        Integer id = idSeleccionado(tablaClientes);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        abrirFormCliente(sistema.getAdminClientes().buscar(id));
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        Integer id = idSeleccionado(tablaClientes);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Eliminar cliente " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                sistema.getAdminClientes().eliminar(id);
                persistir();
                refrescarTablaClientes();
                refrescarContadoresInicio();
            } catch (RegistroNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnAñadirComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirComponenteActionPerformed
        abrirFormComponente(null);
    }//GEN-LAST:event_btnAñadirComponenteActionPerformed

    private void btnEditarComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarComponenteActionPerformed
        Integer id = idSeleccionado(tablaComponentes);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un componente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        abrirFormComponente(sistema.getAdminComponentes().buscar(id));
    }//GEN-LAST:event_btnEditarComponenteActionPerformed

    private void btnEliminarComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarComponenteActionPerformed
        Integer id = idSeleccionado(tablaComponentes);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un componente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Eliminar componente " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                sistema.getAdminComponentes().eliminarPorId(id);
                persistir();
                refrescarTablaComponentes();
                refrescarContadoresInicio();
            } catch (RegistroNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarComponenteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccClientes;
    private javax.swing.JButton btnAccComponentes;
    private javax.swing.JButton btnAccEmpleados;
    private javax.swing.JButton btnAccSalir;
    private javax.swing.JButton btnAñadirCliente;
    private javax.swing.JButton btnAñadirComponente;
    private javax.swing.JButton btnAñadirEmpleado;
    private javax.swing.JButton btnClientes1;
    private javax.swing.JButton btnComponentes;
    private javax.swing.JButton btnConstruirPC;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarComponente;
    private javax.swing.JButton btnEditarEmpleado;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarComponente;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnPCs;
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel cardClientes;
    private javax.swing.JPanel cardComponentes;
    private javax.swing.JPanel cardEmpleados;
    private javax.swing.JPanel cardInicio;
    private javax.swing.JPanel cardPCs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAccesosTitulo;
    private javax.swing.JLabel lblClientesTitulo;
    private javax.swing.JLabel lblComponentesTitulo;
    private javax.swing.JLabel lblContadorClientesDetalle;
    private javax.swing.JLabel lblContadorClientesNombre;
    private javax.swing.JLabel lblContadorClientesValor;
    private javax.swing.JLabel lblContadorComponentesDetalle;
    private javax.swing.JLabel lblContadorComponentesNombre;
    private javax.swing.JLabel lblContadorComponentesValor;
    private javax.swing.JLabel lblContadorEmpleadosDetalle;
    private javax.swing.JLabel lblContadorEmpleadosNombre;
    private javax.swing.JLabel lblContadorEmpleadosValor;
    private javax.swing.JLabel lblEmpleadosTitulo;
    private javax.swing.JLabel lblInicioSubtitulo;
    private javax.swing.JLabel lblInicioTexto;
    private javax.swing.JLabel lblInicioTitulo;
    private javax.swing.JLabel lblPCsTitulo;
    private javax.swing.JLabel lblResumenTitulo;
    private javax.swing.JPanel menuLateral;
    private javax.swing.JPanel panelAccesosRapidos;
    private javax.swing.JPanel panelContadorClientes;
    private javax.swing.JPanel panelContadorComponentes;
    private javax.swing.JPanel panelContadorEmpleados;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelResumenContadores;
    private javax.swing.JScrollPane scrollClientes;
    private javax.swing.JScrollPane scrollComponentes;
    private javax.swing.JScrollPane scrollEmpleados;
    private javax.swing.JScrollPane scrollPCs;
    private javax.swing.JSeparator sepClientes;
    private javax.swing.JSeparator sepComponentes;
    private javax.swing.JSeparator sepEmpleados;
    private javax.swing.JSeparator sepInicio;
    private javax.swing.JSeparator sepPCs;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaComponentes;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JTable tablaPCs;
    // End of variables declaration//GEN-END:variables
}
