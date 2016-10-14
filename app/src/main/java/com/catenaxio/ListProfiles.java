package com.catenaxio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

/**
 * Created by Antonio on 14/10/2016.
 */
public class ListProfiles extends Activity {

//    SharedPreferences.Editor editor = prefs.edit();
//editor.putInt("prefsInt",valorPerfilElegido);
//        editor.apply();
    private Vector<String> lista_perfiles;
    private Context context =this;
    private ListView listaPerfiles;
    private MiAdaptadorPerfiles adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profiles);

        lista_perfiles = new Vector<String>();//si se añaden o quitan, modificar tambien string_array_usuarios para menu Settings
        lista_perfiles.add("Juan");
        lista_perfiles.add("Juanma");
        lista_perfiles.add("Hugo");
        lista_perfiles.add("Meri");
        lista_perfiles.add("Cano");
        lista_perfiles.add("Anton");
        lista_perfiles.add("Jordan");
        lista_perfiles.add("AbelG");
        lista_perfiles.add("Dorado");
        lista_perfiles.add("Ex-jugador");
        lista_perfiles.add("Simpatizante");

        adapter= new MiAdaptadorPerfiles(this,lista_perfiles);
        listaPerfiles = (ListView)findViewById(R.id.listViewUsuarios);
        listaPerfiles.setAdapter(adapter);
        listaPerfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = prefs.edit();
                if(position<9){ //compruebo que es jugador y no invitado
                    editor.putString(getString(R.string.pref_usuarios_key),String.valueOf(position));
                    editor.apply();
                }else{//asigno el 20 para invitados
                    int defaultNum=20;
                    editor.putString(getString(R.string.pref_usuarios_key),String.valueOf(defaultNum));
                    editor.apply();
                }

                Intent volverMain = new Intent(context,MyActivity.class);
                volverMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(volverMain);
            }
        });
    }
}

