package logica.modelo;

public abstract class Persona implements Comparable<Persona> {

    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    private String fechaNacimiento;
    private String direccion;

    public Persona() {
    }

    public Persona(int id, String nombre, String apellido, int dni, String fechaNacimiento, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
    }

    @Override
    public int compareTo(Persona otra) {
        int cmp = this.apellido.compareToIgnoreCase(otra.apellido);
        if (cmp != 0) {
            return cmp;
        }
        return this.nombre.compareToIgnoreCase(otra.nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
