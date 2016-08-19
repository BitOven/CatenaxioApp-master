package com.catenaxio;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
import android.content.Intent;
import android.net.Uri;
import android.content.UriPermission;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ResultadosGanados extends Activity implements View.OnClickListener{
    private WebView webView;
    int abel, jesus, cano, hugo, jordan,juanito,javi,meri;

    private DownloadFilesTask descarga;

    private Button botonGrafica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_ganados);

        botonGrafica=(Button)findViewById(R.id.graficaBoton);
        botonGrafica.setOnClickListener(this);
        //webView = (WebView)findViewById(R.id.webView);
       /* webView = (WebView) findViewById(R.id.webView);
        WebSettings setting=webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);*/

        abel = 0;
        jesus = 0;
        cano = 0;
        hugo = 0;
        jordan = 0;
        juanito=0;
        javi=0;
        meri=0;
        //hacemos la descarga
        descarga=new DownloadFilesTask();
        descarga.execute("http://hidandroid.hol.es/catenaxio/obtener_resultados.php");


        Log.d("mostrar","creado resultados ganados");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resultados_ganados, menu);
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

            progreso=new ProgressDialog(ResultadosGanados.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage ("¿Te gustaría ir a la ventanilla de la federación sin pasar por la sala de billar?");

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
                    if (json.getJSONArray("datos").getJSONObject(i).getString("GPE").equalsIgnoreCase("G")){
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
                            Log.d("resultados","sumo jordan");
                        }
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Juanito").equalsIgnoreCase("1")){
                            juanito++;
                        }
                        if(json.getJSONArray("datos").getJSONObject(i).getString("Meri").equalsIgnoreCase("1")){
                            meri++;
                        }
                    }
                    /*else if (json.getJSONArray("datos").getJSONObject(i).getString("GPE").equalsIgnoreCase("X")){
                        abel = 5;
                        jesus = 2;
                        cano = 1;
                        hugo = 4;
                        jordan = 5;
                        juanito=2;
                        javi=4;
                        meri=2;
                    }*/


                }
                Log.d("background","resultado abel: "+abel);





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
               /*webView.addJavascriptInterface(new WebAppInterface(), "Android");

                webView.getSettings().setJavaScriptEnabled(true);

                //webView.setWebViewClient(new MyWebViewClient());
                webView.loadUrl("file:///android_asset/chart.html");*/



                /*String url="http://hidandroid.hol.es/catenaxio/prueba_canvas.html?abel="+Integer.toString(abel)+
                        "&jesus="+Integer.toString(jesus)+"&cano="+Integer.toString(cano)+"&hugo="+Integer.toString(hugo)+
                        "&javi="+Integer.toString(javi)+"&jordan="+Integer.toString(jordan)+"&juanito="+Integer.toString(juanito)
                        +"&meri="+Integer.toString(meri);*/
                String url="http://hidandroid.hol.es/catenaxio/prueba_canvas.html?abel="+Integer.toString(abel)+
                        "&jesus="+Integer.toString(jesus)+"&cano="+Integer.toString(cano)+"&hugo="+Integer.toString(hugo)+
                        "&jordan="+Integer.toString(jordan)+"&juanito="+Integer.toString(juanito)
                        +"&meri="+Integer.toString(meri);
                Log.d("url","url: "+url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);




            }
            else{
                progreso.dismiss();
                Toast.makeText(ResultadosGanados.this, "Hazte premium y te decimos como", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.example.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("file:///android_asset/chart.html"));
            startActivity(intent);
            return true;
        }
    }


}
