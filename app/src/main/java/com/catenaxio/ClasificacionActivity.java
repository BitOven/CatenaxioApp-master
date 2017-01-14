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

    int id=1;

    NotificationManager mNotifyManager = null;
    NotificationCompat.Builder mBuilder = null;
    Intent intent = null;
    PendingIntent pIntent = null;
//    NotificationManager mNotifyManager =
//            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

    //firebase storage instance
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        imagen=(ImageView)findViewById(R.id.imagenClasificacion);

        mNotifyManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("Descarga de Clasificación")
                .setContentText("Descarga en progreso")
                .setSmallIcon(R.drawable.ic_file_download_black_24dp);
        //boton flotante y descargar clasificacion con él:

        String fileUrl="http://www.femafusa.com/uploads/archivo_delegacion_resultados_3485.pdf";
        String fileName= "clasificacion.pdf";
        new DownloadFile().execute(fileUrl, fileName);
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
            clasifElegida="gs://catenaxio-dd230.appspot.com/clasificacion/clasificacion.PNG";
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
            intent = new Intent(Intent.ACTION_VIEW,Uri.fromFile(pdfFile));
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
//            Toast.makeText(getApplicationContext(), "PDF de clasificación descargado en la carpeta Download", Toast.LENGTH_LONG).show();

            mBuilder.setContentText("Descarga completada")
                    // Removes the progress bar
                    .setProgress(0,0,false)
            .setAutoCancel(true);
            mNotifyManager.notify(id, mBuilder.build());
        }
    }

//    private void cargarUrlPdfFederacion(){
//
//    }
}
