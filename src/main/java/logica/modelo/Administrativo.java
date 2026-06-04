package logica.modelo;

public class Administrativo extends Empleado {

    private String tarea;
    private String area;

    public Administrativo() {
    }

    public Administrativo(int id, String nombre, String apellido, int dni, String fechaNacimiento,
            String direccion, int antiguedad, int legajo, String tarea, String area) {
        super(id, nombre, apellido, dni, fechaNacimiento, direccion, antiguedad, legajo);
        this.tarea = tarea;
        this.area = area;
    }

    @Override
    public String getTipo() {
        return "ADMIN";
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
