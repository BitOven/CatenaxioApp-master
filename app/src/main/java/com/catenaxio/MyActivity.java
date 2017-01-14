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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my);
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
            Intent lanzarActividad=new Intent(this,CalendarioActivity.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonConvocatoria){
            Intent lanzarActividad=new Intent(this,ConvocatoriaActivity.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonEstadistica){
            Intent lanzarActividad=new Intent(this,EstadisticasActivity.class);
            startActivity(lanzarActividad);
        }
        else if(view==botonX){
//             Intent lanzarActividad=new Intent(this,ClasificacionActivity.class);
//             startActivity(lanzarActividad);
            Toast.makeText(getApplicationContext(), "Conectando con el servidor...", Toast.LENGTH_SHORT).show();

            mNotifyManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(this);

            mBuilder.setContentTitle("Descarga de Clasificación")
                    .setContentText("Descarga en progreso")
                    .setSmallIcon(R.drawable.ic_file_download_black_24dp);

//            String fileUrl="http://www.femafusa.com/uploads/archivo_delegacion_resultados_3485.pdf";
            String fileName= "clasificacion.pdf";
            new DownloadFile().execute(urlPDF, fileName);
        }
    }

    private void cargarFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clasificacion");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                urlPDF = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Nuestros servidores estan ocupados ahora", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Download");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            //preparo el intent de la notificacion para abrir el pdf
            intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(pdfFile));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//            intent.setAction(android.content.Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(pdfFile), "Download/*");
            pIntent = PendingIntent.getActivity(getApplicationContext(), id, intent, PendingIntent.FLAG_ONE_SHOT);

            //preparo la notificacion en progreso
            mBuilder.setProgress(0, 0, true)
                    .setContentIntent(pIntent);
            mNotifyManager.notify(id, mBuilder.build());

            PDFDownloader.downloadFile(fileUrl, pdfFile);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mBuilder.setContentText("Descarga completada")
                    // Removes the progress bar
                    .setProgress(0,0,false)
                    .setAutoCancel(true);
            mNotifyManager.notify(id, mBuilder.build());

            Intent lanzarActividad=new Intent(getApplicationContext(),ClasificacionActivity.class);
            startActivity(lanzarActividad);
        }
    }
}