package com.catenaxio.beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Antonio on 22/02/2017.
 */

public class Clasificacion {

    private String temporada;
    private Bitmap bitmap;
    private byte[] bytesArray;

    public byte[] getBlob() {
        return bytesArray;
    }

    public Bitmap getImagen() {
        return bitmap;
    }

    public void setImagen(byte[] bytesArray) {
        this.bytesArray = bytesArray;
        this.bitmap= BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
    }

    public void setImagen(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
}
