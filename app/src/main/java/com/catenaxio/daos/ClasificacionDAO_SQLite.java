package com.catenaxio.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.catenaxio.beans.Clasificacion;
import com.catenaxio.interfaces.daos.ClasificacionDAO_SQLiteInterfaz;
import com.catenaxio.sqlite.SQLiteClasificacion;
import com.catenaxio.utils.Constantes;
import com.catenaxio.utils.MiParseador;

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

    @Override
    public long updateClasificacion(Clasificacion clasificacion) {
        db=sqlClasificacion.getWritableDatabase();
        ContentValues clasif = new ContentValues();
        clasif.put(Constantes.ColumnasClasificacion.COLUMN_TEMPORADA, clasificacion.getTemporada());
        clasif.put(Constantes.ColumnasClasificacion.COLUMN_IMAGENCLASIF, clasificacion.getBlob());

        String whereclause = Constantes.ColumnasClasificacion.COLUMN_TEMPORADA + " = ?";
        String[] selectionArgs = {clasificacion.getTemporada()};

        int resultadoSQL = db.update(Constantes.ColumnasClasificacion.TABLE_NAME, clasif, whereclause, selectionArgs);
        if(resultadoSQL==0){
            return db.insert(Constantes.ColumnasJugadores.TABLE_NAME, null, clasif);
        }else{
            return resultadoSQL;
        }

    }

    @Override
    public Bitmap getClasificacion() {

        db=sqlClasificacion.getReadableDatabase();

        String selection = Constantes.ColumnasClasificacion.COLUMN_TEMPORADA + " = ?";
        String [] selectionArgs = {MiParseador.parsearTemporadaAYear(context)};

        Cursor c = db.query(Constantes.ColumnasClasificacion.TABLE_NAME, null, selection, selectionArgs, null, null, null);

                if(c.moveToNext()){
            byte[] byteArray = c.getBlob(c.getColumnIndex(Constantes.ColumnasClasificacion.COLUMN_IMAGENCLASIF));
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }

        return null;
    }

    @Override
    public Bitmap getClasificacionTemporada(String temp) {

        db=sqlClasificacion.getReadableDatabase();

        String selection = Constantes.ColumnasClasificacion.COLUMN_TEMPORADA + " = ?";
        String [] selectionArgs = {temp};

        Cursor c = db.query(Constantes.ColumnasClasificacion.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if(c.moveToNext()){
            byte[] byteArray = c.getBlob(c.getColumnIndex(Constantes.ColumnasClasificacion.COLUMN_IMAGENCLASIF));
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }

        return null;
    }

    @Override
    public void closeDB() {
        sqlClasificacion.close();
    }
}
