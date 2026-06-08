package logica.administradores;

import excepciones.IdDuplicadoException;
import excepciones.PersistenciaException;
import excepciones.RegistroNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import logica.modelo.Componente;
import logica.modelo.DiscoDuro;
import logica.modelo.Fuente;
import logica.modelo.Memoria;
import logica.modelo.PlacaMadre;
import logica.modelo.Procesador;
import logica.modelo.TarjetaRed;
import persistencia.ControladorDeArchivo;

/** Gestiona el ABM de componentes de PC y su persistencia en componentes.txt. */
public class AdministradorComponentes {

    private final ArrayList<Componente> componentes = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    /** Agrega un componente nuevo; asigna id automático si viene en 0 o negativo. */
    public void alta(Componente componente) throws IdDuplicadoException {
        if (buscar(componente.getId()) != null) {
            throw new IdDuplicadoException("Ya existe un componente con id " + componente.getId());
        }
        if (componente.getId() <= 0) {
            componente.setId(siguienteId());
        }
        componentes.add(componente);
    }

    /** Reemplaza los datos de un componente existente según su id. */
    public void actualizar(Componente componente) throws RegistroNoEncontradoException {
        Componente existente = buscar(componente.getId());
        if (existente == null) {
            throw new RegistroNoEncontradoException("Componente no encontrado: " + componente.getId());
        }
        componentes.set(componentes.indexOf(existente), componente);
    }

    /** Quita de la lista el componente con el id indicado. */
    public void eliminarPorId(int id) throws RegistroNoEncontradoException {
        if (!componentes.removeIf(c -> c.getId() == id)) {
            throw new RegistroNoEncontradoException("Componente no encontrado: " + id);
        }
    }

    /** Devuelve el componente con ese id, o null si no existe. */
    public Componente buscar(int id) {
        for (Componente componente : componentes) {
            if (componente.getId() == id) {
                return componente;
            }
        }
        return null;
    }

    /** Devuelve una copia de todos los componentes en el orden de carga. */
    public List<Componente> listarTodos() {
        return new ArrayList<>(componentes);
    }

    /** Cantidad de componentes cargados en memoria. */
    public int cantidad() {
        return componentes.size();
    }

    /** Calcula el próximo id libre (máximo existente + 1). */
    public int siguienteId() {
        int max = 0;
        for (Componente componente : componentes) {
            if (componente.getId() > max) {
                max = componente.getId();
            }
        }
        return max + 1;
    }

    /** Lee componentes.txt y reconstruye la lista en memoria según el tipo de cada línea. */
    public void cargar() throws PersistenciaException {
        componentes.clear();
        if (!archivo.existe(RutasDatos.COMPONENTES)) {
            archivo.crearArchivo(RutasDatos.COMPONENTES);
            return;
        }
        for (String linea : archivo.leerLineas(RutasDatos.COMPONENTES)) {
            componentes.add(parsear(linea));
        }
    }

    /** Escribe todos los componentes de memoria al archivo componentes.txt. */
    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Componente componente : componentes) {
            lineas.add(serializar(componente));
        }
        archivo.guardarLineas(RutasDatos.COMPONENTES, lineas);
    }

    /** Convierte una línea del archivo en el subtipo de Componente que indica el campo tipo. */
    private Componente parsear(String linea) {
        String[] p = linea.split(";", -1);
        String tipo = p[0];
        int id = Integer.parseInt(p[1]);
        String modelo = p[2];
        double precio = Double.parseDouble(p[3]);
        String descripcion = p[4];

        switch (tipo) {
            case "PLACA":
                return new PlacaMadre(id, modelo, precio, descripcion,
                        Integer.parseInt(p[5]), p[6]);
            case "CPU":
                return new Procesador(id, modelo, precio, descripcion,
                        Double.parseDouble(p[5]), Integer.parseInt(p[6]));
            case "MEM":
                return new Memoria(id, modelo, precio, descripcion, p[5],
                        Integer.parseInt(p[6]), Integer.parseInt(p[7]));
            case "RED":
                return new TarjetaRed(id, modelo, precio, descripcion,
                        Integer.parseInt(p[5]), p[6]);
            case "DISCO":
                return new DiscoDuro(id, modelo, precio, descripcion,
                        Integer.parseInt(p[5]), Integer.parseInt(p[6]), p[7]);
            case "FUENTE":
                return new Fuente(id, modelo, precio, descripcion, Integer.parseInt(p[5]));
            default:
                throw new IllegalArgumentException("Tipo de componente desconocido: " + tipo);
        }
    }

    /** Convierte un componente en una línea de texto, incluyendo campos propios de su subtipo. */
    private String serializar(Componente c) {
        String base = c.getTipo() + ";" + c.getId() + ";" + c.getModelo() + ";" + c.getPrecio() + ";"
                + c.getDescripcion();
        return switch (c.getTipo()) {
            case "PLACA" -> {
                PlacaMadre placa = (PlacaMadre) c;
                yield base + ";" + placa.getRanurasMemoria() + ";" + placa.getIdsProcesadores();
            }
            case "CPU" -> {
                Procesador cpu = (Procesador) c;
                yield base + ";" + cpu.getGhz() + ";" + cpu.getCacheKb();
            }
            case "MEM" -> {
                Memoria mem = (Memoria) c;
                yield base + ";" + mem.getTecnologia() + ";" + mem.getVelocidad() + ";" + mem.getTamanoGb();
            }
            case "RED" -> {
                TarjetaRed red = (TarjetaRed) c;
                yield base + ";" + red.getVelocidadTransmision() + ";" + red.getMac();
            }
            case "DISCO" -> {
                DiscoDuro disco = (DiscoDuro) c;
                yield base + ";" + disco.getRpm() + ";" + disco.getCapacidadGb() + ";" + disco.getTipoDisco();
            }
            case "FUENTE" -> {
                Fuente fuente = (Fuente) c;
                yield base + ";" + fuente.getWatts();
            }
            default -> base;
        };
    }
}
