package com.catenaxio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Vector;

public class ConvocatoriaActivity extends Activity implements View.OnClickListener{

    private ListView listaConvocatoria;
    private Button botonConvocatoria;
    private RadioGroup eleccionConvocatoria;
    private Spinner eleccionJugador;
    private Button refresh;
    private TextView textoAsistConf;

    public static final String PREFS_NAME = "Preferencias";
    public String jugadorString="";
    public int resultado_enviar=0;

    private Vector<Integer> lista_bajas;
    private Vector<String> lista_fechasAct;

    public MiAdaptadorConvocatoria adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocatoria);

        //uno mi interface
        textoAsistConf=(TextView) findViewById(R.id.textAsistencias);
        listaConvocatoria=(ListView)findViewById(R.id.listViewConvocatoria);
        botonConvocatoria=(Button)findViewById(R.id.botonEnviarConvocatoria);
        botonConvocatoria.setOnClickListener(this);
        eleccionConvocatoria=(RadioGroup)findViewById(R.id.radioEleccion);
        eleccionJugador=(Spinner)findViewById(R.id.spinnerJugador);
        refresh=(Button)findViewById(R.id.botonRefresh);
        refresh.setOnClickListener(this);

        //pongo jugador por defecto
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int jugador=sharedPref.getInt("jugadorNombre", 1);

        textoAsistConf.setText(sharedPref.getString("Asistencias","Asistencias Confirmadas: 0"));

        Log.d("mostrar", "jugador antes: " + jugador);
        eleccionJugador.setSelection(jugador);
        jugadorString=eleccionJugador.getSelectedItem().toString();
        Log.d("mostrar","jugador seleccionado:"+jugadorString);

        //ver las preferencias de jugadores anterior a la carga (para cuando no tenga internet que sea el usuario cuando decida conectar)

        lista_bajas=new Vector<Integer>();
        lista_bajas.add(sharedPref.getInt("Juan", 2));
        lista_bajas.add(sharedPref.getInt("Juanma", 2));
        lista_bajas.add(sharedPref.getInt("Hugo", 2));
        lista_bajas.add(sharedPref.getInt("Meri", 2));
        lista_bajas.add(sharedPref.getInt("Cano", 2));
        lista_bajas.add(sharedPref.getInt("Anton", 2));
        lista_bajas.add(sharedPref.getInt("Jordan", 2));
        lista_bajas.add(sharedPref.getInt("AbelD", 2));
        lista_bajas.add(sharedPref.getInt("AbelG", 1));
        //lista_bajas_actualizado=new Vector<Integer>();
        lista_fechasAct=new Vector<String>();
        for(int i=0; i<9;i++){
            lista_fechasAct.add(sharedPref.getString("FechaAct"+i,"Actualizado:\n01/01/2015"));
        }

        adapter=new MiAdaptadorConvocatoria(this,lista_bajas, lista_fechasAct);
        listaConvocatoria.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.convocatoria, menu);
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

    public void enviar(View vista){

    }

    @Override
    public void onClick(View view) {
        if(view==botonConvocatoria){
            Log.d("mostrar","muestro enviar");

            if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_voy){
                resultado_enviar=0;
            }
            else if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_novoy){
                resultado_enviar=1;
            }
            else if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_duda){
                resultado_enviar=2;
            }
            Log.d("mostrar","pulso" + resultado_enviar);

            Log.d("mostrar","jugador: "+eleccionJugador.getSelectedItemPosition());
            /*
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("jugadorNombre", eleccionJugador.getSelectedItemPosition());
            editor.commit();
            */
            jugadorString=eleccionJugador.getSelectedItem().toString();
            Log.d("mostrar", "jugador seleccionado:" + jugadorString);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Jugadores");
            query.whereEqualTo("Nombre", jugadorString);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> playerChosen, ParseException e) {
                    if (e == null) {
                        Log.d("score", "Retrieved " + playerChosen.size() + " scores");
                        playerChosen.get(0).put("Convocatoria", resultado_enviar);
                        playerChosen.get(0).saveInBackground();
                        updateBajas();
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
        }
        else if(view==refresh){
            Log.d("mostrar", "muestro refresh");
            updateBajas();
        }
    }

    private void updateBajas(){
        Toast.makeText(this,"Actualizando...",Toast.LENGTH_SHORT).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Jugadores");
        //query.whereEqualTo("Nombre", jugadorString);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> playerList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + playerList.size() + " scores");
                    int numAsistConf=0;
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();

                    for(int i=0; i<9;i++){
                        lista_bajas.setElementAt(playerList.get(i).getInt("Convocatoria"),i);
                        lista_fechasAct.setElementAt("Actualizado:\n"+ParseaFechas.parsearDateToString(playerList.get(i).getUpdatedAt()),i);
                        if(lista_bajas.get(i)==0){
                            numAsistConf++;
                        }
                        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        //SharedPreferences.Editor editor = settings.edit();
                        editor.putInt(playerList.get(i).getString("Nombre"), lista_bajas.get(i));
                        editor.putString("FechaAct"+i, lista_fechasAct.get(i));
                        editor.apply();
                    }
                    editor.putString("Asistencias","Asistencias Confirmadas: "+numAsistConf);
                    editor.apply();

                    textoAsistConf.setText("Asistencias Confirmadas: " + numAsistConf);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(),"Róbale Wifi al vecino",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
