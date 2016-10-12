package com.catenaxio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.catenaxio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Vector;

public class CalendarioActivity extends Activity {

    private Vector<String> lista_jornada;
    private Vector<String> lista_fecha;
    private Vector<String> lista_hora;
    private Vector<String> lista_rival;
    private Vector<String> lista_lugar;
    private Vector<Integer> lista_estadios;
    private Vector<String> lista_result;
    private Vector<Uri> lista_maps;
    private boolean temporadaActual;

    public static final String PREFS_NAME = "Preferencias";

    //url de los campos
    private static final String JUANDE_LOCATION = "https://www.google.es/maps/place/Polideportivo+Municipal+Juan+De+La+Cierva/@40.3183028,-3.7196404,303m/data=!3m1!1e3!4m5!3m4!1s0x0:0x61fc5377228ef5df!8m2!3d40.3183003!4d-3.7186662";
    private static final String PERALES_LOCATION = "https://www.google.es/maps/place/Calle+de+Groenlandia,+8,+28909+Getafe,+Madrid/@40.323022,-3.6628751,193m/data=!3m2!1e3!4b1!4m5!3m4!1s0xd4223fd57232aef:0x175882019e4f8968!8m2!3d40.323022!4d-3.6620753";
    private static final String GINER_LOCATION = "https://www.google.es/maps/place/Polideportivo+Giner+de+los+Rios/@40.3137777,-3.7400741,165m/data=!3m1!1e3!4m5!3m4!1s0x0:0x17a2613a734b7759!8m2!3d40.3138204!4d-3.7395425";
    private static final String SECTOR3_LOCATION = "https://www.google.es/maps/place/Complejo+Deportivo+Sector+3+Alh%C3%B3ndiga/@40.3147782,-3.7444435,116m/data=!3m1!1e3!4m5!3m4!1s0x0:0x8f0474c33198fb69!8m2!3d40.3148757!4d-3.7437737";
    private static final String M4_LOCATION = "https://www.google.es/maps/place/Av+Francisco+Fernandez+Ordo%C3%B1ez,+10,+28903+Getafe,+Madrid/@40.3151358,-3.7154767,111m/data=!3m1!1e3!4m5!3m4!1s0xd4220c37afa6da1:0xed094409cc05dcc2!8m2!3d40.3152065!4d-3.7150857";

    private ListView miLista;
    private MiAdaptador adapter;

    //firebase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        //creo los vectores
        lista_jornada=new Vector<String>();
        lista_fecha=new Vector<String>();
        lista_hora=new Vector<String>();
        lista_rival=new Vector<String>();
        lista_lugar=new Vector<String>();
        lista_estadios=new Vector<Integer>();
        lista_result=new Vector<String>();
        lista_maps=new Vector<Uri>();

        //pongo jugador por defecto
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        lista_jornada.add("1");
        lista_jornada.add("2");
        lista_jornada.add("3");
        lista_jornada.add("4");
        lista_jornada.add("5");
        lista_jornada.add("6");
        lista_jornada.add("7");
        lista_jornada.add("8");
        lista_jornada.add("9");
        lista_jornada.add("10");
        lista_jornada.add("11");
        lista_jornada.add("12");
        lista_jornada.add("13");
        lista_jornada.add("14");
        lista_jornada.add("15");
        lista_jornada.add("16");
        lista_jornada.add("17");
        lista_jornada.add("18");
        lista_jornada.add("19");
        lista_jornada.add("20");
        lista_jornada.add("21");
        lista_jornada.add("22");

        lista_fecha.add(sharedPref.getString("fechaPref"+1,"01/10/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+2,"08/10/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+3,"16/10/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+4,"22/10/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+5,"06/11/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+6,"12/11/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+7,"20/11/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+8,"26/11/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+9,"04/12/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+10,"17/12/16"));
        lista_fecha.add(sharedPref.getString("fechaPref"+11,"14/01/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+12,"21/01/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+13,"28/01/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+14,"05/02/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+15,"11/02/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+16,"19/02/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+17,"25/02/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+18,"05/03/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+19,"11/03/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+20,"19/03/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+21,"25/03/17"));
        lista_fecha.add(sharedPref.getString("fechaPref"+22,"01/04/17"));

        lista_hora.add(sharedPref.getString("horaPref"+1,"15:00"));
        lista_hora.add(sharedPref.getString("horaPref"+2,"18:00"));
        lista_hora.add(sharedPref.getString("horaPref"+3,"14:00"));
        lista_hora.add(sharedPref.getString("horaPref"+4,"16:00"));
        lista_hora.add(sharedPref.getString("horaPref"+5,"09:00"));
        lista_hora.add(sharedPref.getString("horaPref"+6,"20:30"));
        lista_hora.add(sharedPref.getString("horaPref"+7,"17:00"));
        lista_hora.add(sharedPref.getString("horaPref"+8,"15:00"));
        lista_hora.add(sharedPref.getString("horaPref"+9,"13:00"));
        lista_hora.add(sharedPref.getString("horaPref"+10,"19:00"));
        lista_hora.add(sharedPref.getString("horaPref"+11,"16:00"));
        lista_hora.add(sharedPref.getString("horaPref"+12,"15:00"));
        lista_hora.add(sharedPref.getString("horaPref"+13,"18:00"));
        lista_hora.add(sharedPref.getString("horaPref"+14,"14:00"));
        lista_hora.add(sharedPref.getString("horaPref"+15,"16:00"));
        lista_hora.add(sharedPref.getString("horaPref"+16,"09:00"));
        lista_hora.add(sharedPref.getString("horaPref"+17,"20:30"));
        lista_hora.add(sharedPref.getString("horaPref"+18,"16:00"));
        lista_hora.add(sharedPref.getString("horaPref"+19,"15:00"));
        lista_hora.add(sharedPref.getString("horaPref"+20,"13:00"));
        lista_hora.add(sharedPref.getString("horaPref"+21,"19:00"));
        lista_hora.add(sharedPref.getString("horaPref"+22,"16:00"));

        lista_rival.add(sharedPref.getString("rivalPref"+1,"DEL TIRON"));
        lista_rival.add(sharedPref.getString("rivalPref"+2,"GETAFE ACHEN"));
        lista_rival.add(sharedPref.getString("rivalPref"+3,"MINABO DE KIEV"));
        lista_rival.add(sharedPref.getString("rivalPref"+4,"RED BENCHES"));
        lista_rival.add(sharedPref.getString("rivalPref"+5,"PERALES EN MARCHA"));
        lista_rival.add(sharedPref.getString("rivalPref"+6,"TABERNA DEL TARAO"));
        lista_rival.add(sharedPref.getString("rivalPref"+7,"MESON LA SARTEN"));
        lista_rival.add(sharedPref.getString("rivalPref"+8,"COMUNITY FUTSAL"));
        lista_rival.add(sharedPref.getString("rivalPref"+9,"GRENADE UNITED PARK"));
        lista_rival.add(sharedPref.getString("rivalPref"+10,"SANI MAGIC DE ISLAMAR"));
        lista_rival.add(sharedPref.getString("rivalPref"+11,"CLASSIC GETAFE"));
        lista_rival.add(sharedPref.getString("rivalPref"+12,"DEL TIRON"));
        lista_rival.add(sharedPref.getString("rivalPref"+13,"GETAFE ACHEN"));
        lista_rival.add(sharedPref.getString("rivalPref"+14,"MINABO DE KIEV"));
        lista_rival.add(sharedPref.getString("rivalPref"+15,"RED BENCHES"));
        lista_rival.add(sharedPref.getString("rivalPref"+16,"PERALES EN MARCHA"));
        lista_rival.add(sharedPref.getString("rivalPref"+17,"TABERNA DEL TARAO"));
        lista_rival.add(sharedPref.getString("rivalPref"+18,"MESON LA SARTEN"));
        lista_rival.add(sharedPref.getString("rivalPref"+19,"COMUNITY FUTSAL"));
        lista_rival.add(sharedPref.getString("rivalPref"+20,"GRENADE UNITED PARK"));
        lista_rival.add(sharedPref.getString("rivalPref"+21,"SANI MAGIC DE ISLAMAR"));
        lista_rival.add(sharedPref.getString("rivalPref"+22,"CLASSIC GETAFE"));

        for(int i=1; i<23; i++){
            lista_estadios.add(sharedPref.getInt("color"+i, (i%2)));
        }

        lista_lugar.add(sharedPref.getString("campoPref"+1,"JUAN DE LA CIERVA"));
        lista_lugar.add(sharedPref.getString("campoPref"+2,"PERALES"));
        lista_lugar.add(sharedPref.getString("campoPref"+3,"JUAN DE LA CIERVA"));
        lista_lugar.add(sharedPref.getString("campoPref"+4,"GINER 1"));
        lista_lugar.add(sharedPref.getString("campoPref"+5,"SECTOR III"));
        lista_lugar.add(sharedPref.getString("campoPref"+6,"GINER 2"));
        lista_lugar.add(sharedPref.getString("campoPref"+7,"M-4"));
        lista_lugar.add(sharedPref.getString("campoPref"+8,"M-4"));
        lista_lugar.add(sharedPref.getString("campoPref"+9,"GINER 1"));
        lista_lugar.add(sharedPref.getString("campoPref"+10,"SECTOR III"));
        lista_lugar.add(sharedPref.getString("campoPref"+11,"JUAN DE LA CIERVA"));
        lista_lugar.add(sharedPref.getString("campoPref"+12,"JUAN DE LA CIERVA"));
        lista_lugar.add(sharedPref.getString("campoPref"+13,"PERALES"));
        lista_lugar.add(sharedPref.getString("campoPref"+14,"JUAN DE LA CIERVA"));
        lista_lugar.add(sharedPref.getString("campoPref"+15,"GINER 1"));
        lista_lugar.add(sharedPref.getString("campoPref"+16,"SECTOR III"));
        lista_lugar.add(sharedPref.getString("campoPref"+17,"GINER 2"));
        lista_lugar.add(sharedPref.getString("campoPref"+18,"JUAN DE LA CIERVA"));
        lista_lugar.add(sharedPref.getString("campoPref"+19,"M-4"));
        lista_lugar.add(sharedPref.getString("campoPref"+20,"GINER 1"));
        lista_lugar.add(sharedPref.getString("campoPref"+21,"SECTOR III"));
        lista_lugar.add(sharedPref.getString("campoPref"+22,"JUAN DE LA CIERVA"));

        lista_maps.add(Uri.parse(JUANDE_LOCATION));
        lista_maps.add(Uri.parse(PERALES_LOCATION));
        lista_maps.add(Uri.parse(JUANDE_LOCATION));
        lista_maps.add(Uri.parse(GINER_LOCATION));
        lista_maps.add(Uri.parse(SECTOR3_LOCATION));
        lista_maps.add(Uri.parse(GINER_LOCATION));
        lista_maps.add(Uri.parse(M4_LOCATION));
        lista_maps.add(Uri.parse(M4_LOCATION));
        lista_maps.add(Uri.parse(GINER_LOCATION));
        lista_maps.add(Uri.parse(SECTOR3_LOCATION));
        lista_maps.add(Uri.parse(JUANDE_LOCATION));
        lista_maps.add(Uri.parse(JUANDE_LOCATION));
        lista_maps.add(Uri.parse(PERALES_LOCATION));
        lista_maps.add(Uri.parse(JUANDE_LOCATION));
        lista_maps.add(Uri.parse(GINER_LOCATION));
        lista_maps.add(Uri.parse(SECTOR3_LOCATION));
        lista_maps.add(Uri.parse(GINER_LOCATION));
        lista_maps.add(Uri.parse(JUANDE_LOCATION));
        lista_maps.add(Uri.parse(M4_LOCATION));
        lista_maps.add(Uri.parse(GINER_LOCATION));
        lista_maps.add(Uri.parse(SECTOR3_LOCATION));
        lista_maps.add(Uri.parse(JUANDE_LOCATION));

        for(int i=1; i<23; i++){
            lista_result.add(sharedPref.getString("jornada"+i, "N/A"));
        }

        adapter= new MiAdaptador(this, lista_jornada, lista_fecha, lista_hora, lista_rival, lista_estadios, lista_lugar, lista_result);
        miLista=(ListView)findViewById(R.id.listView);
        miLista.setAdapter(adapter);
        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(temporadaActual) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(lista_maps.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //cargarCalendario();
        cargarCalendarioFirebase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendario, menu);
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

//    private void cargarCalendario(){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17"))temporadaActual=true;
//        if(!prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17"))temporadaActual=false;
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Partidos");
//        query.whereEqualTo("Temporada",prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)));
//        query.orderByAscending("Jornada");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList, ParseException e) {
//                if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                    for (int i = 0; i < scoreList.size(); i++) {
//                        if(scoreList.get(i).getString("Indice").equals("X")){
//                            String resultadoo = "N/A";
//                            int coloor = i%2;
//                            lista_result.setElementAt(resultadoo, i);
//                            lista_estadios.setElementAt(coloor,i);
//                            lista_rival.setElementAt(scoreList.get(i).getString("Rival"),i);
//                            lista_fecha.setElementAt(scoreList.get(i).getString("Fecha"),i);
//                            lista_hora.setElementAt(scoreList.get(i).getString("Hora"),i);
//                            lista_lugar.setElementAt(scoreList.get(i).getString("Campo"),i);
//                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor editor = settings.edit();
//                            editor.putString("jornada"+(i+1), resultadoo);
//                            editor.putInt("color"+(i+1),coloor);
//                            editor.putString("rivalPref"+(i+1),scoreList.get(i).getString("Rival"));
//                            editor.putString("fechaPref"+(i+1),scoreList.get(i).getString("Fecha"));
//                            editor.putString("horaPref"+(i+1),scoreList.get(i).getString("Hora"));
//                            editor.putString("campoPref"+(i+1),scoreList.get(i).getString("Campo"));
//                            editor.apply();
//                        }else{
//                            String resultadoo = scoreList.get(i).getNumber("GF") + "-" + scoreList.get(i).getNumber("GC");
//                            int coloor = scoreList.get(i).getInt("Intindice");
//                            lista_result.setElementAt(resultadoo, i);
//                            lista_estadios.setElementAt(coloor,i);
//                            lista_rival.setElementAt(scoreList.get(i).getString("Rival"),i);
//                            lista_fecha.setElementAt(scoreList.get(i).getString("Fecha"),i);
//                            lista_hora.setElementAt(scoreList.get(i).getString("Hora"),i);
//                            lista_lugar.setElementAt(scoreList.get(i).getString("Campo"),i);
//                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor editor = settings.edit();
//                            editor.putString("jornada"+(i+1), resultadoo);
//                            editor.putInt("color"+(i+1),coloor);
//                            editor.putString("rivalPref"+(i+1),lista_rival.get(i));
//                            editor.putString("fechaPref"+(i+1),lista_fecha.get(i));
//                            editor.putString("horaPref"+(i+1),lista_hora.get(i));
//                            editor.putString("campoPref"+(i+1),lista_lugar.get(i));
//                            editor.apply();
//                        }
//                        //Log.d("score", "Jornada numero: " + scoreList.get(i).getNumber("Jornada") + "resultado: " + scoreList.get(i).getNumber("GF") + "-" + scoreList.get(i).getNumber("GC"));
//                    }
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//    }

    private void cargarCalendarioFirebase(){
        String calendarioElegido="Calendario2016";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17")){
            temporadaActual=true;
            calendarioElegido="Calendario2016";
        }
        if(!prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17")){
            temporadaActual=false;
            calendarioElegido="Calendario2015";
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child(calendarioElegido);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot jornada : dataSnapshot.getChildren()){
                    String resultadoo = jornada.child("Resultado").getValue().toString();
                    lista_result.setElementAt(resultadoo,i);
                    lista_rival.setElementAt(jornada.child("Rival").getValue().toString(),i);
                    lista_lugar.setElementAt(jornada.child("Lugar").getValue().toString(),i);

                    //parseamos la fecha
                    String fechaYhora[]  = parsearHora(jornada.child("Hora").getValue().toString());
                    lista_fecha.setElementAt(fechaYhora[0],i);
                    lista_hora.setElementAt(fechaYhora[1],i);

                    //obtenemos color de celda
                    int coloor;
                    if(colorResultado(jornada.child("KeyResultado").getValue().toString())==1){
                        coloor = i%2;
                    }else{
                        coloor=colorResultado(jornada.child("KeyResultado").getValue().toString());
                    }
                    lista_estadios.setElementAt(coloor,i);
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("jornada"+(i+1), resultadoo);
                    editor.putInt("color"+(i+1),coloor);
                    editor.putString("rivalPref"+(i+1),lista_rival.get(i));
                    editor.putString("fechaPref"+(i+1),lista_fecha.get(i));
                    editor.putString("horaPref"+(i+1),lista_hora.get(i));
                    editor.putString("campoPref"+(i+1),lista_lugar.get(i));
                    editor.apply();
                    i++;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String[] parsearHora(String fechaYhora){
        String resultado[] = fechaYhora.split(" ");
        return resultado;
    }

    private int colorResultado (String keyResultado){
        int color=0;
        if(keyResultado.equalsIgnoreCase("X"))color=1;
        if(keyResultado.equalsIgnoreCase("G"))color=2;
        if(keyResultado.equalsIgnoreCase("E"))color=3;
        if(keyResultado.equalsIgnoreCase("P"))color=4;
        if(keyResultado.equalsIgnoreCase("D"))color=5;
        if(keyResultado.equalsIgnoreCase("A"))color=6;

        return color;
    }
}
