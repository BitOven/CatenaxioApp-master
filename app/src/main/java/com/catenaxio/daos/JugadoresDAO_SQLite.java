package com.catenaxio.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.catenaxio.beans.Jugador;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.sqlite.SQLiteJugadores;
import com.catenaxio.utils.MiParseador;

import java.nio.ByteBuffer;

/**
 * Created by Antonio on 26/01/2017.
 */

public class JugadoresDAO_SQLite {

    SQLiteOpenHelper sqlJugadores;
    SQLiteDatabase db;
    Context context;

    public JugadoresDAO_SQLite(Context cnt){
        context=cnt;
        sqlJugadores = new SQLiteJugadores(cnt);
    }

    public long insertNewPlayer(Jugador jugador){
        db=sqlJugadores.getWritableDatabase();
        ContentValues player = new ContentValues();
        player.put(SQLiteJugadores.Columnas.COLUMN_PLAYERNAME,jugador.getNombre());
        player.put(SQLiteJugadores.Columnas.COLUMN_ASISTENCIAS, jugador.getAsistencias());
        player.put(SQLiteJugadores.Columnas.COLUMN_GOLES, jugador.getGoles());
        player.put(SQLiteJugadores.Columnas.COLUMN_PJ, jugador.getPartidosJugados());
        player.put(SQLiteJugadores.Columnas.COLUMN_PG, jugador.getPartidosGanados());
        player.put(SQLiteJugadores.Columnas.COLUMN_TOTALGOLES, Jugador.getGolesTotales());
        player.put(SQLiteJugadores.Columnas.COLUMN_TOTALPARTIDOS, Jugador.getPartidosTotales());
        player.put(SQLiteJugadores.Columnas.COLUMN_PORCENTGOLES, jugador.getPorcentajeGoles());
        if(jugador.getImageResource()!=0){
            player.put(SQLiteJugadores.Columnas.COLUMN_IMAGERESOURCE, jugador.getImageResource());
        }else if(jugador.getImagen()!=null){
            //código para convertir a bytes sacado de: http://stackoverflow.com/questions/10191871/converting-bitmap-to-bytearray-android
            ByteBuffer buffer = ByteBuffer.allocate(jugador.getImagen().getByteCount());
            jugador.getImagen().copyPixelsToBuffer(buffer);
            byte[] byteArray = buffer.array();

            player.put(SQLiteJugadores.Columnas.COLUMN_IMAGEN, byteArray);
        }
        player.put(SQLiteJugadores.Columnas.COLUMN_TEMPORADA, MiParseador.parsearTemporadaAYear(context));

        return db.insert(SQLiteJugadores.Columnas.TABLE_NAME, null, player);
    }

    public void insertNewPlayers(Jugadores jugadores){
        for(Jugador jugador : jugadores.getJugadores()){
            insertNewPlayer(jugador);
        }
    }

    public Jugadores getJugadores(){
        //TODO implementar metodo para recuperar un objeto Jugadores desde la SQL segun temporada
        //Usar MiParseador.parsearTemporadaAYear(context));
        return null;
    }
}
