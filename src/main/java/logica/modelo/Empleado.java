package logica.modelo;

public abstract class Empleado extends Persona {

    private int antiguedad;
    private int legajo;

    public Empleado() {
    }

    public Empleado(int id, String nombre, String apellido, int dni, String fechaNacimiento,
            String direccion, int antiguedad, int legajo) {
        super(id, nombre, apellido, dni, fechaNacimiento, direccion);
        this.antiguedad = antiguedad;
        this.legajo = legajo;
    }

    public abstract String getTipo();

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
}
