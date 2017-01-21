package com.catenaxio.beans;

import java.util.List;
import java.util.Vector;


/**
 * Created by Antonio on 20/01/2017.
 * @author Antonio "BitOven" Merillas
 * You should treat this like a singleton class.
 * The Jugador.golesTotatales param and the Jugador.partidosTotales param are reset
 * with each instance of this class
 */
public class Jugadores {

    private List<Jugador> jugadores;
    private String temporada;

    public Jugadores(){
        Jugador.resetGolesTotales();
        Jugador.resetPartidosTotales();
        jugadores = new Vector<Jugador>();
    }

    //metodos

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
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
        int golesT=0;
        golesT=Jugador.getGolesTotales();
        golesT+=jug.getGoles();
        Jugador.setGolesTotales(golesT);
        this.jugadores.add(jug);
    }

    public void removeJugador(int idx){
        int golesT=0;
        golesT=Jugador.getGolesTotales();
        golesT-=jugadores.get(idx).getGoles();
        Jugador.setGolesTotales(golesT);
        jugadores.remove(idx);
    }

    public void setPartidosTotales(int pt){
        Jugador.setPartidosTotales(pt);
    }

    public int getPartidosTotales(){
        return Jugador.getPartidosTotales();
    }

    public int getGolesTotales(){
        return Jugador.getGolesTotales();
    }
}
