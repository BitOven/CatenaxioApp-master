package com.catenaxio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import com.catenaxio.R;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Vector;

public class ConvocatoriaActivity extends Activity implements View.OnClickListener{

    private ListView listaConvocatoria;
    private Button botonConvocatoria;
    private RadioGroup eleccionConvocatoria;
    private Spinner eleccionJugador;
    private Button refresh;
    private TextView textoAsistConf;

    public static final String PREFS_NAME = "Preferencias";
    public String jugadorString="";
    public int resultado_enviar=0;

    private Vector<Integer> lista_bajas;
    private Vector<String> lista_fechasAct;
    //private DownloadFilesTask hiloDescarga;
    //private UpLoadFilesTask hiloSubida;
    //public Vector<Integer> lista_bajas_actualizado;

    public MiAdaptadorConvocatoria adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocatoria);

        //uno mi interface
        textoAsistConf=(TextView) findViewById(R.id.textAsistencias);
        listaConvocatoria=(ListView)findViewById(R.id.listViewConvocatoria);
        botonConvocatoria=(Button)findViewById(R.id.botonEnviarConvocatoria);
        botonConvocatoria.setOnClickListener(this);
        eleccionConvocatoria=(RadioGroup)findViewById(R.id.radioEleccion);
        eleccionJugador=(Spinner)findViewById(R.id.spinnerJugador);
        refresh=(Button)findViewById(R.id.botonRefresh);
        refresh.setOnClickListener(this);

        //pongo jugador por defecto
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int jugador=sharedPref.getInt("jugadorNombre", 1);

        textoAsistConf.setText(sharedPref.getString("Asistencias","Asistencias Confirmadas: 0"));

        Log.d("mostrar", "jugador antes: " + jugador);
        eleccionJugador.setSelection(jugador);
        jugadorString=eleccionJugador.getSelectedItem().toString();
        Log.d("mostrar","jugador seleccionado:"+jugadorString);

        //ver las preferencias de jugadores anterior a la carga (para cuando no tenga internet que sea el usuario cuando decida conectar)

        lista_bajas=new Vector<Integer>();
        lista_bajas.add(sharedPref.getInt("Juan", 2));
        lista_bajas.add(sharedPref.getInt("Juanma", 2));
        lista_bajas.add(sharedPref.getInt("Hugo", 2));
        lista_bajas.add(sharedPref.getInt("Meri", 2));
        lista_bajas.add(sharedPref.getInt("Cano", 2));
        lista_bajas.add(sharedPref.getInt("Anton", 2));
        lista_bajas.add(sharedPref.getInt("Jordan", 2));
        lista_bajas.add(sharedPref.getInt("AbelD", 2));
        lista_bajas.add(sharedPref.getInt("AbelG", 1));
        //lista_bajas_actualizado=new Vector<Integer>();
        lista_fechasAct=new Vector<String>();
        for(int i=0; i<9;i++){
            lista_fechasAct.add(sharedPref.getString("FechaAct"+i,"Actualizado:\n01/01/2015"));
        }

        adapter=new MiAdaptadorConvocatoria(this,lista_bajas, lista_fechasAct);
        listaConvocatoria.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.convocatoria, menu);
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

    public void enviar(View vista){

    }

    @Override
    public void onClick(View view) {
        if(view==botonConvocatoria){
            Log.d("mostrar","muestro enviar");

            if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_voy){
                resultado_enviar=0;
            }
            else if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_novoy){
                resultado_enviar=1;
            }
            else if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_duda){
                resultado_enviar=2;
            }
            Log.d("mostrar","pulso" + resultado_enviar);

            Log.d("mostrar","jugador: "+eleccionJugador.getSelectedItemPosition());
            /*
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("jugadorNombre", eleccionJugador.getSelectedItemPosition());
            editor.commit();
            */
            jugadorString=eleccionJugador.getSelectedItem().toString();
            Log.d("mostrar", "jugador seleccionado:" + jugadorString);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Jugadores");
            query.whereEqualTo("Nombre", jugadorString);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> playerChosen, ParseException e) {
                    if (e == null) {
                        Log.d("score", "Retrieved " + playerChosen.size() + " scores");
                        playerChosen.get(0).put("Convocatoria", resultado_enviar);
                        playerChosen.get(0).saveInBackground();
                        updateBajas();
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
            //hiloSubida=new UpLoadFilesTask();
            //hiloSubida.execute("http://hidandroid.hol.es/catenaxio/modificar_convocatoria.php");
        }
        else if(view==refresh){
            Log.d("mostrar", "muestro refresh");
            updateBajas();
            //hiloDescarga=new DownloadFilesTask();
            //hiloDescarga.execute("http://hidandroid.hol.es/catenaxio/ejemploJSON.php");
            //new DownloadFilesTask().execute("www.google");
        }
    }

    private void updateBajas(){
        Toast.makeText(this,"Actualizando...",Toast.LENGTH_SHORT).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Jugadores");
        //query.whereEqualTo("Nombre", jugadorString);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> playerList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + playerList.size() + " scores");
                    int numAsistConf=0;
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();

                    for(int i=0; i<9;i++){
                        lista_bajas.setElementAt(playerList.get(i).getInt("Convocatoria"),i);
                        lista_fechasAct.setElementAt("Actualizado:\n"+ParseaFechas.parsearDateToString(playerList.get(i).getUpdatedAt()),i);
                        if(lista_bajas.get(i)==0){
                            numAsistConf++;
                        }
                        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        //SharedPreferences.Editor editor = settings.edit();
                        editor.putInt(playerList.get(i).getString("Nombre"), lista_bajas.get(i));
                        editor.putString("FechaAct"+i, lista_fechasAct.get(i));
                        editor.apply();
                    }
                    editor.putString("Asistencias","Asistencias Confirmadas: "+numAsistConf);
                    editor.apply();

                    textoAsistConf.setText("Asistencias Confirmadas: " + numAsistConf);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(),"Róbale Wifi al vecino",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //clase asyntask de recibir
    /*
    class DownloadFilesTask extends AsyncTask<String,Integer,Integer> {

        private ProgressDialog progreso;
        @Override
        protected void onPreExecute(){
            Log.d("background","inicio ");

            progreso=new ProgressDialog(ConvocatoriaActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Contactando con la federacion, updating");
            progreso.setCancelable(false);
            progreso.show();


        }

        @Override
        protected Integer doInBackground(String... urls) {
            String urlString=urls[0];

            Log.d("background","mi url: "+urlString);
            for(int i=0;i<2;i++){
                Log.d("background","cuenta:"+i);
                SystemClock.sleep(1000);
            }


            InputStream is = null;
            String result = "";
            JSONObject json = null;
            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httppost = new HttpGet(urlString);
                //aadir url al post si quiero enviar la fecha
                //HttpPost httppost = new HttpPost(serverUrl);
                //List<NameValuePair> params = new ArrayList<NameValuePair>();
                //  params.add(new BasicNameValuePair("iddevice", regId));
                //httppost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            }
            catch(Exception e){
                Log.d("background","error "+e);
                return 1;
            }


            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result=sb.toString();
            } catch(Exception e){ return 1;}

            try{
                json = new JSONObject(result);
            }catch(JSONException e){return 1;}

            try {
                Log.d("background","resultado: "+json.getJSONArray("datos"));

                if(lista_bajas_actualizado.capacity()!=0){
                    lista_bajas_actualizado.removeAllElements();
                }
                for(int i=0;i<=8;i++){

                    String nombre=json.getJSONArray("datos").getJSONObject(i).getString("jugador");
                    String resultado=json.getJSONArray("datos").getJSONObject(i).getString("resultado");
                    lista_bajas_actualizado.add(Integer.parseInt(resultado));
                    Log.d("background","Filtrando nombre "+nombre+" y resultado "+resultado);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt(nombre, Integer.parseInt(resultado));
                    editor.commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.d("backgorund","terminado");
            if(result==0){
                progreso.dismiss();
                int b=0;
                for(int a : lista_bajas_actualizado){
                    Log.d("resultados","tengo: "+a);
                    lista_bajas.setElementAt(a,b);
                    b++;
                }
                adapter.notifyDataSetChanged();
            }
            else{
                progreso.dismiss();
                Toast.makeText(getApplicationContext(),"Esta la federacion como para atenderte",Toast.LENGTH_LONG).show();
            }

        }
    }

    //clase asyntask de recibir
    class UpLoadFilesTask extends AsyncTask<String,Integer,Integer> {

        private ProgressDialog progreso;
        @Override
        protected void onPreExecute(){
            Log.d("background","inicio ");

            progreso=new ProgressDialog(ConvocatoriaActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Enviando informacion a la federacion, suerte");
            progreso.setCancelable(false);
            progreso.show();


        }


        @Override
        protected Integer doInBackground(String... urls) {
            String urlString=urls[0];

            Log.d("background","mi url: "+urlString);
            for(int i=0;i<2;i++){
                Log.d("background","cuenta:"+i);
                SystemClock.sleep(1000);
            }

            String serverUrl =  urlString;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("jugador", jugadorString));
            //params.add(new BasicNameValuePair("nombre", UtilidadesGCM.SENDER_ID));
            params.add(new BasicNameValuePair("resultado", Integer.toString(resultado_enviar)));

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(serverUrl);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpclient.execute(httppost);
                Log.d("subida","exito");
                return 0;
            } catch (IOException e) {
                Log.d("subida" ,"error "+e);
                return 1;
            }





        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.d("backgorund","terminado");
            if(result==0){
                progreso.dismiss();
                hiloDescarga=new DownloadFilesTask();
                hiloDescarga.execute("http://hidandroid.hol.es/catenaxio/ejemploJSON.php");
            }
            else{
                progreso.dismiss();
                Toast.makeText(getApplicationContext(),"La federacion esta cerrada, mira a ver si tienes internet anda",Toast.LENGTH_LONG).show();
            }

        }
    }*/
}
