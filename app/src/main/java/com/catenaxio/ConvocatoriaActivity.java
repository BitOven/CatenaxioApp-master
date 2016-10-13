package com.catenaxio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class ConvocatoriaActivity extends Activity implements View.OnClickListener{

    private ListView listaConvocatoria;
    private Button botonConvocatoria;
    private RadioGroup eleccionConvocatoria;

    private TextView textoAsistConf;

    public static final String PREFS_NAME = "Preferencias";
    public int perfilInt;
    public int resultado_enviar=0;

    private Vector<Integer> lista_bajas;
    private Vector<String> lista_fechasAct;

    public MiAdaptadorConvocatoria adapter;

    //firebase
    private DatabaseReference mDatabase;

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

        //pongo jugador del perfil
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        int jugador=sharedPref.getInt("jugadorNombre", 1);
        perfilInt=sharedPref.getInt("perfilPref",3);

        textoAsistConf.setText(sharedPref.getString("Asistencias","Asistencias Confirmadas: 0"));
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
    protected void onStart() {
        super.onStart();
        updateBajasFB();
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

    @Override
    public void onClick(View view) {
        if(view==botonConvocatoria){

            if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_voy){
                resultado_enviar=0;
            }
            else if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_novoy){
                resultado_enviar=1;
            }
            else if(eleccionConvocatoria.getCheckedRadioButtonId()==R.id.radio_duda){
                resultado_enviar=2;
            }

            //subo dato del jugador si no es perfil de Invitado
            if(perfilInt!=20){
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Convocatoria");
                mDatabase.child(String.valueOf(perfilInt)).child("KeyStatus").setValue(resultado_enviar);
                mDatabase.child(String.valueOf(perfilInt)).child("Fecha").setValue(ParseaFechas.getFechaHoyStringShort());
            }
        }
    }
    private void updateBajasFB(){
        //selecciono la rama de firebase adecuada
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Convocatoria");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                int numAsistConf=0;
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                for(DataSnapshot jugador : dataSnapshot.getChildren()){
                    String nombrePref = jugador.child("Nombre").getValue().toString();
                    lista_bajas.setElementAt(jugador.child("KeyStatus").getValue(Integer.class),i);
                    lista_fechasAct.setElementAt("Actualizado:\n"+jugador.child("Fecha").getValue().toString(),i);
                    if(lista_bajas.get(i)==0)numAsistConf++;

                    editor.putInt(nombrePref,lista_bajas.get(i));
                    editor.putString("FechaAct"+i, lista_fechasAct.get(i));
                    editor.apply();
                    i++;
                }
                editor.putString("Asistencias","Asistencias Confirmadas: "+numAsistConf);
                editor.apply();
                textoAsistConf.setText("Asistencias Confirmadas: " + numAsistConf);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Róbale Wifi al vecino",Toast.LENGTH_LONG).show();
            }
        });
    }
}
