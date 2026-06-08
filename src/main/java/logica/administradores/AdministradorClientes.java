package logica.administradores;

import excepciones.IdDuplicadoException;
import excepciones.PersistenciaException;
import excepciones.RegistroNoEncontradoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import logica.modelo.Cliente;
import persistencia.ControladorDeArchivo;

/** Gestiona el ABM de clientes en memoria y su persistencia en clientes.txt. */
public class AdministradorClientes {

    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    /** Agrega un cliente nuevo; asigna id automático si viene en 0 o negativo. */
    public void alta(Cliente cliente) throws IdDuplicadoException {
        if (buscar(cliente.getId()) != null) {
            throw new IdDuplicadoException("Ya existe un cliente con id " + cliente.getId());
        }
        if (cliente.getId() <= 0) {
            cliente.setId(siguienteId());
        }
        clientes.add(cliente);
    }

    /** Reemplaza los datos de un cliente existente según su id. */
    public void actualizar(Cliente cliente) throws RegistroNoEncontradoException {
        Cliente existente = buscar(cliente.getId());
        if (existente == null) {
            throw new RegistroNoEncontradoException("Cliente no encontrado: " + cliente.getId());
        }
        int indice = clientes.indexOf(existente);
        clientes.set(indice, cliente);
    }

    /** Quita de la lista el cliente con el id indicado. */
    public void eliminar(int id) throws RegistroNoEncontradoException {
        if (!clientes.removeIf(c -> c.getId() == id)) {
            throw new RegistroNoEncontradoException("Cliente no encontrado: " + id);
        }
    }

    /** Devuelve el cliente con ese id, o null si no existe. */
    public Cliente buscar(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    /** Devuelve una copia de todos los clientes ordenados por apellido y nombre. */
    public List<Cliente> listarOrdenados() {
        ArrayList<Cliente> copia = new ArrayList<>(clientes);
        Collections.sort(copia);
        return copia;
    }

    /** Cantidad de clientes cargados en memoria. */
    public int cantidad() {
        return clientes.size();
    }

    /** Calcula el próximo id libre (máximo existente + 1). */
    public int siguienteId() {
        int max = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getId() > max) {
                max = cliente.getId();
            }
        }
        return max + 1;
    }

    /** Lee clientes.txt y reconstruye la lista en memoria. */
    public void cargar() throws PersistenciaException {
        clientes.clear();
        if (!archivo.existe(RutasDatos.CLIENTES)) {
            archivo.crearArchivo(RutasDatos.CLIENTES);
            return;
        }
        for (String linea : archivo.leerLineas(RutasDatos.CLIENTES)) {
            clientes.add(parsear(linea));
        }
    }

    /** Escribe todos los clientes de memoria al archivo clientes.txt. */
    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Cliente cliente : clientes) {
            lineas.add(serializar(cliente));
        }
        archivo.guardarLineas(RutasDatos.CLIENTES, lineas);
    }

    /** Convierte una línea del archivo en un objeto Cliente. */
    private Cliente parsear(String linea) {
        String[] p = linea.split(";", -1);
        Cliente c = new Cliente();
        c.setId(Integer.parseInt(p[0]));
        c.setNombre(p[1]);
        c.setApellido(p[2]);
        c.setDni(Integer.parseInt(p[3]));
        c.setFechaNacimiento(p[4]);
        c.setDireccion(p[5]);
        c.setMail(p[6]);
        c.setCuil(Long.parseLong(p[7]));
        c.setNacionalidad(p[8]);
        return c;
    }

    /** Convierte un Cliente en una línea de texto separada por punto y coma. */
    private String serializar(Cliente c) {
        return c.getId() + ";" + c.getNombre() + ";" + c.getApellido() + ";" + c.getDni() + ";"
                + c.getFechaNacimiento() + ";" + c.getDireccion() + ";" + c.getMail() + ";"
                + c.getCuil() + ";" + c.getNacionalidad();
    }
}
