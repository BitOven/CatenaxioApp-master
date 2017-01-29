package com.catenaxio.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.catenaxio.beans.Jugador;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.interfaces.daos.JugadoresDAO_SQLiteInterfaz;
import com.catenaxio.sqlite.SQLiteJugadores;
import com.catenaxio.utils.MiParseador;

import java.nio.ByteBuffer;

/**
 * Created by Antonio on 26/01/2017.
 */

public class JugadoresDAO_SQLite implements JugadoresDAO_SQLiteInterfaz {

    SQLiteOpenHelper sqlJugadores;
    SQLiteDatabase db;
    Context context;

    public JugadoresDAO_SQLite(Context cnt){
        context=cnt;
        sqlJugadores = new SQLiteJugadores(cnt);
    }

    public long updatePlayer(Jugador jugador){
        db=sqlJugadores.getWritableDatabase();
        ContentValues player = new ContentValues();
        player.put(SQLiteJugadores.Columnas.COLUMN_PLAYERNAME,jugador.getNombre());
        player.put(SQLiteJugadores.Columnas.COLUMN_ASISTENCIAS, jugador.getAsistencias());
        player.put(SQLiteJugadores.Columnas.COLUMN_GOLES, jugador.getGoles());
        player.put(SQLiteJugadores.Columnas.COLUMN_PJ, jugador.getPartidosJugados());
        player.put(SQLiteJugadores.Columnas.COLUMN_PG, jugador.getPartidosGanados());
        player.put(SQLiteJugadores.Columnas.COLUMN_PT, Jugador.getPartidosTotales());

        if(jugador.getImageResource()!=0){
            player.put(SQLiteJugadores.Columnas.COLUMN_IMAGERESOURCE, jugador.getImageResource());
        }
        player.put(SQLiteJugadores.Columnas.COLUMN_TEMPORADA, MiParseador.parsearTemporadaAYear(context));

        String whereclause = SQLiteJugadores.Columnas.COLUMN_TEMPORADA + " = ? AND "+SQLiteJugadores.Columnas.COLUMN_PLAYERNAME +" = ?";
        String[] selectionArgs = {MiParseador.parsearTemporadaAYear(context),jugador.getNombre()};

        int resultadoSQL = db.update(SQLiteJugadores.Columnas.TABLE_NAME, player, whereclause, selectionArgs);
        if(resultadoSQL==0){
            return db.insert(SQLiteJugadores.Columnas.TABLE_NAME, null, player);
        }else{
            return resultadoSQL;
        }
    }

    public boolean updatePlayers(Jugadores jugadores){
        boolean resultOk=true;
        for(Jugador jugador : jugadores.getJugadores()){
            if(updatePlayer(jugador)<=0){
                resultOk=false;
            }
        }

        return resultOk;
    }

    public void getJugadores(Jugadores jugadoresSQL){

        Jugadores jugadores = jugadoresSQL;
        jugadores.resetJugadores();
        db=sqlJugadores.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = SQLiteJugadores.Columnas.COLUMN_TEMPORADA + " = ?";
        String[] selectionArgs = {MiParseador.parsearTemporadaAYear(context)};

        Cursor c = db.query(SQLiteJugadores.Columnas.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        jugadores.setTemporada(MiParseador.parsearTemporadaAYear(context));

//        c.moveToFirst();
        while(c.moveToNext()){
            Jugador jugador = new Jugador();
            jugador.setPartidosJugados(c.getInt(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_PJ)));
            jugador.setPartidosGanados(c.getInt(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_PG)));
            jugador.setNombre(c.getString(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_PLAYERNAME)));
            jugador.setAsistencias(c.getInt(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_ASISTENCIAS)));
            jugador.setGoles(c.getInt(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_GOLES)));
            jugador.setImageResource(c.getInt(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_IMAGERESOURCE)));
            if(jugador.getImageResource()==0){
                byte[] byteArray = c.getBlob(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_IMAGEN));
                if(byteArray!=null) {
                    Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    jugador.setImagen(image);
                }
            }
            jugadores.addJugador(jugador);
            if(c.isLast()){
                jugadores.setPartidosTotales(c.getInt(c.getColumnIndex(SQLiteJugadores.Columnas.COLUMN_PT)));
            }
        }

    }

    @Override
    public void closeDB() {
        sqlJugadores.close();
    }

    public int updateImagePlayer(Jugador jugador, byte[] byteArr){

        db=sqlJugadores.getWritableDatabase();
        ContentValues player = new ContentValues();

        player.put(SQLiteJugadores.Columnas.COLUMN_IMAGERESOURCE, 0);
        //código para convertir a bytes sacado de: http://stackoverflow.com/questions/10191871/converting-bitmap-to-bytearray-android
//        ByteBuffer buffer = ByteBuffer.allocate(jugador.getImagen().getByteCount());
//        jugador.getImagen().copyPixelsToBuffer(buffer);
//        byte[] byteArray = buffer.array();
//        player.put(SQLiteJugadores.Columnas.COLUMN_IMAGEN, byteArray);
        player.put(SQLiteJugadores.Columnas.COLUMN_IMAGEN, byteArr);

        String whereclause = SQLiteJugadores.Columnas.COLUMN_TEMPORADA + " = ? AND "+SQLiteJugadores.Columnas.COLUMN_PLAYERNAME +" = ?";
        String[] selectionArgs = {MiParseador.parsearTemporadaAYear(context),jugador.getNombre()};

        return db.update(SQLiteJugadores.Columnas.TABLE_NAME, player, whereclause, selectionArgs);
    }
}
