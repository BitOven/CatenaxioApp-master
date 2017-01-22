package com.catenaxio.activities;

/**
 * Created by Antonio on 21/01/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.catenaxio.R;
import com.catenaxio.SettingsActivity;
import com.catenaxio.adapters.MiAdaptador2;
import com.catenaxio.beans.Jornadas;
import com.catenaxio.daos.JornadasDAOFireBase;
import com.catenaxio.interfaces.daos.JornadasDAOInterfaz;
import com.catenaxio.utils.MiParseador;
import com.catenaxio.utils.Preferencias;
import com.google.firebase.database.DatabaseReference;


public class CalendarioActivity2 extends Activity {

    private ListView miLista;
    private BaseAdapter adapter;

    //firebase
    private DatabaseReference mDatabase;
    private Jornadas jornadas;
    JornadasDAOInterfaz jorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendario);
        jornadas= new Jornadas();
        adapter= new MiAdaptador2(this, jornadas);
        miLista=(ListView)findViewById(R.id.listView);
        miLista.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        jorDAO = new JornadasDAOFireBase(this, jornadas, adapter);
        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!jornadas.getJornadas(position).getUrlCampo().equals(" ")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(jornadas.getJornadas(position).getUrlCampo()));
                    startActivity(intent);
                }
            }
        });
        miLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!jornadas.getJornadas(position).getUrlCampo().equals(" ")) {
                    Intent intent = createShareForecastIntent(jornadas.getJornadas(position).getUrlCampo());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Preferencias.cargarPreferenciasCalendario(this, jornadas);
        adapter.notifyDataSetChanged();
        jorDAO.downloadJornadas(getCalendarioByTemporada());
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

    /**
     * La temporada debe tener el formato "2015-16"
     * El calendario asociado de firebase debe llamarse "Calendario2015"
     * @return el string del calendario correspondiente
     */
    private String getCalendarioByTemporada(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String calendarioElegido=MiParseador.parsearCalendarioElegido(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)));
        return calendarioElegido;
    }

    private Intent createShareForecastIntent(String enlaceGMaps){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, enlaceGMaps);
        return shareIntent;
    }
}
