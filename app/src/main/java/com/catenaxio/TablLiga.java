package com.catenaxio;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TablLiga extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitiy_tab_liga);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ClasificacionActivity.class);
        spec = tabHost.newTabSpec("Clasificacion")
                .setIndicator("Clasificacion")
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        intent = new Intent().setClass(this, ResultadosActivity.class);
        spec = tabHost.newTabSpec("Resultados")
                .setIndicator("Resultados")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ResultadosGanados.class);
        spec = tabHost.newTabSpec("Ganados")
                .setIndicator("Ganados")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ResultadosPerdidos.class);
        spec = tabHost.newTabSpec("Perdidos")
                .setIndicator("Perdidos")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ResultadosEmpatados.class);
        spec = tabHost.newTabSpec("Empatados")
                .setIndicator("Empatados")
                .setContent(intent);
        tabHost.addTab(spec);




        //set tab which one you want open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);


    }

}



