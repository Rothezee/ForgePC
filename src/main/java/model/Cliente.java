/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Usuario
 */
public class Cliente extends Persona{
    private String mail;
    private int cuil;
    public enum Nacionalidad{
        Argentina,
        Brasil,
        Ecuador,
        Bolivia,
        Colombia,
        Chile,
        Uruguay,
        Paraguay
    }
    private Nacionalidad nacion;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getCuil() {
        return cuil;
    }

    public void setCuil(int cuil) {
        this.cuil = cuil;
    }

    public Nacionalidad getNacion() {
        return nacion;
    }

    public void setNacion(Nacionalidad nacion) {
        this.nacion = nacion;
    }

}
