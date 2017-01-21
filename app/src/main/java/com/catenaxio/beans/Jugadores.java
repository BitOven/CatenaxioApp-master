package com.catenaxio.beans;

import java.util.List;
import java.util.Vector;

/**
 * Created by Antonio on 20/01/2017.
 */

public class Jugadores {

    private List<Jugador> jugadores;
    private String temporada;



    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

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

    public Jugador getJugadores(int idx) {
        return jugadores.get(idx);
    }

    /**
     *
     * @param name
     * @return null if notFound, first Jugador with nombre=name otherwise
     */
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

    public void resetJugadores() {
        jugadores = new Vector<Jugador>();
    }

    public void addJugador(Jugador jug){
        this.jugadores.add(jug);
    }

    public int getPartidosTotales(){
        return Jugador.getPartidosTotales();
    }

    public int getGolesTotales(){
        return Jugador.getGolesTotales();
    }
}
