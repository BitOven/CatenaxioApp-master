package com.catenaxio.daos;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.catenaxio.R;
import com.catenaxio.beans.Jornada;
import com.catenaxio.beans.Jornadas;
import com.catenaxio.interfaces.conexion.ConexionDB;
import com.catenaxio.interfaces.daos.JornadasDAOInterfaz;
import com.catenaxio.utils.ConexionFirebase;
import com.catenaxio.utils.MiParseador;
import com.catenaxio.utils.Preferencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Antonio on 21/01/2017.
 */

public class JornadasDAOFireBase implements JornadasDAOInterfaz {

    private DatabaseReference mDatabase;//firebase
    private Context appContext;
    private Jornadas jornadasRet;
    private BaseAdapter adapter;
    ConexionDB conn;

    /**
     *
     * @param appContext
     * @param jornadas
     * @param adapter
     */
    public JornadasDAOFireBase(Context appContext, Jornadas jornadas, BaseAdapter adapter){

        this.appContext=appContext;
        jornadasRet=jornadas;
        this.adapter=adapter;
    }

    @Override
    public void downloadJornadas(String temporada) {
        conn = new ConexionFirebase();
        mDatabase = (DatabaseReference)conn.conectar();
        mDatabase = mDatabase.child(temporada);
        descargarJornadas();//asincrono
    }

    @Override
    public void uploadJornadas(Jornadas jornadas) {

    }

    private void descargarJornadas(){

        mDatabase.addValueEventListener(new ValueEventListener() {//metodo asincrono
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                jornadasRet.resetJornadas();
                for(DataSnapshot jornada : dataSnapshot.getChildren()){
                    Jornada contenedor = new Jornada();
                    contenedor.setMarcador(jornada.child("Resultado").getValue().toString());
                    contenedor.setRival(jornada.child("Rival").getValue().toString());
                    contenedor.setLugar(jornada.child("Lugar").getValue().toString());
                    //parseamos la fecha
                    String fechaYhora[]  = MiParseador.parsearFechaYHora(jornada.child("Hora").getValue().toString());
                    contenedor.setFecha(fechaYhora[0]);
                    contenedor.setHora(fechaYhora[1]);
                    contenedor.setResultado(jornada.child("KeyResultado").getValue().toString());

                    if(jornada.child("URLCampo").exists()) {
                        contenedor.setUrlCampo(jornada.child("URLCampo").getValue().toString());
                    }else{
                        contenedor.setUrlCampo(MiParseador.urlCampoFinder(contenedor.getLugar()));
                    }
                    contenedor.setNumJornada(i+1);
                    jornadasRet.addJornada(contenedor);
                    i++;
                }
                Preferencias.guardarPreferenciasCalendario(appContext,jornadasRet);
                adapter.notifyDataSetChanged();
                conn.desconectar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appContext, "Nuestros servidores estan ocupados ahora", Toast.LENGTH_LONG).show();
                conn.desconectar();
            }
        });
    }
}
