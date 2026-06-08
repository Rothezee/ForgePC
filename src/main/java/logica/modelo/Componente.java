package logica.modelo;

public abstract class Componente {

    private int id;
    private String modelo;
    private double precio;
    private String descripcion;

    public Componente() {
    }

    public Componente(int id, String modelo, double precio, String descripcion) {
        this.id = id;
        this.modelo = modelo;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public abstract String getTipo();

    public boolean esUn(String tipo) {
        return tipo != null && tipo.equals(getTipo());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
