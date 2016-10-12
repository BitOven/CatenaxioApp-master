package com.catenaxio;
import android.app.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.os.AsyncTask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.catenaxio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.widget.Button;

public class ClasificacionActivity extends Activity {
    private ImageView imagen;
    private Bitmap bitmap;

    //firebase storage instance
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private DatabaseReference mDatabase;
//    private ProgressDialog pDialog;
//    private LoadImage hiloDescarga;
//    private Button clasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        imagen=(ImageView)findViewById(R.id.imagenClasificacion);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //cargarClasif();
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

//    private void cargarClasif(){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clasificacion").whereMatches("Temporada",prefs.getString(getString(R.string.pref_temporada_key),getString(R.string.pref_temporada_default)));
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> imageList, ParseException e) {
//                if (e == null) {
//                    Log.d("score", "Retrieved " + imageList.size() + " images");
//                    ParseFile applicantResume = (ParseFile) imageList.get(0).get("imagen");
//                    applicantResume.getDataInBackground(new GetDataCallback() {
//                        public void done(byte[] data, ParseException e) {
//                            if (e == null) {
//                                // data has the bytes for the resume
//                                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                                imagen.setImageBitmap(bitmap);
//
//                            } else {
//                                // something went wrong
//                            }
//                        }
//                    });
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//    }

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

//    @Override
//    public void onClick(View view) {
//        if(view==clasificacion){
//
////            hiloDescarga=new LoadImage();
////            hiloDescarga.execute("http://hidandroid.hol.es/catenaxio/clasificacion.png");
//
//        }
//    }

//    private class LoadImage extends AsyncTask<String, String, Bitmap> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(ClasificacionActivity.this);
//            pDialog.setMessage("Cargando la clasificacion, descargando todos los virus de la pagina femafusa");
//            pDialog.show();
//        }
//        protected Bitmap doInBackground(String... args) {
//            for(int i=0;i<3;i++){
//                SystemClock.sleep(1000);
//            }
//
//            try {
//                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//        protected void onPostExecute(Bitmap image) {
//            if(image != null){
//                imagen.setImageBitmap(image);
//                pDialog.dismiss();
//            }else{
//                pDialog.dismiss();
//                Toast.makeText(ClasificacionActivity.this, "Te quedaste sin graduacion y ahora te quedas sin clasificacion", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
