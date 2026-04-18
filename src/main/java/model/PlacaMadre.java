package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class PlacaMadre extends Componente{
    private int cantidadRanurasMemoria;
    private final List<Integer> procesadoresSoportados = new ArrayList<>();
    
    public void añadirProcesador(int id){
        this.procesadoresSoportados.add(id);
    }

    public int getCantidadRanurasMemoria() {
        return cantidadRanurasMemoria;
    }

    public void setCantidadRanurasMemoria(int cantidadRanurasMemoria) {
        this.cantidadRanurasMemoria = cantidadRanurasMemoria;
    }

    public List<Integer> getProcesadoresSoportados() {
        return procesadoresSoportados;
    }

}
