package com.catenaxio;
import android.app.Activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.catenaxio.utils.Constantes;
import com.catenaxio.utils.MiParseador;
import com.catenaxio.utils.PDFDownloader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class ClasificacionActivity extends Activity {
    private ImageView imagen;
    private Bitmap bitmap;

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
//TODO Agregar guardado de imagen en sql (y poder cargarla en offline)
//TODO Añadir sistema que permita saber si es necesario descargar nueva imagen (para evitar descargas innecesarias)
    private void cargarFireBase(){
        String clasifElegida = MiParseador.parsearTemporadaAYear(this);
        //selecciono la rama de firebase adecuada
        StorageReference storageRef = storage.getReference().child(Constantes.FRBS_CLASIFICACION).child(Constantes.FRBS_CLASIFICACION+clasifElegida+Constantes.PNG_EXTENSION);
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
                Toast.makeText(getApplicationContext(), "¡Tenemos el servidor colapsado!", Toast.LENGTH_LONG).show();
            }
        });
    }
    //TODO Añadir botón flotante para descargar pdf sólo si se desea
}
