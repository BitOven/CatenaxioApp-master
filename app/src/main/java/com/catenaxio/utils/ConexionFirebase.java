package com.catenaxio.utils;

import com.catenaxio.interfaces.conexion.ConexionDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Antonio on 21/01/2017.
 */

public class ConexionFirebase implements ConexionDB{

    @Override
    public Object conectar() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void desconectar() {

    }

}
