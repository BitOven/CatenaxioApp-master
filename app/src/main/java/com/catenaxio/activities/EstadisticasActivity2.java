package com.catenaxio.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.catenaxio.R;
import com.catenaxio.SettingsActivity;
import com.catenaxio.adapters.MiAdaptadorEstadistica2;

import com.catenaxio.beans.Jugador;
import com.catenaxio.beans.JugadorQuesito;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.daos.JugadoresDAOFireBase;
import com.catenaxio.interfaces.daos.JugadoresDAOInterfaz;
import com.catenaxio.managers.ConnectionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Antonio on 22/01/2017.
 */

public class EstadisticasActivity2 extends Activity implements View.OnClickListener{

    private Jugadores jugadores;
    private JugadoresDAOInterfaz jugDAO;

    private ListView miLista;
    private Button botonGrafica;
    private BaseAdapter adapter;

    //firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        jugadores = new Jugadores();
        ConnectionManager.getJugadoresDAO_SQLite(this).getJugadores(jugadores);//actualiza jugadores desde sql

        miLista=(ListView)findViewById(R.id.listView);
        adapter=new MiAdaptadorEstadistica2(this,jugadores);
        miLista.setAdapter(adapter);

        botonGrafica=(Button)findViewById(R.id.botonPorcentajeGoles);
        botonGrafica.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO no cargar imagenes de internet si ya estan en sql (peor exp de usuario)
        jugDAO= ConnectionManager.getJugadoresDAO(this,jugadores,adapter);
        jugDAO.downloadJugadores();//actualiza los jugadores desde firebase
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.estadisticas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity( new Intent(this,SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if(view==botonGrafica){
            Intent i = new Intent(this, QuesitoActivity2.class);
            List<JugadorQuesito> jugadoresQuesito = new ArrayList<JugadorQuesito>();
            for(Jugador jug : jugadores.getJugadores()){
                JugadorQuesito jQuesito = new JugadorQuesito(jug.getNombre(),jug.getGoles(),jug.getPorcentajeGoles());
                jugadoresQuesito.add(jQuesito);
            }
            i.putExtra("jugadores", (Serializable) jugadoresQuesito);

            startActivity(i);
        }
    }

    //asynk task para guardar preferencias (sustituido por sql)
//    class GuardarPrefTask extends AsyncTask<Void,Void,Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            SharedPreferences settings = getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE);
//            SharedPreferences.Editor editor = settings.edit();
//            for(int i=0;i<10;i++){
//                editor.putString(nombres[i]+"partidos", lista_partidos.get(i));//PT
//                editor.putString(nombres[i]+"titulares", lista_titulares.get(i));//PJ
//                editor.putString(nombres[i]+"goles", lista_goles.get(i));
//                editor.putString(nombres[i]+"asistencias", lista_asistencias.get(i));
//                editor.putString(nombres[i]+"partidosGanados", lista_partidosGanados.get(i));//PG
//                editor.putString(nombres[i]+"porcentajeGoles", lista_porcentajeGoles.get(i));
//                editor.putInt(nombres[i]+"pctgGoles", pctg_goles.get(i));
//                editor.apply();
//            }
//            return null;
//        }
//    }

    //cambiado a DAO
//    private void cargarFirebase(){
//        //Aqui se decide, segun lo elegido en el menu Settings, que temporada cargar
//        String statsElegidas="Jugadores2016";
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17")){
//            statsElegidas="Jugadores2016";
//            partidosTotStr="20";
//        }
//        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2015-16")){
//            statsElegidas="Jugadores2015";
//            partidosTotStr="22";
//        }
//        //selecciono la rama de firebase adecuada
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Estadisticas").child(statsElegidas);
//        golesTotales=0.0;
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot jugador : dataSnapshot.getChildren()){
//                    golesTotales+=Double.parseDouble(jugador.child("Goles").getValue().toString());
//                }
//                limpiarArrays();
//                int i=0;
//                for(DataSnapshot jugador : dataSnapshot.getChildren()){
//                    lista_partidos.add(jugador.child("PJ").getValue().toString());
//                    lista_partidosGanados.add(jugador.child("PG").getValue().toString());
//                    lista_asistencias.add(jugador.child("Asistencias").getValue().toString());
//                    lista_goles.add(jugador.child("Goles").getValue().toString());
//                    lista_titulares.add(partidosTotStr);
//                    pctg_goles.add(Integer.parseInt(jugador.child("Goles").getValue().toString()));
//
//                    double pctgG = Double.parseDouble(lista_goles.get(i));
//                    pctgG = (pctgG / golesTotales) * 100;
//                    DecimalFormat decimales = new DecimalFormat("0.0");
//                    lista_porcentajeGoles.add(decimales.format(pctgG));
//                    i++;
//                }
//                adapter.notifyDataSetChanged();
//                new GuardarPrefTask().execute();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), "Nuestros servidores estan ocupados ahora", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}
