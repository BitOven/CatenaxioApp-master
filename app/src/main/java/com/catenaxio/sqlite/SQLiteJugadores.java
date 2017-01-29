package com.catenaxio.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

/**
 * Created by Antonio on 26/01/2017.
 */

public class SQLiteJugadores extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    public static class Columnas implements BaseColumns {
        public static final String TABLE_NAME = "jugadores";
        public static final String COLUMN_PLAYERNAME = "nombre";
        public static final String COLUMN_PJ = "partidosjugados";
        public static final String COLUMN_PG = "partidosganados";
        public static final String COLUMN_ASISTENCIAS = "asistencias";
        public static final String COLUMN_GOLES = "goles";
        public static final String COLUMN_IMAGEN = "imagen";
        public static final String COLUMN_IMAGERESOURCE = "imageresource";
        public static final String COLUMN_PT = "partidostotales";
        public static final String COLUMN_TEMPORADA = "temporada";
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Catenaxio.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String BLOB_TYPE = " BLOB";
    private static final String NULL_TYPE = " NULL";
    private static final String COMMA_SEP = ", ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Columnas.TABLE_NAME + " (" +
                    Columnas._ID + INT_TYPE+ PRIMARY_KEY + COMMA_SEP+
                    Columnas.COLUMN_PLAYERNAME + TEXT_TYPE + COMMA_SEP +
                    Columnas.COLUMN_PJ + INT_TYPE + COMMA_SEP +
                    Columnas.COLUMN_PG + INT_TYPE+ COMMA_SEP +
                    Columnas.COLUMN_ASISTENCIAS + INT_TYPE + COMMA_SEP +
                    Columnas.COLUMN_GOLES + INT_TYPE + COMMA_SEP +
                    Columnas.COLUMN_IMAGEN + BLOB_TYPE + COMMA_SEP+
                    Columnas.COLUMN_IMAGERESOURCE + INT_TYPE + COMMA_SEP+
                    Columnas.COLUMN_PT + INT_TYPE + COMMA_SEP +
                    Columnas.COLUMN_TEMPORADA + TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Columnas.TABLE_NAME;

    public SQLiteJugadores(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}