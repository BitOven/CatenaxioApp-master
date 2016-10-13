package com.catenaxio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity implements View.OnClickListener{

    Button botonCalendario,botonEstadistica,botonX,botonConvocatoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        botonCalendario=(Button)findViewById(R.id.botonCalendario);
        botonConvocatoria=(Button)findViewById(R.id.BotonConvocatoria);
        botonEstadistica=(Button)findViewById(R.id.botonEstadisticas);
        botonX=(Button)findViewById(R.id.botonX);
        botonCalendario.setOnClickListener(this);
//        botonConvocatoria.setOnClickListener(this);
        botonConvocatoria.setClickable(false);
        botonEstadistica.setOnClickListener(this);
        botonX.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
        if(view==botonCalendario){
            Intent lanzarActividad=new Intent(this,CalendarioActivity.class);
            startActivity(lanzarActividad);
        }
//        else if(view==botonConvocatoria){
//            Intent lanzarActividad=new Intent(this,ConvocatoriaActivity.class);
//            startActivity(lanzarActividad);
//        }
        else if(view==botonEstadistica){
            Intent lanzarActividad=new Intent(this,EstadisticasActivity.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonX){
             Intent lanzarActividad=new Intent(this,ClasificacionActivity.class);
             startActivity(lanzarActividad);
        }
    }
}
