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

public class AdministradorComponentes {

    private final ArrayList<Componente> componentes = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    public void alta(Componente componente) throws IdDuplicadoException {
        if (buscar(componente.getId()) != null) {
            throw new IdDuplicadoException("Ya existe un componente con id " + componente.getId());
        }
        if (componente.getId() <= 0) {
            componente.setId(siguienteId());
        }
        componentes.add(componente);
    }

    public void actualizar(Componente componente) throws RegistroNoEncontradoException {
        Componente existente = buscar(componente.getId());
        if (existente == null) {
            throw new RegistroNoEncontradoException("Componente no encontrado: " + componente.getId());
        }
        componentes.set(componentes.indexOf(existente), componente);
    }

    public void eliminarPorId(int id) throws RegistroNoEncontradoException {
        Componente componente = buscar(id);
        if (componente == null) {
            throw new RegistroNoEncontradoException("Componente no encontrado: " + id);
        }
        componentes.remove(componente);
    }

    public Componente buscar(int id) {
        for (Componente componente : componentes) {
            if (componente.getId() == id) {
                return componente;
            }
        }
        return null;
    }

    public List<Componente> listarTodos() {
        return new ArrayList<>(componentes);
    }

    public int cantidad() {
        return componentes.size();
    }

    public int siguienteId() {
        int max = 0;
        for (Componente componente : componentes) {
            if (componente.getId() > max) {
                max = componente.getId();
            }
        }
        return max + 1;
    }

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

    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Componente componente : componentes) {
            lineas.add(serializar(componente));
        }
        archivo.guardarLineas(RutasDatos.COMPONENTES, lineas);
    }

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

    private String serializar(Componente c) {
        String base = c.getTipo() + ";" + c.getId() + ";" + c.getModelo() + ";" + c.getPrecio() + ";"
                + c.getDescripcion();
        if (c instanceof PlacaMadre placa) {
            return base + ";" + placa.getRanurasMemoria() + ";" + placa.getIdsProcesadores();
        }
        if (c instanceof Procesador cpu) {
            return base + ";" + cpu.getGhz() + ";" + cpu.getCacheKb();
        }
        if (c instanceof Memoria mem) {
            return base + ";" + mem.getTecnologia() + ";" + mem.getVelocidad() + ";" + mem.getTamanoGb();
        }
        if (c instanceof TarjetaRed red) {
            return base + ";" + red.getVelocidadTransmision() + ";" + red.getMac();
        }
        if (c instanceof DiscoDuro disco) {
            return base + ";" + disco.getRpm() + ";" + disco.getCapacidadGb() + ";" + disco.getTipoDisco();
        }
        if (c instanceof Fuente fuente) {
            return base + ";" + fuente.getWatts();
        }
        return base;
    }
}
