package logica.administradores;

import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import logica.modelo.PC;
import persistencia.ControladorDeArchivo;

/** Gestiona el registro de PCs armadas y su persistencia en pcs.txt. */
public class AdministradorPCs {

    private final ArrayList<PC> pcs = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    /** Registra una PC armada; valida cliente y componentes; asigna id si viene en 0 o negativo. */
    public void construir(PC pc, AdministradorClientes adminClientes, AdministradorComponentes adminComponentes) {
        if (adminClientes.buscar(pc.getIdCliente()) == null) {
            throw new IllegalArgumentException("Cliente no encontrado: " + pc.getIdCliente());
        }
        if (pc.getIdsComponentes() == null || pc.getIdsComponentes().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos un componente.");
        }
        for (int idComp : pc.getIdsComponentes()) {
            if (adminComponentes.buscar(idComp) == null) {
                throw new IllegalArgumentException("Componente no encontrado: " + idComp);
            }
        }
        if (pc.getId() <= 0) {
            pc.setId(siguienteId());
        }
        pcs.add(pc);
    }

    /** Devuelve una copia de todas las PCs registradas. */
    public List<PC> listar() {
        return new ArrayList<>(pcs);
    }

    /** Cantidad de PCs cargadas en memoria. */
    public int cantidad() {
        return pcs.size();
    }

    /** Calcula el próximo id libre (máximo existente + 1). */
    public int siguienteId() {
        int max = 0;
        for (PC pc : pcs) {
            if (pc.getId() > max) {
                max = pc.getId();
            }
        }
        return max + 1;
    }

    /** Lee pcs.txt y reconstruye la lista de PCs en memoria. */
    public void cargar() throws PersistenciaException {
        pcs.clear();
        if (!archivo.existe(RutasDatos.PCS)) {
            archivo.crearArchivo(RutasDatos.PCS);
            return;
        }
        for (String linea : archivo.leerLineas(RutasDatos.PCS)) {
            pcs.add(parsear(linea));
        }
    }

    /** Escribe todas las PCs de memoria al archivo pcs.txt. */
    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (PC pc : pcs) {
            lineas.add(serializar(pc));
        }
        archivo.guardarLineas(RutasDatos.PCS, lineas);
    }

    /** Convierte una línea del archivo en un objeto PC con sus ids de componentes. */
    private PC parsear(String linea) {
        String[] p = linea.split(";", -1);
        PC pc = new PC();
        pc.setId(Integer.parseInt(p[0]));
        pc.setIdCliente(Integer.parseInt(p[1]));
        pc.setFechaArmado(p[2]);
        if (!p[3].isEmpty()) {
            List<Integer> ids = Arrays.stream(p[3].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            pc.setIdsComponentes(ids);
        }
        return pc;
    }

    /** Convierte una PC en una línea de texto con ids de componentes separados por coma. */
    private String serializar(PC pc) {
        String ids = pc.getIdsComponentes().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return pc.getId() + ";" + pc.getIdCliente() + ";" + pc.getFechaArmado() + ";" + ids;
    }
}
