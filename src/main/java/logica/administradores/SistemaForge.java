package logica.administradores;

import excepciones.PersistenciaException;

/** Punto de acceso único (singleton) que agrupa todos los administradores del sistema. */
public class SistemaForge {

    private static SistemaForge instancia;

    private final AdministradorClientes adminClientes = new AdministradorClientes();
    private final AdministradorEmpleados adminEmpleados = new AdministradorEmpleados();
    private final AdministradorComponentes adminComponentes = new AdministradorComponentes();
    private final AdministradorPCs adminPCs = new AdministradorPCs();

    private SistemaForge() {
    }

    /** Devuelve la única instancia del sistema, creándola la primera vez que se invoca. */
    public static SistemaForge getInstancia() {
        if (instancia == null) {
            instancia = new SistemaForge();
        }
        return instancia;
    }

    /** Carga en memoria clientes, empleados, componentes y PCs desde sus archivos .txt. */
    public void cargarTodo() throws PersistenciaException {
        adminClientes.cargar();
        adminEmpleados.cargar();
        adminComponentes.cargar();
        adminPCs.cargar();
    }

    /** Persiste en disco todos los datos que están en memoria. */
    public void guardarTodo() throws PersistenciaException {
        adminClientes.guardar();
        adminEmpleados.guardar();
        adminComponentes.guardar();
        adminPCs.guardar();
    }

    /** Acceso al administrador de clientes. */
    public AdministradorClientes getAdminClientes() {
        return adminClientes;
    }

    /** Acceso al administrador de empleados. */
    public AdministradorEmpleados getAdminEmpleados() {
        return adminEmpleados;
    }

    /** Acceso al administrador de componentes. */
    public AdministradorComponentes getAdminComponentes() {
        return adminComponentes;
    }

    /** Acceso al administrador de PCs armadas. */
    public AdministradorPCs getAdminPCs() {
        return adminPCs;
    }
}
