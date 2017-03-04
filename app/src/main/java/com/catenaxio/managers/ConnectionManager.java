package com.catenaxio.managers;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.catenaxio.beans.Clasificacion;
import com.catenaxio.beans.Jornadas;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.daos.ClasificacionDAOFirebase;
import com.catenaxio.daos.ClasificacionDAO_SQLite;
import com.catenaxio.daos.JornadasDAOFireBase;
import com.catenaxio.daos.JugadoresDAOFireBase;
import com.catenaxio.daos.JugadoresDAO_SQLite;
import com.catenaxio.interfaces.daos.ClasificacionDAOInterfaz;
import com.catenaxio.interfaces.daos.ClasificacionDAO_SQLiteInterfaz;
import com.catenaxio.interfaces.daos.JornadasDAOInterfaz;
import com.catenaxio.interfaces.daos.JugadoresDAOInterfaz;
import com.catenaxio.interfaces.daos.JugadoresDAO_SQLiteInterfaz;
import com.catenaxio.utils.ConexionFirebase;

/**
 * Created by Antonio on 29/01/2017.
 */

public class ConnectionManager {

    /**
     *
     * @return objeto de tipo ConexionFirebase, cuyo metodo conectar devuelve un objeto de tipo DatabaseReference
     */
    public static ConexionFirebase getConexionFirebase() {

        return new ConexionFirebase();
    }

    public static JugadoresDAOInterfaz getJugadoresDAO(Context cnt, Jugadores jugadores, BaseAdapter adapter){

        return new JugadoresDAOFireBase(cnt, jugadores, adapter);

    }

    public static JornadasDAOInterfaz getJornadasDAO(Context cnt, Jornadas jornadas, BaseAdapter adapter){

        return new JornadasDAOFireBase(cnt, jornadas, adapter);

    }

    public static JugadoresDAO_SQLiteInterfaz getJugadoresDAO_SQLite(Context cnt){

        return new JugadoresDAO_SQLite(cnt);

    }

    public static ClasificacionDAO_SQLiteInterfaz getClasificacionDAO_SQLite(Context cnt){

        return new ClasificacionDAO_SQLite(cnt);

    }

    /**
     * Metodo que devuelve un ClasificacionDAOInterfaz
     * @param imageView
     * @param clasificacion
     * @param cnt
     * @return
     */
    public static ClasificacionDAOInterfaz getClasificacionDAOFirebase(ImageView imageView, Clasificacion clasificacion, Context cnt){

        return new ClasificacionDAOFirebase(imageView, clasificacion, cnt);
    }
}
