package com.catenaxio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    public static final String PREFS_NAME = "Preferencias";

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

        lista_fecha.add("03/10/15");
        lista_fecha.add("18/10/15");
        lista_fecha.add("24/10/15");
        lista_fecha.add("07/11/15");
        lista_fecha.add("15/11/15");
        lista_fecha.add("21/11/15");
        lista_fecha.add("29/11/15");
        lista_fecha.add("12/12/15");
        lista_fecha.add("20/12/15");
        lista_fecha.add("09/01/16");
        lista_fecha.add("17/01/16");
        lista_fecha.add("23/01/16");
        lista_fecha.add("31/01/16");
        lista_fecha.add("06/02/16");
        lista_fecha.add("13/02/16");
        lista_fecha.add("21/02/16");
        lista_fecha.add("27/02/16");
        lista_fecha.add("06/03/16");
        lista_fecha.add("12/03/16");
        lista_fecha.add("03/04/16");
        lista_fecha.add("09/04/16");
        lista_fecha.add("17/04/16");

        lista_hora.add("18:00");
        lista_hora.add("14:30");
        lista_hora.add("17:00");
        lista_hora.add("20:00");
        lista_hora.add("14:00");
        lista_hora.add("19:00");
        lista_hora.add("09:00");
        lista_hora.add("18:30");
        lista_hora.add("16:00");
        lista_hora.add("17:00");
        lista_hora.add("11:00");
        lista_hora.add("18:00");
        lista_hora.add("15:00");
        lista_hora.add("17:00");
        lista_hora.add("20:00");
        lista_hora.add("14:00");
        lista_hora.add("19:00");
        lista_hora.add("09:00");
        lista_hora.add("18:30");
        lista_hora.add("17:00");
        lista_hora.add("17:00");
        lista_hora.add("11:00");

        lista_rival.add("DIETO UNITED");
        lista_rival.add("MONUMENTS MENS CF");
        lista_rival.add("PASEO B FS");
        lista_rival.add("SET POINT FS");
        lista_rival.add("AD AZULES B");
        lista_rival.add("WHISKY PARK RANGERS");
        lista_rival.add("LA TABERNA DEL TARAO");
        lista_rival.add("RAYO BERCIAL");
        lista_rival.add("NARANJA MECANICA B");
        lista_rival.add("INTER FS");
        lista_rival.add("DREAM TEAM FS");
        lista_rival.add("DIETO UNITED");
        lista_rival.add("MONUMENTS MENS CF");
        lista_rival.add("PASEO B FS");
        lista_rival.add("SET POINT FS");
        lista_rival.add("AD AZULES B");
        lista_rival.add("WHISKY PARK RANGERS");
        lista_rival.add("LA TABERNA DEL TARAO");
        lista_rival.add("RAYO BERCIAL");
        lista_rival.add("NARANJA MECANICA B");
        lista_rival.add("INTER FS");
        lista_rival.add("DREAM TEAM FS");

        for(int i=1; i<23; i++){
            lista_estadios.add(sharedPref.getInt("color"+i, (i%2)));
        }


        lista_lugar.add("JUAN DE LA CIERVA 2");
        lista_lugar.add("GINER DE LOS RIOS 2 ");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("PABELLON PERALES");
        lista_lugar.add("JUAN DE LA CIERVA 2");
        lista_lugar.add("GINER DE LOS RIOS 1");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("GINER DE LOS RIOS 2");
        lista_lugar.add("PABELLON M-4");
        lista_lugar.add("PABELLON M-4");
        lista_lugar.add("GINER DE LOS RIOS 1");
        lista_lugar.add("JUAN DE LA CIERVA 2");
        lista_lugar.add("PABELLON PERALES");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("PABELLON PERALES");
        lista_lugar.add("JUAN DE LA CIERVA 2");
        lista_lugar.add("GINER DE LOS RIOS 1");
        lista_lugar.add("SECTOR III");
        lista_lugar.add("GINER DE LOS RIOS 2");
        lista_lugar.add("JUAN DE LA CIERVA 2");
        lista_lugar.add("PABELLON M-4");
        lista_lugar.add("GINER DE LOS RIOS 1");

        for(int i=1; i<23; i++){
            lista_result.add(sharedPref.getString("jornada"+i, "N/A"));
        }

        adapter= new MiAdaptador(this, lista_jornada, lista_fecha, lista_hora, lista_rival, lista_estadios, lista_lugar, lista_result);
        miLista=(ListView)findViewById(R.id.listView);
        miLista.setAdapter(adapter);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Partidos");
        query.whereEqualTo("Temporada","2015-16");
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
