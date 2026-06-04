package logica.modelo;

public class Operario extends Empleado {

    private String sector;

    public Operario() {
    }

    public Operario(int id, String nombre, String apellido, int dni, String fechaNacimiento,
            String direccion, int antiguedad, int legajo, String sector) {
        super(id, nombre, apellido, dni, fechaNacimiento, direccion, antiguedad, legajo);
        this.sector = sector;
    }

    @Override
    public String getTipo() {
        return "OPER";
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
