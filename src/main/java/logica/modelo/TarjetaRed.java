package logica.modelo;

public class TarjetaRed extends Componente {

    private int velocidadTransmision;
    private String mac;

    public TarjetaRed() {
    }

    public TarjetaRed(int id, String modelo, double precio, String descripcion,
            int velocidadTransmision, String mac) {
        super(id, modelo, precio, descripcion);
        this.velocidadTransmision = velocidadTransmision;
        this.mac = mac;
    }

    @Override
    public String getTipo() {
        return "RED";
    }

    public int getVelocidadTransmision() {
        return velocidadTransmision;
    }

    public void setVelocidadTransmision(int velocidadTransmision) {
        this.velocidadTransmision = velocidadTransmision;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
