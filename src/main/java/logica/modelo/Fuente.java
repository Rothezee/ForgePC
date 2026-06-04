package logica.modelo;

public class Fuente extends Componente {

    private int watts;

    public Fuente() {
    }

    public Fuente(int id, String modelo, double precio, String descripcion, int watts) {
        super(id, modelo, precio, descripcion);
        this.watts = watts;
    }

    @Override
    public String getTipo() {
        return "FUENTE";
    }

    public int getWatts() {
        return watts;
    }

    public void setWatts(int watts) {
        this.watts = watts;
    }
}
