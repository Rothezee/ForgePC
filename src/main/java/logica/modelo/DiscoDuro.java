package logica.modelo;

public class DiscoDuro extends Componente {

    private int rpm;
    private int capacidadGb;
    private String tipoDisco;

    public DiscoDuro() {
    }

    public DiscoDuro(int id, String modelo, double precio, String descripcion,
            int rpm, int capacidadGb, String tipoDisco) {
        super(id, modelo, precio, descripcion);
        this.rpm = rpm;
        this.capacidadGb = capacidadGb;
        this.tipoDisco = tipoDisco;
    }

    @Override
    public String getTipo() {
        return "DISCO";
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public int getCapacidadGb() {
        return capacidadGb;
    }

    public void setCapacidadGb(int capacidadGb) {
        this.capacidadGb = capacidadGb;
    }

    public String getTipoDisco() {
        return tipoDisco;
    }

    public void setTipoDisco(String tipoDisco) {
        this.tipoDisco = tipoDisco;
    }
}
