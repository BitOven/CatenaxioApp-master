package com.catenaxio.interfaces.daos;

import com.catenaxio.beans.Jornadas;

/**
 * Created by Antonio on 21/01/2017.
 */

public interface JornadasDAOInterfaz {

    public void downloadJornadas();

    public void uploadJornadas(Jornadas jornadas);
}
