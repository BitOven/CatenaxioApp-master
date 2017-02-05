package com.catenaxio.daos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.catenaxio.R;
import com.catenaxio.beans.Jornadas;
import com.catenaxio.beans.Jugador;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.interfaces.conexion.ConexionDB;
import com.catenaxio.interfaces.daos.JugadoresDAOInterfaz;
import com.catenaxio.interfaces.daos.JugadoresDAO_SQLiteInterfaz;
import com.catenaxio.managers.ConnectionManager;
import com.catenaxio.utils.ConexionFirebase;
import com.catenaxio.utils.Constantes;
import com.catenaxio.utils.MiParseador;
import com.catenaxio.utils.Preferencias;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Antonio on 21/01/2017.
 */

public class JugadoresDAOFireBase implements JugadoresDAOInterfaz {

    private DatabaseReference mDatabase;//firebase
    private Context appContext;
    private Jugadores jugadores;
    private BaseAdapter adapter;
    private ConexionDB conn;


    public JugadoresDAOFireBase(Context appContext, Jugadores jugadores, BaseAdapter adapter){
        this.appContext = appContext;
        this.jugadores = jugadores;
        this.adapter = adapter;
    }

    @Override
    public void downloadJugadores() {
        conn = new ConexionFirebase();
        mDatabase = (DatabaseReference) conn.conectar();
        //entro en el nodo Estadisticas/Jugadores2016(o la temporada seleccionada)
        mDatabase = mDatabase.child(Constantes.FRBS_ESTADISTICAS).child(Constantes.FRBS_JUGADORES + MiParseador.parsearTemporadaAYear(appContext));
        descargarJugadores();
    }

    @Override
    public boolean updateJugadores(Jugadores jugadores) {
        return false;
    }

    private void descargarJugadores(){

        mDatabase.addValueEventListener(new ValueEventListener() {//metodo asincrono
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                jugadores.resetJugadores();
                jugadores.setTemporada( MiParseador.parsearTemporadaAYear(appContext));
                Jugador.setPartidosTotales(dataSnapshot.child(Constantes.FRBS_PARTIDOSTOTALES).getValue(Integer.class));

                for(DataSnapshot jugador : dataSnapshot.child(Constantes.FRBS_JUGADORES).getChildren()){
                    Jugador contenedor = new Jugador();
                    contenedor.setNombre(jugador.child(Constantes.FRBS_NOMBREJUGADORES).getValue().toString());
                    contenedor.setGoles(jugador.child(Constantes.FRBS_GOLES).getValue(Integer.class));
                    contenedor.setAsistencias(jugador.child(Constantes.FRBS_ASISTENCIAS).getValue(Integer.class));
                    contenedor.setPartidosGanados(jugador.child(Constantes.FRBS_PARTIDOSGANADOS).getValue(Integer.class));
                    contenedor.setPartidosJugados(jugador.child(Constantes.FRBS_PARTIDOSJUGADOS).getValue(Integer.class));
                    if(Constantes.IMG_JUGADORES.containsKey(contenedor.getNombre().toLowerCase())){
                        contenedor.setImageResource(Constantes.IMG_JUGADORES.get(contenedor.getNombre().toLowerCase()));
                    }else{
                        descargarImagenes(contenedor);
                    }
                    jugadores.addJugador(contenedor);
                    i++;
                }
                adapter.notifyDataSetChanged();
                conn.desconectar();

                JugadoresDAO_SQLiteInterfaz guardarSQL = ConnectionManager.getJugadoresDAO_SQLite(appContext);
                if(!guardarSQL.updatePlayers(jugadores)){
                    Log.d(this.getClass().getSimpleName(), "algo fue mal en el guardado sql");
                }
                guardarSQL.closeDB();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appContext, "Nuestros servidores estan ocupados ahora", Toast.LENGTH_LONG).show();
                conn.desconectar();
            }
        });
    }

    private void descargarImagenes(Jugador jugador){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference stRef = storage.getReference().child(Constantes.JUGADORES_STORAGE)
                .child(jugador.getNombre().toLowerCase()+Constantes.JPG_EXTENSION);
        final long ONE_MEGABYTE = 1024 * 1024;
        stRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new MiSuccessListener(jugador)).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(this.getClass().getSimpleName(),e.getLocalizedMessage());
            }
        });
    }

    //descarga y setea la imagen descargada
    private class MiSuccessListener implements OnSuccessListener{

        Jugador jug;

        public MiSuccessListener(Jugador jugador){
            super();
            jug=jugador;
        }

        @Override
        public void onSuccess(Object o) {
            byte[] bytes = (byte[])o;
            jug.setImagen(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            ConnectionManager.getJugadoresDAO_SQLite(appContext).updateImagePlayer(jug, bytes);
            adapter.notifyDataSetChanged();
        }
    }
}
