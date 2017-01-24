package com.catenaxio.interfaces.daos;

import com.catenaxio.beans.Jugadores;

/**
 * Created by Antonio on 21/01/2017.
 */

public interface JugadoresDAOInterfaz {

    /**
     *
     */
    public void downloadJugadores();

    public boolean updateJugadores(Jugadores jugadores);
}
