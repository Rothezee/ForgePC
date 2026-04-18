/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Usuario
 */
public class TarjetaRed extends Componente{
    private int velocidadTransmision;
    private String mac;

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
