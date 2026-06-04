package logica.modelo;

import java.util.ArrayList;
import java.util.List;

public class PC {

    private int id;
    private int idCliente;
    private String fechaArmado;
    private List<Integer> idsComponentes = new ArrayList<>();

    public PC() {
    }

    public PC(int id, int idCliente, String fechaArmado, List<Integer> idsComponentes) {
        this.id = id;
        this.idCliente = idCliente;
        this.fechaArmado = fechaArmado;
        this.idsComponentes = new ArrayList<>(idsComponentes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaArmado() {
        return fechaArmado;
    }

    public void setFechaArmado(String fechaArmado) {
        this.fechaArmado = fechaArmado;
    }

    public List<Integer> getIdsComponentes() {
        return idsComponentes;
    }

    public void setIdsComponentes(List<Integer> idsComponentes) {
        this.idsComponentes = idsComponentes;
    }
}
