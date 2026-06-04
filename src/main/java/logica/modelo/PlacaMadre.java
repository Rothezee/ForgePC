package logica.modelo;

public class PlacaMadre extends Componente {

    private int ranurasMemoria;
    private String idsProcesadores;

    public PlacaMadre() {
    }

    public PlacaMadre(int id, String modelo, double precio, String descripcion,
            int ranurasMemoria, String idsProcesadores) {
        super(id, modelo, precio, descripcion);
        this.ranurasMemoria = ranurasMemoria;
        this.idsProcesadores = idsProcesadores;
    }

    @Override
    public String getTipo() {
        return "PLACA";
    }

    public int getRanurasMemoria() {
        return ranurasMemoria;
    }

    public void setRanurasMemoria(int ranurasMemoria) {
        this.ranurasMemoria = ranurasMemoria;
    }

    public String getIdsProcesadores() {
        return idsProcesadores;
    }

    public void setIdsProcesadores(String idsProcesadores) {
        this.idsProcesadores = idsProcesadores;
    }
}
