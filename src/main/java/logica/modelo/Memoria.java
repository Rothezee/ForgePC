package logica.modelo;

public class Memoria extends Componente {

    private String tecnologia;
    private int velocidad;
    private int tamanoGb;

    public Memoria() {
    }

    public Memoria(int id, String modelo, double precio, String descripcion,
            String tecnologia, int velocidad, int tamanoGb) {
        super(id, modelo, precio, descripcion);
        this.tecnologia = tecnologia;
        this.velocidad = velocidad;
        this.tamanoGb = tamanoGb;
    }

    @Override
    public String getTipo() {
        return "MEM";
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getTamanoGb() {
        return tamanoGb;
    }

    public void setTamanoGb(int tamanoGb) {
        this.tamanoGb = tamanoGb;
    }
}
