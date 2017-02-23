package com.catenaxio.beans;

import android.graphics.Bitmap;

/**
 * Created by Antonio on 22/02/2017.
 */

public class Clasificacion {

    private Bitmap imagen;
    private String temporada;

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
}
