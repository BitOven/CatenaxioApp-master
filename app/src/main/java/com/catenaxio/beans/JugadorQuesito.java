package com.catenaxio.beans;

import java.io.Serializable;

/**
 * Created by Antonio on 29/01/2017.
 */

public class JugadorQuesito implements Serializable{

    private String nombre;
    private int goles;
    private String porcentajeGoles;

    public JugadorQuesito() {
    }

    public JugadorQuesito(String nombre, int goles, String porcentajeGoles) {
        this.nombre = nombre;
        this.goles = goles;
        this.porcentajeGoles = porcentajeGoles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public String getPorcentajeGoles() {
        return porcentajeGoles;
    }

    public void setPorcentajeGoles(String porcentajeGoles) {
        this.porcentajeGoles = porcentajeGoles;
    }
}
