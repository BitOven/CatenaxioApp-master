package com.catenaxio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.catenaxio.R;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;
import android.app.Activity;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.widget.Button;

public class ResultadosEmpatados extends Activity implements View.OnClickListener{
    private WebView webView;
    int abel, jesus, cano, hugo, jordan,juanito,javi,meri;
    private DownloadFilesTask descarga;

    private Button botonGrafica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_empatados);

        abel = 0;
        jesus = 0;
        cano = 0;
        hugo = 0;
        jordan = 0;
        juanito=0;
        javi=0;
        meri=0;
        //webView = (WebView)findViewById(R.id.webViewEmpatados);
        botonGrafica=(Button)findViewById(R.id.graficaBoton);
        botonGrafica.setOnClickListener(this);
        //hacemos la descarga
        descarga=new DownloadFilesTask();
        descarga.execute("http://hidandroid.hol.es/catenaxio/obtener_resultados.php");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resultados_empatados, menu);
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

    @Override
    public void onClick(View view) {
        descarga=new DownloadFilesTask();
        descarga.execute("http://hidandroid.hol.es/catenaxio/obtener_resultados.php");
    }

    public class WebAppInterface {



        @JavascriptInterface
        public int getAbel() {
            return abel;
        }

        @JavascriptInterface
        public int getJesus() {
            return jesus;
        }

        @JavascriptInterface
        public int getJordan() {
            return jordan;
        }

        @JavascriptInterface
        public int getCano() {
            return cano;
        }

        @JavascriptInterface
        public int getHugo() {
            return hugo;
        }

        @JavascriptInterface
        public int getJavi() {
            return javi;
        }
        @JavascriptInterface
        public int getJuanito() {
            return juanito;
        }
        @JavascriptInterface
        public int getMeri() {
            return meri;
        }
    }


    //clase asyntask de recibir
    class DownloadFilesTask extends AsyncTask<String,Integer,Integer> {

        private ProgressDialog progreso;
        @Override
        protected void onPreExecute(){
            Log.d("background", "inicio ");

            progreso=new ProgressDialog(ResultadosEmpatados.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("¿Como se llamaba el hotel y las tres discotecas que fuiste en mallorca?");
            progreso.setCancelable(false);
            progreso.show();


        }




        @Override
        protected Integer doInBackground(String... urls) {
            String urlString=urls[0];

            Log.d("background","mi url: "+urlString);
            for(int i=0;i<3;i++){
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
                    if (json.getJSONArray("datos").getJSONObject(i).getString("GPE").equalsIgnoreCase("E")){
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Abel").equalsIgnoreCase("1")){
                            abel++;
                        }
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Jesus").equalsIgnoreCase("1")){
                            jesus++;
                        }
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Cano").equalsIgnoreCase("1")){
                            cano++;
                        }

                        if(json.getJSONArray("datos").getJSONObject(i).getString("Hugo").equalsIgnoreCase("1")){
                            hugo++;
                        }
                        /*if(json.getJSONArray("datos").getJSONObject(i).getString("Javi").equalsIgnoreCase("1")){
                            javi++;
                        }*/
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Jordan").equalsIgnoreCase("1")){
                            jordan++;
                        }
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Juanito").equalsIgnoreCase("1")){
                            juanito++;
                        }
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Meri").equalsIgnoreCase("1")){
                            meri++;
                        }
                    }
                    else if (json.getJSONArray("datos").getJSONObject(i).getString("GPE").equalsIgnoreCase("X")){
                        abel = 5;
                        jesus = 2;
                        cano = 1;
                        hugo = 4;
                        jordan = 5;
                        juanito=2;
                        javi=4;
                        meri=2;
                    }


                }
                Log.d("background","resultado abel: "+abel);


                /*webView.addJavascriptInterface(new WebAppInterface(), "Android");

                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/partidos_empatados.html");*/


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

               /* String url="http://hidandroid.hol.es/catenaxio/chart_empatados.html?abel="+Integer.toString(abel)+
                        "&jesus="+Integer.toString(jesus)+"&cano="+Integer.toString(cano)+"&hugo="+Integer.toString(hugo)+
                        "&javi="+Integer.toString(javi)+"&jordan="+Integer.toString(jordan)+"&juanito="+Integer.toString(juanito)
                        +"&meri="+Integer.toString(meri);*/
                String url="http://hidandroid.hol.es/catenaxio/chart_empatados.html?abel="+Integer.toString(abel)+
                        "&jesus="+Integer.toString(jesus)+"&cano="+Integer.toString(cano)+"&hugo="+Integer.toString(hugo)+
                        "&jordan="+Integer.toString(jordan)+"&juanito="+Integer.toString(juanito)
                        +"&meri="+Integer.toString(meri);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
            else{
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "¿Cuantos días bajaste a desayunar?", Toast.LENGTH_LONG).show();
            }

        }
    }
}
