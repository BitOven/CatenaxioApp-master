package com.catenaxio.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.catenaxio.R;
import com.catenaxio.SettingsActivity;
import com.catenaxio.beans.Clasificacion;
import com.catenaxio.interfaces.daos.ClasificacionDAOInterfaz;
import com.catenaxio.managers.ConnectionManager;
import com.catenaxio.utils.MiParseador;

/**
 * Created by Antonio on 03/03/2017.
 */

public class ClasificacionActivity2 extends Activity {

    private Clasificacion clasificacion;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        imageView=(ImageView)findViewById(R.id.imagenClasificacion);
        clasificacion= new Clasificacion();
        clasificacion.setTemporada(MiParseador.parsearTemporadaAYear(getApplicationContext()));
        bitmap = ConnectionManager.getClasificacionDAO_SQLite(this).getClasificacion();
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }else{
            Log.d(this.getLocalClassName(),"no ha recibido un bitmap valido");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionManager.getClasificacionDAOFirebase(imageView, clasificacion, getApplicationContext()).downloadClasificacion();//actualiza la imageView desde firebase, y guarda en sql
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clasificacion, menu);
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
}
