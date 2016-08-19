package com.catenaxio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.catenaxio.R;
import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.catenaxio.ObjetoResultados;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResultadosActivity extends Activity {

    private ArrayList<ObjetoResultados> lista;
    private DownloadFilesTask hilo;
    private ListView miLista;
    public MiAdaptadorResultados adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        lista=new ArrayList<ObjetoResultados>();
        //descarga
        hilo=new DownloadFilesTask();
        hilo.execute("http://hidandroid.hol.es/catenaxio/obtener_resultados.php");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resultados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //clase asyntask de recibir
    class DownloadFilesTask extends AsyncTask<String,Integer,Integer> {

        private ProgressDialog progreso;
        @Override
        protected void onPreExecute(){
            Log.d("background", "inicio ");

            progreso=new ProgressDialog(ResultadosActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Verde destaca, rojo necesita mejorar y azul progresa adecuadamente");
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
                Log.d("background","el numero de partidos es: "+json.getJSONArray("datos").length());


               /*

                    String nombre=json.getJSONArray("datos").getJSONObject(i).getString("jugador");
                    String partidos=json.getJSONArray("datos").getJSONObject(i).getString("partidos");
                    String titulares=json.getJSONArray("datos").getJSONObject(i).getString("titulares");
                    String goles=json.getJSONArray("datos").getJSONObject(i).getString("goles");
                    String asistencias=json.getJSONArray("datos").getJSONObject(i).getString("asistencias");
                    String partidos_ganados=json.getJSONArray("datos").getJSONObject(i).getString("partidosGanados");
*/

                for(int i=0;i<json.getJSONArray("datos").length();i++){
                    //ver si el partido es ganado
                    String resultado=json.getJSONArray("datos").getJSONObject(i).getString("Resultado");
                    String abel=json.getJSONArray("datos").getJSONObject(i).getString("Abel");
                    String jesus=json.getJSONArray("datos").getJSONObject(i).getString("Jesus");
                    String cano=json.getJSONArray("datos").getJSONObject(i).getString("Cano");
                    String hugo=json.getJSONArray("datos").getJSONObject(i).getString("Hugo");
                    //String javi=json.getJSONArray("datos").getJSONObject(i).getString("Javi");
                    String jordan=json.getJSONArray("datos").getJSONObject(i).getString("Jordan");
                    String juanito=json.getJSONArray("datos").getJSONObject(i).getString("Juanito");
                    String meri=json.getJSONArray("datos").getJSONObject(i).getString("Meri");
                    String indice=json.getJSONArray("datos").getJSONObject(i).getString("GPE");
                    String rival=json.getJSONArray("datos").getJSONObject(i).getString("Rival");

                    //ObjetoResultados miObjeto=new ObjetoResultados(resultado,rival,abel,jesus,cano,hugo,javi,jordan,juanito,meri,indice);
                    ObjetoResultados miObjeto=new ObjetoResultados(resultado,rival,abel,jesus,cano,hugo,jordan,juanito,meri,indice);

                    lista.add(miObjeto);


                }



                /*webView.addJavascriptInterface(new WebAppInterface(), "Android");

                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/partidos_empatados.html");*/


            } catch (JSONException e) {
                e.printStackTrace();
                return 1;
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

                int i=1;
                for(ObjetoResultados x : lista){

                    Log.d("mostrar","valor abel:"+x.getRival());
                }
                miLista=(ListView)findViewById(R.id.listView);
                adapter=new MiAdaptadorResultados(ResultadosActivity.this,lista);
                miLista.setAdapter(adapter);


            }
            else{
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Esta la federacion a tope, intentalo mas tarde o ahora con internet", Toast.LENGTH_LONG).show();
            }

        }
    }
}
