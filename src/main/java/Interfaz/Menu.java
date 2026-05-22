/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Ventana principal del sistema ForgePCs.
 */
public class Menu extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;

    private static final Color MENU_LATERAL = Color.GRAY;
    private static final Color FONDO_CONTENIDO = new Color(30, 30, 30);
    private static final Color FONDO_BOTON = new Color(86, 86, 86);
    private static final Color BOTON_ACTIVO = new Color(110, 110, 110);
    private static final Color MENU_SUPERIOR = new Color(50, 50, 50);
    private static final Color TEXTO_CLARO = Color.WHITE;
    private static final Color TEXTO_SECUNDARIO = new Color(180, 180, 180);
    private static final Color BORDE_TARJETA = new Color(70, 70, 70);
    private static final Color FONDO_TARJETA = new Color(40, 40, 40);

    private String panelActivo = "inicio";

    @SuppressWarnings("this-escape")
    public Menu() {
        if (!java.beans.Beans.isDesignTime()) {
            setUndecorated(true);
        }
        initComponents();
        if (!java.beans.Beans.isDesignTime()) {
            configurarBarraTitulo();
            configurarTema();
            configurarAccesosInicio();
            mostrarPanel("inicio");
        }
    }

    private void configurarBarraTitulo() {
        setJMenuBar(null);

        JPanel barraTitulo = new JPanel(new BorderLayout());
        barraTitulo.setBackground(MENU_SUPERIOR);
        barraTitulo.setPreferredSize(new Dimension(0, 32));

        jMenuBar1.setBackground(MENU_SUPERIOR);
        jMenuBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 0));
        jMenuBar1.setOpaque(true);

        JPanel botonesVentana = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        botonesVentana.setOpaque(false);

        JButton btnMinimizar = crearBotonVentana("\u2014");
        JButton btnMaximizar = crearBotonVentana("\u25a1");
        JButton btnCerrar = crearBotonVentana("\u2715");

        btnMinimizar.addActionListener(e -> setExtendedState(ICONIFIED));
        btnMaximizar.addActionListener(e -> {
            if ((getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH) {
                setExtendedState(NORMAL);
            } else {
                setExtendedState(MAXIMIZED_BOTH);
            }
        });
        btnCerrar.addActionListener(e -> System.exit(0));

        botonesVentana.add(btnMinimizar);
        botonesVentana.add(btnMaximizar);
        botonesVentana.add(btnCerrar);

        barraTitulo.add(jMenuBar1, BorderLayout.WEST);
        barraTitulo.add(botonesVentana, BorderLayout.EAST);

        MouseAdapter moverVentana = new MouseAdapter() {
            private int origenX;
            private int origenY;

            @Override
            public void mousePressed(MouseEvent e) {
                origenX = e.getXOnScreen() - getX();
                origenY = e.getYOnScreen() - getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if ((getExtendedState() & MAXIMIZED_BOTH) != MAXIMIZED_BOTH) {
                    setLocation(e.getXOnScreen() - origenX, e.getYOnScreen() - origenY);
                }
            }
        };

        barraTitulo.addMouseListener(moverVentana);
        barraTitulo.addMouseMotionListener(moverVentana);

        getContentPane().add(barraTitulo, BorderLayout.NORTH);
    }

    private JButton crearBotonVentana(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setBackground(MENU_SUPERIOR);
        boton.setForeground(TEXTO_CLARO);
        boton.setPreferredSize(new Dimension(46, 32));
        boton.setFont(boton.getFont().deriveFont(14f));
        return boton;
    }

    private void configurarTema() {
        getContentPane().setBackground(FONDO_CONTENIDO);

        menuLateral.setBackground(MENU_LATERAL);
        menuLateral.setOpaque(true);

        jLabel1.setOpaque(true);
        jLabel1.setBackground(MENU_LATERAL);
        jLabel1.setForeground(TEXTO_CLARO);

        jSeparator1.setForeground(new Color(60, 60, 60));
        jSeparator1.setBackground(new Color(60, 60, 60));

        panelContenido.setBackground(FONDO_CONTENIDO);
        aplicarFondo(FONDO_CONTENIDO, cardInicio, cardClientes, cardEmpleados, cardComponentes);
        aplicarMarcoTarjeta(cardInicio, cardClientes, cardEmpleados, cardComponentes);

        estiloBotonMenu(btnInicio);
        estiloBotonMenu(btnClientes1);
        estiloBotonMenu(btnEmpleados);
        estiloBotonMenu(btnComponentes);
        estiloBotonMenu(btnSalir);
        estiloBotonMenu(btnAccClientes);
        estiloBotonMenu(btnAccEmpleados);
        estiloBotonMenu(btnAccComponentes);
        estiloBotonMenu(btnAccSalir);

        estiloMenu(jMenu1);
        estiloMenu(jMenu2);

        estiloTituloSeccion(lblInicioTitulo, lblClientesTitulo, lblEmpleadosTitulo, lblComponentesTitulo);
        estiloSubtituloSeccion(lblInicioSubtitulo, lblClientesSubtitulo, lblEmpleadosSubtitulo, lblComponentesSubtitulo);
        estiloTextoSeccion(lblInicioTexto, lblClientesTexto, lblEmpleadosTexto, lblComponentesTexto);
        estiloTituloSeccion(lblAccesosTitulo, lblResumenTitulo);

        estiloBotonAccesoGrande(btnAccClientes, btnAccEmpleados, btnAccComponentes, btnAccSalir);
        estiloContador(panelContadorClientes, lblContadorClientesValor, lblContadorClientesNombre, lblContadorClientesDetalle);
        estiloContador(panelContadorEmpleados, lblContadorEmpleadosValor, lblContadorEmpleadosNombre, lblContadorEmpleadosDetalle);
        estiloContador(panelContadorComponentes, lblContadorComponentesValor, lblContadorComponentesNombre, lblContadorComponentesDetalle);

        panelAccesosRapidos.setOpaque(false);
        panelResumenContadores.setOpaque(false);

        estiloSeparador(sepInicio, sepClientes, sepEmpleados, sepComponentes);
    }

    private void estiloBotonAccesoGrande(JButton... botones) {
        Font fuente = botones[0].getFont().deriveFont(Font.BOLD, 18f);
        for (JButton boton : botones) {
            boton.setFont(fuente);
            boton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            boton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        }
    }

    private void estiloContador(JPanel panel, JLabel valor, JLabel nombre, JLabel detalle) {
        panel.setBackground(FONDO_TARJETA);
        panel.setOpaque(true);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_TARJETA, 1),
                javax.swing.BorderFactory.createEmptyBorder(24, 16, 24, 16)
        ));
        valor.setForeground(TEXTO_CLARO);
        nombre.setForeground(TEXTO_CLARO);
        detalle.setForeground(TEXTO_SECUNDARIO);
        valor.setFont(valor.getFont().deriveFont(Font.BOLD, 48f));
        nombre.setFont(nombre.getFont().deriveFont(Font.BOLD, 18f));
        detalle.setFont(detalle.getFont().deriveFont(Font.PLAIN, 14f));
        valor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    private void configurarAccesosInicio() {
        btnAccClientes.addActionListener(e -> mostrarPanel("clientes"));
        btnAccEmpleados.addActionListener(e -> mostrarPanel("empleados"));
        btnAccComponentes.addActionListener(e -> mostrarPanel("componentes"));
        btnAccSalir.addActionListener(e -> System.exit(0));
    }

    private void estiloMenu(JMenu menu) {
        menu.setOpaque(true);
        menu.setBackground(MENU_SUPERIOR);
        menu.setForeground(TEXTO_CLARO);
    }

    private void aplicarFondo(Color color, JPanel... paneles) {
        for (JPanel panel : paneles) {
            panel.setBackground(color);
            panel.setOpaque(true);
        }
    }

    private void aplicarMarcoTarjeta(JPanel... paneles) {
        javax.swing.border.Border marco = javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_TARJETA, 1),
                javax.swing.BorderFactory.createEmptyBorder(36, 40, 36, 40));
        for (JPanel panel : paneles) {
            panel.setBorder(marco);
        }
    }

    private void estiloBotonMenu(JButton boton) {
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        boton.setBackground(FONDO_BOTON);
        boton.setForeground(TEXTO_CLARO);
    }

    private void estiloTituloSeccion(JLabel... labels) {
        for (JLabel label : labels) {
            label.setForeground(TEXTO_CLARO);
        }
    }

    private void estiloSubtituloSeccion(JLabel... labels) {
        for (JLabel label : labels) {
            label.setForeground(BOTON_ACTIVO);
        }
    }

    private void estiloTextoSeccion(JLabel... labels) {
        for (JLabel label : labels) {
            label.setForeground(TEXTO_SECUNDARIO);
        }
    }

    private void estiloSeparador(javax.swing.JSeparator... separadores) {
        for (javax.swing.JSeparator sep : separadores) {
            sep.setForeground(BORDE_TARJETA);
            sep.setBackground(BORDE_TARJETA);
        }
    }

    private void actualizarBotonActivo() {
        aplicarEstadoBoton(btnInicio, "inicio".equals(panelActivo));
        aplicarEstadoBoton(btnClientes1, "clientes".equals(panelActivo));
        aplicarEstadoBoton(btnEmpleados, "empleados".equals(panelActivo));
        aplicarEstadoBoton(btnComponentes, "componentes".equals(panelActivo));
    }

    private void aplicarEstadoBoton(JButton boton, boolean activo) {
        boton.setBackground(activo ? BOTON_ACTIVO : FONDO_BOTON);
        boton.setForeground(TEXTO_CLARO);
    }

    private void mostrarPanel(String nombre) {
        panelActivo = nombre;
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, nombre);
        actualizarBotonActivo();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuLateral = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnInicio = new javax.swing.JButton();
        btnClientes1 = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnComponentes = new javax.swing.JButton();
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
        lblContadorClientesValor1 = new javax.swing.JLabel();
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
        lblClientesSubtitulo = new javax.swing.JLabel();
        sepClientes = new javax.swing.JSeparator();
        lblClientesTexto = new javax.swing.JLabel();
        cardEmpleados = new javax.swing.JPanel();
        lblEmpleadosTitulo = new javax.swing.JLabel();
        lblEmpleadosSubtitulo = new javax.swing.JLabel();
        sepEmpleados = new javax.swing.JSeparator();
        lblEmpleadosTexto = new javax.swing.JLabel();
        cardComponentes = new javax.swing.JPanel();
        lblComponentesTitulo = new javax.swing.JLabel();
        lblComponentesSubtitulo = new javax.swing.JLabel();
        sepComponentes = new javax.swing.JSeparator();
        lblComponentesTexto = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuLateral.setBackground(java.awt.Color.gray);
        menuLateral.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jLabel1.setBackground(java.awt.Color.gray);
        jLabel1.setFont(new java.awt.Font("Cooper Black", 3, 28)); // NOI18N
        jLabel1.setText("ForgePCs");

        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("Inicio");
        btnInicio.addActionListener(this::btnInicioActionPerformed);

        btnClientes1.setText("Clientes");
        btnClientes1.addActionListener(this::btnClientes1ActionPerformed);

        btnEmpleados.setText("Empleados");
        btnEmpleados.addActionListener(this::btnEmpleadosActionPerformed);

        btnComponentes.setText("Componentes");
        btnComponentes.addActionListener(this::btnComponentesActionPerformed);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        javax.swing.GroupLayout menuLateralLayout = new javax.swing.GroupLayout(menuLateral);
        menuLateral.setLayout(menuLateralLayout);
        menuLateralLayout.setHorizontalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClientes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnComponentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuLateralLayout.setVerticalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLateralLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        getContentPane().add(menuLateral, java.awt.BorderLayout.LINE_START);

        panelContenido.setBackground(new java.awt.Color(245, 245, 245));
        panelContenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelContenido.setLayout(new java.awt.CardLayout());

        cardInicio.setBackground(new java.awt.Color(245, 245, 245));

        lblInicioTitulo.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblInicioTitulo.setText("ForgePCs");

        lblInicioSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInicioSubtitulo.setText("Sistema de gestion");

        lblInicioTexto.setForeground(new java.awt.Color(102, 102, 102));
        lblInicioTexto.setText("<html><div style='width:520px;'>Fabricacion de componentes y armado de computadoras personalizadas.</div></html>");

        lblAccesosTitulo.setText("Accesos rapidos");

        panelAccesosRapidos.setLayout(new java.awt.GridLayout(2, 2, 12, 12));

        btnAccClientes.setText("Clientes");
        panelAccesosRapidos.add(btnAccClientes);

        btnAccEmpleados.setText("Empleados");
        panelAccesosRapidos.add(btnAccEmpleados);

        btnAccComponentes.setText("Componentes");
        panelAccesosRapidos.add(btnAccComponentes);

        btnAccSalir.setText("Salir");
        panelAccesosRapidos.add(btnAccSalir);

        lblResumenTitulo.setText("Resumen del sistema");

        panelResumenContadores.setLayout(new java.awt.GridLayout(1, 3, 16, 0));

        panelContadorClientes.setLayout(new java.awt.BorderLayout());

        lblContadorClientesNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesNombre.setText("Clientes");
        panelContadorClientes.add(lblContadorClientesNombre, java.awt.BorderLayout.NORTH);

        lblContadorClientesValor.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorClientesValor.setForeground(new java.awt.Color(51, 51, 51));
        lblContadorClientesValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesValor.setText("--");
        panelContadorClientes.add(lblContadorClientesValor, java.awt.BorderLayout.CENTER);

        lblContadorClientesDetalle.setForeground(new java.awt.Color(102, 102, 102));
        lblContadorClientesDetalle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesDetalle.setText("Registrados");
        panelContadorClientes.add(lblContadorClientesDetalle, java.awt.BorderLayout.SOUTH);

        lblContadorClientesValor1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorClientesValor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorClientesValor1.setText("--");
        panelContadorClientes.add(lblContadorClientesValor1, java.awt.BorderLayout.CENTER);

        panelResumenContadores.add(panelContadorClientes);

        panelContadorEmpleados.setLayout(new java.awt.BorderLayout());

        lblContadorEmpleadosNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorEmpleadosNombre.setText("Empleados");
        panelContadorEmpleados.add(lblContadorEmpleadosNombre, java.awt.BorderLayout.PAGE_START);

        lblContadorEmpleadosValor.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorEmpleadosValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorEmpleadosValor.setText("--");
        panelContadorEmpleados.add(lblContadorEmpleadosValor, java.awt.BorderLayout.CENTER);

        lblContadorEmpleadosDetalle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorEmpleadosDetalle.setText("Activos");
        panelContadorEmpleados.add(lblContadorEmpleadosDetalle, java.awt.BorderLayout.PAGE_END);

        panelResumenContadores.add(panelContadorEmpleados);

        panelContadorComponentes.setLayout(new java.awt.BorderLayout());

        lblContadorComponentesNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorComponentesNombre.setText("Componentes");
        panelContadorComponentes.add(lblContadorComponentesNombre, java.awt.BorderLayout.PAGE_START);

        lblContadorComponentesValor.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContadorComponentesValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContadorComponentesValor.setText("--");
        panelContadorComponentes.add(lblContadorComponentesValor, java.awt.BorderLayout.CENTER);

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
                    .addComponent(lblInicioTexto, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
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
                .addComponent(panelResumenContadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContenido.add(cardInicio, "inicio");

        cardClientes.setBackground(new java.awt.Color(245, 245, 245));

        lblClientesTitulo.setText("Clientes");

        javax.swing.GroupLayout cardClientesLayout = new javax.swing.GroupLayout(cardClientes);
        cardClientes.setLayout(cardClientesLayout);
        cardClientesLayout.setHorizontalGroup(
            cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientesTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addComponent(lblClientesSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sepClientes)
                    .addComponent(lblClientesTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cardClientesLayout.setVerticalGroup(
            cardClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblClientesTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClientesSubtitulo)
                .addGap(18, 18, 18)
                .addComponent(sepClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblClientesTexto)
                .addContainerGap(300, Short.MAX_VALUE))
        );

        panelContenido.add(cardClientes, "clientes");

        cardEmpleados.setBackground(new java.awt.Color(245, 245, 245));

        lblEmpleadosTitulo.setText("Empleados");

        javax.swing.GroupLayout cardEmpleadosLayout = new javax.swing.GroupLayout(cardEmpleados);
        cardEmpleados.setLayout(cardEmpleadosLayout);
        cardEmpleadosLayout.setHorizontalGroup(
            cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmpleadosTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addComponent(lblEmpleadosSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sepEmpleados)
                    .addComponent(lblEmpleadosTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cardEmpleadosLayout.setVerticalGroup(
            cardEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEmpleadosTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmpleadosSubtitulo)
                .addGap(18, 18, 18)
                .addComponent(sepEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblEmpleadosTexto)
                .addContainerGap(300, Short.MAX_VALUE))
        );

        panelContenido.add(cardEmpleados, "empleados");

        cardComponentes.setBackground(new java.awt.Color(245, 245, 245));

        lblComponentesTitulo.setText("Componentes");

        javax.swing.GroupLayout cardComponentesLayout = new javax.swing.GroupLayout(cardComponentes);
        cardComponentes.setLayout(cardComponentesLayout);
        cardComponentesLayout.setHorizontalGroup(
            cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardComponentesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComponentesTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addComponent(lblComponentesSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sepComponentes)
                    .addComponent(lblComponentesTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cardComponentesLayout.setVerticalGroup(
            cardComponentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardComponentesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComponentesTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblComponentesSubtitulo)
                .addGap(18, 18, 18)
                .addComponent(sepComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblComponentesTexto)
                .addContainerGap(300, Short.MAX_VALUE))
        );

        panelContenido.add(cardComponentes, "componentes");

        getContentPane().add(panelContenido, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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
        mostrarPanel("componentes");
    }//GEN-LAST:event_btnComponentesActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccClientes;
    private javax.swing.JButton btnAccComponentes;
    private javax.swing.JButton btnAccEmpleados;
    private javax.swing.JButton btnAccSalir;
    private javax.swing.JButton btnClientes1;
    private javax.swing.JButton btnComponentes;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel cardClientes;
    private javax.swing.JPanel cardComponentes;
    private javax.swing.JPanel cardEmpleados;
    private javax.swing.JPanel cardInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAccesosTitulo;
    private javax.swing.JLabel lblClientesSubtitulo;
    private javax.swing.JLabel lblClientesTexto;
    private javax.swing.JLabel lblClientesTitulo;
    private javax.swing.JLabel lblComponentesSubtitulo;
    private javax.swing.JLabel lblComponentesTexto;
    private javax.swing.JLabel lblComponentesTitulo;
    private javax.swing.JLabel lblContadorClientesDetalle;
    private javax.swing.JLabel lblContadorClientesNombre;
    private javax.swing.JLabel lblContadorClientesValor;
    private javax.swing.JLabel lblContadorClientesValor1;
    private javax.swing.JLabel lblContadorComponentesDetalle;
    private javax.swing.JLabel lblContadorComponentesNombre;
    private javax.swing.JLabel lblContadorComponentesValor;
    private javax.swing.JLabel lblContadorEmpleadosDetalle;
    private javax.swing.JLabel lblContadorEmpleadosNombre;
    private javax.swing.JLabel lblContadorEmpleadosValor;
    private javax.swing.JLabel lblEmpleadosSubtitulo;
    private javax.swing.JLabel lblEmpleadosTexto;
    private javax.swing.JLabel lblEmpleadosTitulo;
    private javax.swing.JLabel lblInicioSubtitulo;
    private javax.swing.JLabel lblInicioTexto;
    private javax.swing.JLabel lblInicioTitulo;
    private javax.swing.JLabel lblResumenTitulo;
    private javax.swing.JPanel menuLateral;
    private javax.swing.JPanel panelAccesosRapidos;
    private javax.swing.JPanel panelContadorClientes;
    private javax.swing.JPanel panelContadorComponentes;
    private javax.swing.JPanel panelContadorEmpleados;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelResumenContadores;
    private javax.swing.JSeparator sepClientes;
    private javax.swing.JSeparator sepComponentes;
    private javax.swing.JSeparator sepEmpleados;
    private javax.swing.JSeparator sepInicio;
    // End of variables declaration//GEN-END:variables
}
