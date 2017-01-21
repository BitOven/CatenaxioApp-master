package com.catenaxio.beans;

import com.catenaxio.utils.Validador;

/**
 * Created by Antonio on 20/01/2017.
 */

public class Jugador {

    private String nombre;
    private int dorsal;
    private int partidosGanados;
    private int partidosJugados;
    private int asistencias;
    private int goles;
    private static int partidosTotales;
    private static int golesTotales;

    static{
        partidosTotales=0;
        golesTotales=0;
    }

    //metodos

    public float getPorcentajeGoles(){
        float result;
        if(golesTotales!=0){
         return result = (float)goles/(float)golesTotales;
        }else {
            return 0;
        }
    }

    //getter y setters

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        if(Validador.validarNumPositivo(dorsal)){
            this.dorsal = dorsal;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        if(Validador.validarNumPositivo(partidosGanados)){
            this.partidosGanados = partidosGanados;
        }
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        if(Validador.validarNumPositivo(asistencias)){
            this.asistencias = asistencias;
        }
    }

    public int getGoles() {
        return goles;
    }

    public boolean setGoles(int goles) {//seteo goles y actualizo golesTotales
        if(Validador.validarNumPositivo(goles)){
            if(this.goles!=goles){
                golesTotales-=this.goles;
                this.goles=goles;
                golesTotales+=this.goles;
            }
            return true;
        }else{
            return false;
        }
    }

    public static int getPartidosTotales() {
        return partidosTotales;
    }

    public static void setPartidosTotales(int partidosTotales) {
        Jugador.partidosTotales = partidosTotales;
    }

    public static void resetPartidosTotales() {
        Jugador.partidosTotales = 0;
    }

    public static int getGolesTotales() {
        return golesTotales;
    }

    public static void setGolesTotales(int golesTotales) {
        Jugador.golesTotales = golesTotales;
    }

    public static void resetGolesTotales() {
        Jugador.golesTotales = 0;
    }
}
