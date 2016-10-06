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

    public static final String PREFS_NAME = "Preferencias";

    //url de los campos
    private static final String JUANDE_LOCATION = "https://www.google.es/maps/place/Polideportivo+Municipal+Juan+De+La+Cierva/@40.3183028,-3.7196404,303m/data=!3m1!1e3!4m5!3m4!1s0x0:0x61fc5377228ef5df!8m2!3d40.3183003!4d-3.7186662";
    private static final String PERALES_LOCATION = "https://www.google.es/maps/place/Calle+de+Groenlandia,+8,+28909+Getafe,+Madrid/@40.323022,-3.6628751,193m/data=!3m2!1e3!4b1!4m5!3m4!1s0xd4223fd57232aef:0x175882019e4f8968!8m2!3d40.323022!4d-3.6620753";
    private static final String GINER_LOCATION = "https://www.google.es/maps/place/Polideportivo+Giner+de+los+Rios/@40.3137777,-3.7400741,165m/data=!3m1!1e3!4m5!3m4!1s0x0:0x17a2613a734b7759!8m2!3d40.3138204!4d-3.7395425";
    private static final String SECTOR3_LOCATION = "https://www.google.es/maps/place/Complejo+Deportivo+Sector+3+Alh%C3%B3ndiga/@40.3147782,-3.7444435,116m/data=!3m1!1e3!4m5!3m4!1s0x0:0x8f0474c33198fb69!8m2!3d40.3148757!4d-3.7437737";
    private static final String M4_LOCATION = "https://www.google.es/maps/place/Av+Francisco+Fernandez+Ordo%C3%B1ez,+10,+28903+Getafe,+Madrid/@40.3151358,-3.7154767,111m/data=!3m1!1e3!4m5!3m4!1s0xd4220c37afa6da1:0xed094409cc05dcc2!8m2!3d40.3152065!4d-3.7150857";

    private ListView miLista;
    private MiAdaptador adapter;

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

        lista_fecha.add("01/10/16");
        lista_fecha.add("08/10/16");
        lista_fecha.add("16/10/16");
        lista_fecha.add("22/10/16");
        lista_fecha.add("06/11/16");
        lista_fecha.add("12/11/16");
        lista_fecha.add("20/11/16");
        lista_fecha.add("26/11/16");
        lista_fecha.add("04/12/16");
        lista_fecha.add("17/12/16");
        lista_fecha.add("14/01/17");
        lista_fecha.add("21/01/17");
        lista_fecha.add("28/01/17");
        lista_fecha.add("05/02/17");
        lista_fecha.add("11/02/17");
        lista_fecha.add("19/02/17");
        lista_fecha.add("25/02/17");
        lista_fecha.add("05/03/17");
        lista_fecha.add("11/03/17");
        lista_fecha.add("19/03/17");
        lista_fecha.add("25/03/17");
        lista_fecha.add("01/04/17");

        lista_hora.add("15:00");
        lista_hora.add("18:00");
        lista_hora.add("14:00");
        lista_hora.add("16:00");
        lista_hora.add("09:00");
        lista_hora.add("20:30");
        lista_hora.add("17:00");
        lista_hora.add("15:00");
        lista_hora.add("13:00");
        lista_hora.add("19:00");
        lista_hora.add("16:00");
        lista_hora.add("15:00");
        lista_hora.add("18:00");
        lista_hora.add("14:00");
        lista_hora.add("16:00");
        lista_hora.add("09:00");
        lista_hora.add("20:30");
        lista_hora.add("16:00");
        lista_hora.add("15:00");
        lista_hora.add("13:00");
        lista_hora.add("19:00");
        lista_hora.add("16:00");

        lista_rival.add("DEL TIRON");
        lista_rival.add("GETAFE ACHEN");
        lista_rival.add("MINABO DE KIEV");
        lista_rival.add("RED BENCHES");
        lista_rival.add("PERALES EN MARCHA");
        lista_rival.add("TABERNA DEL TARAO");
        lista_rival.add("MESON LA SARTEN");
        lista_rival.add("COMUNITY FUTSAL");
        lista_rival.add("GRENADE UNITED PARK");
        lista_rival.add("SANI MAGIC DE ISLAMAR");
        lista_rival.add("CLASSIC GETAFE");
        lista_rival.add("DEL TIRON");
        lista_rival.add("GETAFE ACHEN");
        lista_rival.add("MINABO DE KIEV");
        lista_rival.add("RED BENCHES");
        lista_rival.add("PERALES EN MARCHA");
        lista_rival.add("TABERNA DEL TARAO");
        lista_rival.add("MESON LA SARTEN");
        lista_rival.add("COMUNITY FUTSAL");
        lista_rival.add("GRENADE UNITED PARK");
        lista_rival.add("SANI MAGIC DE ISLAMAR");
        lista_rival.add("CLASSIC GETAFE");


        for(int i=1; i<23; i++){
            lista_estadios.add(sharedPref.getInt("color"+i, (i%2)));
        }


        lista_lugar.add("JUAN DE LA CIERVA");
        lista_lugar.add("PERALES");
        lista_lugar.add("JUAN DE LA CIERVA");
        lista_lugar.add("GINER 1");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("GINER 2");
        lista_lugar.add("M-4");
        lista_lugar.add("M-4");
        lista_lugar.add("GINER 1");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("JUAN DE LA CIERVA");
        lista_lugar.add("JUAN DE LA CIERVA");
        lista_lugar.add("PERALES");
        lista_lugar.add("JUAN DE LA CIERVA");
        lista_lugar.add("GINER 1");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("GINER 2");
        lista_lugar.add("JUAN DE LA CIERVA");
        lista_lugar.add("M-4");
        lista_lugar.add("GINER 1");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("JUAN DE LA CIERVA");

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Partidos");
        query.whereEqualTo("Temporada","2016-17");
        query.orderByAscending("Jornada");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for (int i = 0; i < scoreList.size(); i++) {
                        String resultadoo = scoreList.get(i).getNumber("GF") + "-" + scoreList.get(i).getNumber("GC");
                        int coloor = scoreList.get(i).getInt("Intindice");
                        lista_result.setElementAt(resultadoo, i);
                        lista_estadios.setElementAt(coloor,i);
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("jornada"+(i+1), resultadoo);
                        editor.putInt("color"+(i+1),coloor);
                        editor.apply();
                        Log.d("score", "Jornada numero: " + scoreList.get(i).getNumber("Jornada") + "resultado: " + scoreList.get(i).getNumber("GF") + "-" + scoreList.get(i).getNumber("GC"));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(lista_maps.get(position));
                    startActivity(intent);
            }
        });
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
