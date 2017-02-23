package com.catenaxio.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.catenaxio.beans.Clasificacion;
import com.catenaxio.interfaces.daos.ClasificacionDAO_SQLiteInterfaz;
import com.catenaxio.sqlite.SQLiteClasificacion;
import com.catenaxio.utils.Constantes;

/**
 * Created by Antonio on 22/02/2017.
 */

public class ClasificacionDAO_SQLite implements ClasificacionDAO_SQLiteInterfaz {

    private SQLiteOpenHelper sqlClasificacion;
    private SQLiteDatabase db;
    private Context context;

    public ClasificacionDAO_SQLite(Context context) {
        this.context = context;
        this.sqlClasificacion = new SQLiteClasificacion(context);
    }
//TODO implementar dao
    @Override
    public long updateClasificacion(Clasificacion clasificacion) {
        db=sqlClasificacion.getWritableDatabase();
        ContentValues clasif = new ContentValues();
        clasif.put(Constantes.ColumnasClasificacion.COLUMN_TEMPORADA, clasificacion.getTemporada());


        return 0;
    }

    @Override
    public long updateClasificacion(String temporada) {
        return 0;
    }

    @Override
    public Bitmap getClasificación() {
        return null;
    }

    @Override
    public Bitmap getClasificacionTemporada() {
        return null;
    }
}
