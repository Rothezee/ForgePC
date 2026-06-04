package logica.modelo;

public class Procesador extends Componente {

    private double ghz;
    private int cacheKb;

    public Procesador() {
    }

    public Procesador(int id, String modelo, double precio, String descripcion, double ghz, int cacheKb) {
        super(id, modelo, precio, descripcion);
        this.ghz = ghz;
        this.cacheKb = cacheKb;
    }

    @Override
    public String getTipo() {
        return "CPU";
    }

    public double getGhz() {
        return ghz;
    }

    public void setGhz(double ghz) {
        this.ghz = ghz;
    }

    public int getCacheKb() {
        return cacheKb;
    }

    public void setCacheKb(int cacheKb) {
        this.cacheKb = cacheKb;
    }
}
