package com.catenaxio.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.catenaxio.utils.Constantes;

/**
 * Created by Antonio on 22/02/2017.
 */

public class SQLiteClasificacion extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constantes.ColumnasClasificacion.TABLE_NAME + " (" +
                    Constantes.ColumnasClasificacion._ID + Constantes.SqliteGlobales.INT_TYPE+ Constantes.SqliteGlobales.PRIMARY_KEY + Constantes.SqliteGlobales.COMMA_SEP+
                    Constantes.ColumnasClasificacion.COLUMN_IMAGENCLASIF + Constantes.SqliteGlobales.BLOB_TYPE + Constantes.SqliteGlobales.COMMA_SEP+
                    Constantes.ColumnasClasificacion.COLUMN_TEMPORADA + Constantes.SqliteGlobales.TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constantes.ColumnasClasificacion.TABLE_NAME;

    public SQLiteClasificacion(Context context) {
        super(context, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
