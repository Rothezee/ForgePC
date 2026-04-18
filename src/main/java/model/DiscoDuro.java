/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Usuario
 */
public class DiscoDuro extends Componente{
    private int velocidadRotacion;
    private int capacidad;
    public enum Tipo{
        SSH,
        HDD
    }
    private Tipo tipo;

    public int getVelocidadRotacion() {
        return velocidadRotacion;
    }

    public void setVelocidadRotacion(int velocidadRotacion) {
        this.velocidadRotacion = velocidadRotacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

}
