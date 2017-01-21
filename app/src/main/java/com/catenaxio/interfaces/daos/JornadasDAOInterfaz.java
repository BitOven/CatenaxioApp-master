package com.catenaxio.interfaces.daos;

import com.catenaxio.beans.Jornadas;

/**
 * Created by Antonio on 21/01/2017.
 */

public interface JornadasDAOInterfaz {

    /**
     *
     * @param temporada
     * @return null si no descargó nada
     */
    public void downloadJornadas(String temporada);

    public void uploadJornadas(Jornadas jornadas);
}
