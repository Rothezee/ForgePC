package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class Procesador extends Componente{
    private float velocidadGhz;
    private int memoriaCache;

    public float getVelocidadGhz() {
        return velocidadGhz;
    }

    public void setVelocidadGhz(float velocidadGhz) {
        this.velocidadGhz = velocidadGhz;
    }

    public int getMemoriaCache() {
        return memoriaCache;
    }

    public void setMemoriaCache(int memoriaCache) {
        this.memoriaCache = memoriaCache;
    }

}
