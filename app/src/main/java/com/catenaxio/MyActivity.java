package com.catenaxio;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.catenaxio.activities.CalendarioActivity2;
import com.catenaxio.activities.EstadisticasActivity2;
import com.catenaxio.utils.DownloadFile;
import com.catenaxio.utils.PDFDownloader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;


public class MyActivity extends Activity implements View.OnClickListener{

    Button botonCalendario,botonEstadistica,botonX,botonConvocatoria;

    int id=1;

    NotificationManager mNotifyManager = null;
    NotificationCompat.Builder mBuilder = null;
    Intent intent = null;
    PendingIntent pIntent = null;
    String urlPDF=null;

    //firebase
    private DatabaseReference mDatabase;
    boolean ready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my);
            ready=false;
            botonCalendario = (Button) findViewById(R.id.botonCalendario);
            botonConvocatoria = (Button) findViewById(R.id.BotonConvocatoria);
            botonEstadistica = (Button) findViewById(R.id.botonEstadisticas);
            botonX = (Button) findViewById(R.id.botonX);
            botonCalendario.setOnClickListener(this);
            botonConvocatoria.setOnClickListener(this);
            botonConvocatoria.setOnClickListener(this);
            botonEstadistica.setOnClickListener(this);
            botonX.setOnClickListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString(getString(R.string.pref_usuarios_key),"-1").equals("-1")){
            Intent lanzarAct = new Intent(this,ListProfiles.class);
            startActivity(lanzarAct);
        }
        cargarFirebase();
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
            Intent lanzarActividad=new Intent(this,CalendarioActivity2.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonConvocatoria){
            Intent lanzarActividad=new Intent(this,ConvocatoriaActivity.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonEstadistica){
            Intent lanzarActividad=new Intent(this,EstadisticasActivity2.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonX){
            if(ready){
                mNotifyManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(this);

                mBuilder.setContentTitle("Descarga de Clasificación")
                        .setContentText("Descarga en progreso")
                        .setSmallIcon(R.drawable.ic_file_download_black_24dp);

                String fileName= "clasificacion.pdf";
                new DownloadFile(id, mNotifyManager, mBuilder, intent, pIntent, this).execute(urlPDF, fileName);
                Intent lanzarActividad=new Intent(getApplicationContext(),ClasificacionActivity.class);
                startActivity(lanzarActividad);
            }else{
                Intent lanzarActividad=new Intent(getApplicationContext(),ClasificacionActivity.class);
                startActivity(lanzarActividad);
            }
        }
    }

    private void cargarFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clasificacion");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                urlPDF = dataSnapshot.getValue().toString();
                ready=true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               ready=false;
            }
        });
    }
}