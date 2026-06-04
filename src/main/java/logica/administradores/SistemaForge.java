package logica.administradores;

import excepciones.PersistenciaException;

public class SistemaForge {

    private static SistemaForge instancia;

    private final AdministradorClientes adminClientes = new AdministradorClientes();
    private final AdministradorEmpleados adminEmpleados = new AdministradorEmpleados();
    private final AdministradorComponentes adminComponentes = new AdministradorComponentes();
    private final AdministradorPCs adminPCs = new AdministradorPCs();

    private SistemaForge() {
    }

    public static SistemaForge getInstancia() {
        if (instancia == null) {
            instancia = new SistemaForge();
        }
        return instancia;
    }

    public void cargarTodo() throws PersistenciaException {
        adminClientes.cargar();
        adminEmpleados.cargar();
        adminComponentes.cargar();
        adminPCs.cargar();
    }

    public void guardarTodo() throws PersistenciaException {
        adminClientes.guardar();
        adminEmpleados.guardar();
        adminComponentes.guardar();
        adminPCs.guardar();
    }

    public AdministradorClientes getAdminClientes() {
        return adminClientes;
    }

    public AdministradorEmpleados getAdminEmpleados() {
        return adminEmpleados;
    }

    public AdministradorComponentes getAdminComponentes() {
        return adminComponentes;
    }

    public AdministradorPCs getAdminPCs() {
        return adminPCs;
    }
}
