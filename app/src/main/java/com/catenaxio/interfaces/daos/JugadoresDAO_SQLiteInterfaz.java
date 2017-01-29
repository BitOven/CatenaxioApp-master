package com.catenaxio.interfaces.daos;

import com.catenaxio.beans.Jugador;
import com.catenaxio.beans.Jugadores;

/**
 * Created by Antonio on 29/01/2017.
 */

public interface JugadoresDAO_SQLiteInterfaz {

    /**
     * guarda datos de un jugador en la sql
     * @param jugador
     * @return 0 o -1 if there was a fail, >0 if ok
     */
    long updatePlayer(Jugador jugador);

    /**
     *  guarda los datos de Jugadores en la sql
     * @param jugadores
     * @return false si algo salio mal, true si fue ok
     */
    boolean updatePlayers(Jugadores jugadores);

    void getJugadores(Jugadores jugadores);

    void closeDB();

    /**
     *
     * @param jugador
     * @return 1 if ok
     */
    int updateImagePlayer(Jugador jugador, byte[] byteArr);

}
