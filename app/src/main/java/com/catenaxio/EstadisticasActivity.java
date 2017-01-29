package com.catenaxio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Vector;

public class EstadisticasActivity extends Activity implements View.OnClickListener{
    private Vector<String> lista_partidos;
    private Vector<String> lista_titulares;
    private Vector<String> lista_goles;
    private Vector<String> lista_asistencias;
    private Vector<String> lista_partidosGanados;
    private Vector<String> lista_porcentajeGoles;
    private Vector<Integer> pctg_goles;

    private ListView miLista;
    private Button botonGrafica;
    private double golesTotales=0.0;

    private String[] nombres={"AbelG","AbelD","Anton","Cano","Hugo","Jordan","Juan","Juanma","Meri","Invitado"};//FireBase
    public MiAdaptadorEstadistica adapter;

    //firebase
    private DatabaseReference mDatabase;
    private String partidosTotStr="20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE);
        lista_partidos=new Vector<String>();
        lista_titulares=new Vector<String>();
        lista_goles=new Vector<String>();
        lista_asistencias=new Vector<String>();
        lista_partidosGanados=new Vector<String>();
        lista_porcentajeGoles=new Vector<String>();
        pctg_goles=new Vector<Integer>();

        for(int i=0;i<=9;i++){
            int z=0;
            lista_partidos.add(sharedPref.getString(nombres[i]+"partidos", "0"));
            lista_titulares.add(sharedPref.getString(nombres[i]+"titulares", "0"));
            lista_goles.add(sharedPref.getString(nombres[i]+"goles", "0"));
            lista_asistencias.add(sharedPref.getString(nombres[i]+"asistencias", "0"));
            lista_partidosGanados.add(sharedPref.getString(nombres[i]+"partidosGanados", "0"));
            lista_porcentajeGoles.add(sharedPref.getString(nombres[i]+"porcentajeGoles", "0.0"));
            pctg_goles.add(sharedPref.getInt(nombres[i] + "pctgGoles", z));
        }

        miLista=(ListView)findViewById(R.id.listView);
        adapter=new MiAdaptadorEstadistica(this,lista_partidos,lista_titulares,lista_goles,lista_asistencias,lista_partidosGanados,lista_porcentajeGoles);
        miLista.setAdapter(adapter);

        botonGrafica=(Button)findViewById(R.id.botonPorcentajeGoles);
        botonGrafica.setOnClickListener(this);

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

        if(view==botonGrafica){//firebase

            Intent i =new Intent(this, QuesitoActivity.class);
            i.putExtra("AbelG",pctg_goles.get(0));
            i.putExtra("AbelD",pctg_goles.get(1));
            i.putExtra("Anton",pctg_goles.get(2));
            i.putExtra("Cano",pctg_goles.get(3));
            i.putExtra("Hugo",pctg_goles.get(4));
            i.putExtra("Jordan",pctg_goles.get(5));
            i.putExtra("Juan",pctg_goles.get(6));
            i.putExtra("Juanma",pctg_goles.get(7));
            i.putExtra("Meri",pctg_goles.get(8));
            i.putExtra("Invitado", pctg_goles.get(9));
            startActivity(i);

        }
    }

    private void limpiarArrays(){
        if(!lista_partidos.isEmpty()){
            lista_partidos.clear();
        }
        if(!lista_titulares.isEmpty()){
            lista_titulares.clear();
        }
        if(!lista_goles.isEmpty()){
            lista_goles.clear();
        }
        if(!lista_asistencias.isEmpty()){
            lista_asistencias.clear();
        }
        if(!lista_partidosGanados.isEmpty()){
            lista_partidosGanados.clear();
        }
        if(!lista_porcentajeGoles.isEmpty()){
            lista_porcentajeGoles.clear();
        }
        if(!pctg_goles.isEmpty()){
            pctg_goles.clear();
        }
    }

    //asynk task para guardar preferencias
    class GuardarPrefTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            for(int i=0;i<10;i++){
                editor.putString(nombres[i]+"partidos", lista_partidos.get(i));//PT
                editor.putString(nombres[i]+"titulares", lista_titulares.get(i));//PJ
                editor.putString(nombres[i]+"goles", lista_goles.get(i));
                editor.putString(nombres[i]+"asistencias", lista_asistencias.get(i));
                editor.putString(nombres[i]+"partidosGanados", lista_partidosGanados.get(i));//PG
                editor.putString(nombres[i]+"porcentajeGoles", lista_porcentajeGoles.get(i));
                editor.putInt(nombres[i]+"pctgGoles", pctg_goles.get(i));
                editor.apply();
            }
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarFirebase();
    }

    private void cargarFirebase(){
        //Aqui se decide, segun lo elegido en el menu Settings, que temporada cargar
        String statsElegidas="Jugadores2016";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17")){
            statsElegidas="Jugadores2016";
            partidosTotStr="20";
        }
        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2015-16")){
            statsElegidas="Jugadores2015";
            partidosTotStr="22";
        }
        //selecciono la rama de firebase adecuada
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Estadisticas").child(statsElegidas);
        golesTotales=0.0;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot jugador : dataSnapshot.getChildren()){
                    golesTotales+=Double.parseDouble(jugador.child("Goles").getValue().toString());
                }
                limpiarArrays();
                int i=0;
                for(DataSnapshot jugador : dataSnapshot.getChildren()){
                    lista_partidos.add(jugador.child("PJ").getValue().toString());
                    lista_partidosGanados.add(jugador.child("PG").getValue().toString());
                    lista_asistencias.add(jugador.child("Asistencias").getValue().toString());
                    lista_goles.add(jugador.child("Goles").getValue().toString());
                    lista_titulares.add(partidosTotStr);
                    pctg_goles.add(Integer.parseInt(jugador.child("Goles").getValue().toString()));

                    double pctgG = Double.parseDouble(lista_goles.get(i));
                    pctgG = (pctgG / golesTotales) * 100;
                    DecimalFormat decimales = new DecimalFormat("0.0");
                    lista_porcentajeGoles.add(decimales.format(pctgG));
                    i++;
                }
                adapter.notifyDataSetChanged();
                new GuardarPrefTask().execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Nuestros servidores estan ocupados ahora", Toast.LENGTH_LONG).show();
            }
        });
    }
}
