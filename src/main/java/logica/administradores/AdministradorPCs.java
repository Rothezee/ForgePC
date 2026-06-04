package logica.administradores;

import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import logica.modelo.PC;
import persistencia.ControladorDeArchivo;

public class AdministradorPCs {

    private final ArrayList<PC> pcs = new ArrayList<>();
    private final ControladorDeArchivo archivo = new ControladorDeArchivo();

    public void construir(PC pc) {
        if (pc.getId() <= 0) {
            pc.setId(siguienteId());
        }
        pcs.add(pc);
    }

    public List<PC> listar() {
        return new ArrayList<>(pcs);
    }

    public int cantidad() {
        return pcs.size();
    }

    public int siguienteId() {
        int max = 0;
        for (PC pc : pcs) {
            if (pc.getId() > max) {
                max = pc.getId();
            }
        }
        return max + 1;
    }

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

    public void guardar() throws PersistenciaException {
        ArrayList<String> lineas = new ArrayList<>();
        for (PC pc : pcs) {
            lineas.add(serializar(pc));
        }
        archivo.guardarLineas(RutasDatos.PCS, lineas);
    }

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

    private String serializar(PC pc) {
        String ids = pc.getIdsComponentes().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return pc.getId() + ";" + pc.getIdCliente() + ";" + pc.getFechaArmado() + ";" + ids;
    }
}
