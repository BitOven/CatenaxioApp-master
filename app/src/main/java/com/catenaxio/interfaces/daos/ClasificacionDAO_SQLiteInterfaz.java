package com.catenaxio.interfaces.daos;

import android.graphics.Bitmap;

import com.catenaxio.beans.Clasificacion;

/**
 * Created by Antonio on 22/02/2017.
 */

public interface ClasificacionDAO_SQLiteInterfaz {

    long updateClasificacion(Clasificacion clasificacion);

    long updateClasificacion(String temporada);

    Bitmap getClasificación();

    Bitmap getClasificacionTemporada();
}
