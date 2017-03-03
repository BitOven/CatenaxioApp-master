package com.catenaxio.beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Antonio on 22/02/2017.
 */

public class Clasificacion {

    private String temporada;
    private byte[] bytesArray;

    public byte[] getBlob() {
        return bytesArray;
    }

    public Bitmap getImagen() {
        if(bytesArray!=null){
            return BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
        }else{
            return null;
        }
    }

    public void setImagen(byte[] bytesArray) {
        this.bytesArray = bytesArray;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
}
