package com.catenaxio.beans;

import java.util.List;
import java.util.Vector;

/**
 * Created by Antonio on 20/01/2017.
 */

public class Jugadores {

    private List<Jugador> jugadores;

    public Jugadores(){
        jugadores = new Vector<Jugador>();
    }

    /**
     *
     * @return Lista de jugadores almacenados
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador getJugador(int idx) {
        return jugadores.get(idx);
    }

    public Jugador getJugador(String name) {
        Jugador player=null;
        for(int i=0; i<jugadores.size(); i++){
            if(jugadores.get(i).getNombre().equals(name)){
                player=jugadores.get(i);
                break;
            }
        }
        return player;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void addJugador(Jugador jug){
        this.jugadores.add(jug);
    }
}
