package com.catenaxio.managers;

import android.content.Context;
import android.widget.BaseAdapter;

import com.catenaxio.beans.Jornadas;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.daos.ClasificacionDAO_SQLite;
import com.catenaxio.daos.JornadasDAOFireBase;
import com.catenaxio.daos.JugadoresDAOFireBase;
import com.catenaxio.daos.JugadoresDAO_SQLite;
import com.catenaxio.interfaces.daos.ClasificacionDAO_SQLiteInterfaz;
import com.catenaxio.interfaces.daos.JornadasDAOInterfaz;
import com.catenaxio.interfaces.daos.JugadoresDAOInterfaz;
import com.catenaxio.interfaces.daos.JugadoresDAO_SQLiteInterfaz;

/**
 * Created by Antonio on 29/01/2017.
 */

public class ConnectionManager {

    private static JugadoresDAOInterfaz jugadoresDAOfb;
    private static JornadasDAOFireBase jornadasDAO;
    private static JugadoresDAO_SQLite jugadoresDAO_sqLite;
    private static ClasificacionDAO_SQLite clasificacionDAO_sqLite;

    public static JugadoresDAOInterfaz getJugadoresDAO(Context cnt, Jugadores jugadores, BaseAdapter adapter){

        jugadoresDAOfb =new JugadoresDAOFireBase(cnt, jugadores, adapter);

        return jugadoresDAOfb;
    }

    public static JornadasDAOInterfaz getJornadasDAO(Context cnt, Jornadas jornadas, BaseAdapter adapter){

        jornadasDAO=new JornadasDAOFireBase(cnt, jornadas, adapter);

        return jornadasDAO;
    }

    public static JugadoresDAO_SQLiteInterfaz getJugadoresDAO_SQLite(Context cnt){

        jugadoresDAO_sqLite= new JugadoresDAO_SQLite(cnt);

        return jugadoresDAO_sqLite;
    }

    public static ClasificacionDAO_SQLiteInterfaz getClasificacionDAO_SQLite(Context cnt){

        clasificacionDAO_sqLite= new ClasificacionDAO_SQLite(cnt);

        return clasificacionDAO_sqLite;
    }
}
