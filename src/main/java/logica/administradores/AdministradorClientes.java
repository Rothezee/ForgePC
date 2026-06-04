package logica.administradores;

import excepciones.IdDuplicadoException;
import excepciones.PersistenciaException;
import excepciones.RegistroNoEncontradoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import logica.modelo.Cliente;
import persistencia.ControladorDeArchivo;

public class AdministradorClientes {

    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    public void alta(Cliente cliente) throws IdDuplicadoException {
        if (buscar(cliente.getId()) != null) {
            throw new IdDuplicadoException("Ya existe un cliente con id " + cliente.getId());
        }
        if (cliente.getId() <= 0) {
            cliente.setId(siguienteId());
        }
        clientes.add(cliente);
    }

    public void actualizar(Cliente cliente) throws RegistroNoEncontradoException {
        Cliente existente = buscar(cliente.getId());
        if (existente == null) {
            throw new RegistroNoEncontradoException("Cliente no encontrado: " + cliente.getId());
        }
        int indice = clientes.indexOf(existente);
        clientes.set(indice, cliente);
    }

    public void eliminar(int id) throws RegistroNoEncontradoException {
        Cliente cliente = buscar(id);
        if (cliente == null) {
            throw new RegistroNoEncontradoException("Cliente no encontrado: " + id);
        }
        clientes.remove(cliente);
    }

    public Cliente buscar(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> listarOrdenados() {
        ArrayList<Cliente> copia = new ArrayList<>(clientes);
        Collections.sort(copia);
        return copia;
    }

    public int cantidad() {
        return clientes.size();
    }

    public int siguienteId() {
        int max = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getId() > max) {
                max = cliente.getId();
            }
        }
        return max + 1;
    }

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

    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Cliente cliente : clientes) {
            lineas.add(serializar(cliente));
        }
        archivo.guardarLineas(RutasDatos.CLIENTES, lineas);
    }

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

    private String serializar(Cliente c) {
        return c.getId() + ";" + c.getNombre() + ";" + c.getApellido() + ";" + c.getDni() + ";"
                + c.getFechaNacimiento() + ";" + c.getDireccion() + ";" + c.getMail() + ";"
                + c.getCuil() + ";" + c.getNacionalidad();
    }
}
