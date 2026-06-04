package logica.modelo;

public class Gerencial extends Empleado {

    private String cargo;

    public Gerencial() {
    }

    public Gerencial(int id, String nombre, String apellido, int dni, String fechaNacimiento,
            String direccion, int antiguedad, int legajo, String cargo) {
        super(id, nombre, apellido, dni, fechaNacimiento, direccion, antiguedad, legajo);
        this.cargo = cargo;
    }

    @Override
    public String getTipo() {
        return "GEREN";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
