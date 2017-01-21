package com.catenaxio.beans;

import java.util.List;
import java.util.Vector;

/**
 * Created by Antonio on 21/01/2017.
 */

public class Jornadas {

    private List<Jornada> jornadas;

    public Jornadas(){
        jornadas = new Vector<Jornada>();
    }

    public List<Jornada> getJornadas() {
        return jornadas;
    }

    public void setJornadas(List<Jornada> jornadas) {
        this.jornadas = jornadas;
    }

    public void resetJornadas() {
        this.jornadas = new Vector<Jornada>();
    }

    public void addJornada(Jornada jornad){
        jornadas.add(jornad);
    }
}
