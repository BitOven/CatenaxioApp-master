package com.catenaxio.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.catenaxio.utils.Constantes;

/**
 * Created by Antonio on 26/01/2017.
 */

public class SQLiteJugadores extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constantes.ColumnasJugadores.TABLE_NAME + " (" +
                    Constantes.ColumnasJugadores._ID + Constantes.SqliteGlobales.INT_TYPE+ Constantes.SqliteGlobales.PRIMARY_KEY + Constantes.SqliteGlobales.COMMA_SEP+
                    Constantes.ColumnasJugadores.COLUMN_PLAYERNAME + Constantes.SqliteGlobales.TEXT_TYPE + Constantes.SqliteGlobales.COMMA_SEP +
                    Constantes.ColumnasJugadores.COLUMN_PJ + Constantes.SqliteGlobales.INT_TYPE + Constantes.SqliteGlobales.COMMA_SEP +
                    Constantes.ColumnasJugadores.COLUMN_PG + Constantes.SqliteGlobales.INT_TYPE+ Constantes.SqliteGlobales.COMMA_SEP +
                    Constantes.ColumnasJugadores.COLUMN_ASISTENCIAS + Constantes.SqliteGlobales.INT_TYPE + Constantes.SqliteGlobales.COMMA_SEP +
                    Constantes.ColumnasJugadores.COLUMN_GOLES + Constantes.SqliteGlobales.INT_TYPE + Constantes.SqliteGlobales.COMMA_SEP +
                    Constantes.ColumnasJugadores.COLUMN_IMAGEN + Constantes.SqliteGlobales.BLOB_TYPE + Constantes.SqliteGlobales.COMMA_SEP+
                    Constantes.ColumnasJugadores.COLUMN_IMAGERESOURCE + Constantes.SqliteGlobales.INT_TYPE + Constantes.SqliteGlobales.COMMA_SEP+
                    Constantes.ColumnasJugadores.COLUMN_PT + Constantes.SqliteGlobales.INT_TYPE + Constantes.SqliteGlobales.COMMA_SEP +
                    Constantes.ColumnasJugadores.COLUMN_TEMPORADA + Constantes.SqliteGlobales.TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constantes.ColumnasJugadores.TABLE_NAME;

    public SQLiteJugadores(Context context) {
        super(context, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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