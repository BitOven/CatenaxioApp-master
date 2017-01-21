package com.catenaxio.beans;

import com.catenaxio.utils.Validador;

/**
 * Created by Antonio on 21/01/2017.
 */

public class Jornada {

    private int numJornada;
    private String fecha;
    private String hora;
    private String lugar;
    private String rival;
    private String resultado;
    private String marcador;
    private String urlCampo=" ";


    public int getNumJornada() {
        return numJornada;
    }

    public void setNumJornada(int numJornada) {
        if(Validador.validarNumPositivo(numJornada)){
            this.numJornada = numJornada;
        }
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
            this.resultado = resultado;
    }

    public String getMarcador() {
        return marcador;
    }

    public void setMarcador(String marcador) {
        this.marcador = marcador;
    }

    public String getUrlCampo() {
        return urlCampo;
    }

    public void setUrlCampo(String urlCampo) {
        this.urlCampo = urlCampo;
    }
}
