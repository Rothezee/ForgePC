package logica.modelo;

public class Cliente extends Persona {

    private String mail;
    private long cuil;
    private String nacionalidad;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellido, int dni, String fechaNacimiento,
            String direccion, String mail, long cuil, String nacionalidad) {
        super(id, nombre, apellido, dni, fechaNacimiento, direccion);
        this.mail = mail;
        this.cuil = cuil;
        this.nacionalidad = nacionalidad;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getCuil() {
        return cuil;
    }

    public void setCuil(long cuil) {
        this.cuil = cuil;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
