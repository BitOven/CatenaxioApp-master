package com.catenaxio;
import android.app.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ClasificacionActivity extends Activity {
    private ImageView imagen;
    private Bitmap bitmap;

    //firebase storage instance
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        imagen=(ImageView)findViewById(R.id.imagenClasificacion);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Cargando...", Toast.LENGTH_SHORT).show();
        cargarFireBase();
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

    private void cargarFireBase(){

        String clasifElegida="gs://catenaxio-dd230.appspot.com/clasificacion/clasificacion.png";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2016-17")){
            clasifElegida="gs://catenaxio-dd230.appspot.com/clasificacion/clasificacion.png";
        }
        if(prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)).equals("2015-16")){
            clasifElegida="gs://catenaxio-dd230.appspot.com/clasificacion/clasificacion15.png";
        }
        //selecciono la rama de firebase adecuada
        StorageReference storageRef = storage.getReferenceFromUrl(clasifElegida);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagen.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Nuestros servidores estan ocupados ahora", Toast.LENGTH_LONG).show();
            }
        });
    }
}
