package logica.administradores;

import excepciones.IdDuplicadoException;
import excepciones.PersistenciaException;
import excepciones.RegistroNoEncontradoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import logica.modelo.Administrativo;
import logica.modelo.Empleado;
import logica.modelo.Gerencial;
import logica.modelo.Operario;
import persistencia.ControladorDeArchivo;

public class AdministradorEmpleados {

    private final ArrayList<Empleado> empleados = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    public void alta(Empleado empleado) throws IdDuplicadoException {
        if (buscar(empleado.getId()) != null) {
            throw new IdDuplicadoException("Ya existe un empleado con id " + empleado.getId());
        }
        if (empleado.getId() <= 0) {
            empleado.setId(siguienteId());
        }
        empleados.add(empleado);
    }

    public void actualizar(Empleado empleado) throws RegistroNoEncontradoException {
        Empleado existente = buscar(empleado.getId());
        if (existente == null) {
            throw new RegistroNoEncontradoException("Empleado no encontrado: " + empleado.getId());
        }
        empleados.set(empleados.indexOf(existente), empleado);
    }

    public void eliminar(int id) throws RegistroNoEncontradoException {
        Empleado empleado = buscar(id);
        if (empleado == null) {
            throw new RegistroNoEncontradoException("Empleado no encontrado: " + id);
        }
        empleados.remove(empleado);
    }

    public Empleado buscar(int id) {
        for (Empleado empleado : empleados) {
            if (empleado.getId() == id) {
                return empleado;
            }
        }
        return null;
    }

    public List<Empleado> listarTodos() {
        ArrayList<Empleado> copia = new ArrayList<>(empleados);
        Collections.sort(copia);
        return copia;
    }

    public int cantidad() {
        return empleados.size();
    }

    public int siguienteId() {
        int max = 0;
        for (Empleado empleado : empleados) {
            if (empleado.getId() > max) {
                max = empleado.getId();
            }
        }
        return max + 1;
    }

    public void cargar() throws PersistenciaException {
        empleados.clear();
        if (!archivo.existe(RutasDatos.EMPLEADOS)) {
            archivo.crearArchivo(RutasDatos.EMPLEADOS);
            return;
        }
        for (String linea : archivo.leerLineas(RutasDatos.EMPLEADOS)) {
            empleados.add(parsear(linea));
        }
    }

    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Empleado empleado : empleados) {
            lineas.add(serializar(empleado));
        }
        archivo.guardarLineas(RutasDatos.EMPLEADOS, lineas);
    }

    private Empleado parsear(String linea) {
        String[] p = linea.split(";", -1);
        String tipo = p[0];
        int id = Integer.parseInt(p[1]);
        String nombre = p[2];
        String apellido = p[3];
        int dni = Integer.parseInt(p[4]);
        String fecha = p[5];
        String direccion = p[6];
        int antiguedad = Integer.parseInt(p[7]);
        int legajo = Integer.parseInt(p[8]);

        switch (tipo) {
            case "ADMIN":
                return new Administrativo(id, nombre, apellido, dni, fecha, direccion,
                        antiguedad, legajo, p[9], p[10]);
            case "OPER":
                return new Operario(id, nombre, apellido, dni, fecha, direccion,
                        antiguedad, legajo, p[9]);
            case "GEREN":
                return new Gerencial(id, nombre, apellido, dni, fecha, direccion,
                        antiguedad, legajo, p[9]);
            default:
                throw new IllegalArgumentException("Tipo de empleado desconocido: " + tipo);
        }
    }

    private String serializar(Empleado e) {
        String base = e.getTipo() + ";" + e.getId() + ";" + e.getNombre() + ";" + e.getApellido() + ";"
                + e.getDni() + ";" + e.getFechaNacimiento() + ";" + e.getDireccion() + ";"
                + e.getAntiguedad() + ";" + e.getLegajo();
        if (e instanceof Administrativo a) {
            return base + ";" + a.getTarea() + ";" + a.getArea();
        }
        if (e instanceof Operario o) {
            return base + ";" + o.getSector();
        }
        if (e instanceof Gerencial g) {
            return base + ";" + g.getCargo();
        }
        return base;
    }
}
