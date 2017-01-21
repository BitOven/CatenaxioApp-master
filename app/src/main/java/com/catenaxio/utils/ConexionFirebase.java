package com.catenaxio.utils;

import com.catenaxio.interfaces.conexion.ConexionDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Antonio on 21/01/2017.
 */

public class ConexionFirebase implements ConexionDB{

    //firebase
    private DatabaseReference mDatabase;

    @Override
    public Object conectar() {
        return mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void desconectar() {

    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

}
