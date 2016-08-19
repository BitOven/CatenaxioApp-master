package com.catenaxio;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class QuesitoActivity extends Activity implements View.OnClickListener {

    private WebView webView;
    private Button botonCargar;

    private int abelG, abelD, jordan, anton,cano,meri,hugo,juanma,juan,invitado;
    private String titulo;
    private int contadorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesito);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        botonCargar = (Button) findViewById(R.id.botonRefresh2);
        botonCargar.setOnClickListener(this);
        //webView.addJavascriptInterface(new WebAppInterface(), "Android");
        //webView.setWebViewClient(new MyWebViewClient());

        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            abelG=extras.getInt("AbelG");
            abelD=extras.getInt("AbelD");
            Log.d("dale", "Abel lleva " + abelD + " goles");
            jordan=extras.getInt("Jordan");
            anton=extras.getInt("Anton");
            cano=extras.getInt("Cano");
            meri=extras.getInt("Meri");
            hugo=extras.getInt("Hugo");
            juanma=extras.getInt("Juanma");
            juan=extras.getInt("Juan");
            invitado=extras.getInt("Invitado");
            titulo="Goles";
        }
        webView.loadUrl("file:///android_asset/ejemplo32.html");
        //webView.loadUrl("javascript:drawChart('"+abelG+"','"+abelD+"','"+jordan+"','"+anton+"','"+cano+"','"+meri+"','"+hugo+"','"+juanma+"','"+juan+"','"+invitado+"','"+titulo+"')");
        //contadorr=2;
    }

    @Override
    public void onClick(View v) {
        if(v==botonCargar){
            webView.loadUrl("javascript:drawChart('"+abelG+"','"+abelD+"','"+jordan+"','"+anton+"','"+cano+"','"+meri+"','"+hugo+"','"+juanma+"','"+juan+"','"+invitado+"','"+titulo+"')");
            /*
            if(contadorr==-1){
                webView.loadUrl("javascript:drawChart2()");
                contadorr--;
            }
            else if(contadorr==0){
                webView.loadUrl("javascript:drawChart()");
                //webView.loadUrl("javascript:drawChart("+Integer.valueOf(abelG)+","+Integer.valueOf(abelD)+","+Integer.valueOf(jordan)+","+Integer.valueOf(anton)+","+Integer.valueOf(cano)+","+Integer.valueOf(meri)+","+Integer.valueOf(hugo)+","+Integer.valueOf(juanma)+","+Integer.valueOf(juan)+","+Integer.valueOf(invitado)+","+String.valueOf(titulo)+")");
            }
            else if(contadorr==1){
                webView.loadUrl("javascript:drawChart(abelG,abelD,jordan,anton,cano,meri,hugo,juanma,juan,invitado,titulo)");
                //webView.loadUrl("javascript:drawChart("+String.valueOf(abelG)+","+String.valueOf(abelD)+","+String.valueOf(jordan)+","+String.valueOf(anton)+","+String.valueOf(cano)+","+String.valueOf(meri)+","+String.valueOf(hugo)+","+String.valueOf(juanma)+","+String.valueOf(juan)+","+String.valueOf(invitado)+","+String.valueOf(titulo)+")");
            }
            else if(contadorr==2){//funciona !!!!!!!!!!!!!!!
                webView.loadUrl("javascript:drawChart('"+abelG+"','"+abelD+"','"+jordan+"','"+anton + "','" + cano + "','" + meri + "','" + hugo + "','" + juanma + "','" + juan + "','" + invitado + "','" + titulo + "')");
            } else if (contadorr ==3){
                webView.loadUrl("javascript:drawChart(\""+abelG+","+abelD+","+jordan+","+anton+","+cano+","+meri+","+hugo+","+juanma+","+juan+","+invitado+","+titulo+"\")");
            }
            else if(contadorr==4){
                webView.loadUrl("javascript:drawChart(\""+Integer.valueOf(abelG)+","+Integer.valueOf(abelD)+","+Integer.valueOf(jordan)+","+Integer.valueOf(anton)+","+Integer.valueOf(cano)+","+Integer.valueOf(meri)+","+Integer.valueOf(hugo)+","+Integer.valueOf(juanma)+","+Integer.valueOf(juan)+","+Integer.valueOf(invitado)+","+String.valueOf(titulo)+"\")");
            }
            else if(contadorr==5){
                webView.loadUrl("javascript:drawChart(\""+String.valueOf(abelG)+","+String.valueOf(abelD)+","+String.valueOf(jordan)+","+String.valueOf(anton)+","+String.valueOf(cano)+","+String.valueOf(meri)+","+String.valueOf(hugo)+","+String.valueOf(juanma)+","+String.valueOf(juan)+","+String.valueOf(invitado)+","+String.valueOf(titulo)+"\")");
            }
            else if(contadorr==6){
                Toast.makeText(this,"Disponible proximamente", Toast.LENGTH_SHORT).show();
            }
            contadorr++;
            */
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quesito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
