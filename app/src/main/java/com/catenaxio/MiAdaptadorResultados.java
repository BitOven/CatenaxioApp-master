package com.catenaxio;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;
import com.catenaxio.ObjetoResultados;

/**
 * Created by hugoizquierdo on 9/13/14.
 */

public class MiAdaptadorResultados extends BaseAdapter {
    private ResultadosActivity actividad;


    private ArrayList<ObjetoResultados> lista;
    private LinearLayout celda;




    public MiAdaptadorResultados(ResultadosActivity act, ArrayList<ObjetoResultados> list) {
        super();
        this.actividad=act;
        this.lista=list;

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        //System.out.println("cargo position"+position);
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.celdaresultados, null, true);

        //lista rival
        TextView textViewRival =(TextView)view.findViewById(R.id.rivalPartido1);
        textViewRival.setText(lista.get(position).getRival());

        //lista resultado
        TextView textViewResultado =(TextView)view.findViewById(R.id.resultadoPartido1);
        textViewResultado.setText(lista.get(position).getResultado());

        //lista abel
        TextView textViewAbel =(TextView)view.findViewById(R.id.abelPartido1);
        if(lista.get(position).getAbel().equalsIgnoreCase("1")) //ha ido
            textViewAbel.setText("x");
        else{
            textViewAbel.setText("o");
        }

        //lista jesus
        TextView textViewJesus =(TextView)view.findViewById(R.id.antonPartido1);
        if(lista.get(position).getJesus().equalsIgnoreCase("1")) //ha ido
            textViewJesus.setText("x");
        else{
            textViewJesus.setText("o");
        }

        //lista cano
        TextView textViewCano =(TextView)view.findViewById(R.id.canoPartido1);
        if(lista.get(position).getCano().equalsIgnoreCase("1")) //ha ido
            textViewCano.setText("x");
        else{
            textViewCano.setText("o");
        }

        //lista hugo
        TextView textViewHugo =(TextView)view.findViewById(R.id.hugoPartido1);
        if(lista.get(position).getHugo().equalsIgnoreCase("1")) //ha ido
            textViewHugo.setText("x");
        else{
            textViewHugo.setText("o");
        }

        //lista javi
        /*TextView textViewJavi =(TextView)view.findViewById(R.id.javiPartido1);
        if(lista.get(position).getJavi().equalsIgnoreCase("1")) //ha ido
            textViewJavi.setText("x");
        else{
            textViewJavi.setText("o");
        }*/

        //lista jordan
        TextView textViewJordan =(TextView)view.findViewById(R.id.jordanPartido1);
        if(lista.get(position).getJordan().equalsIgnoreCase("1")) //ha ido
            textViewJordan.setText("x");
        else{
            textViewJordan.setText("o");
        }

        //lista juanito
        TextView textViewJuanito =(TextView)view.findViewById(R.id.juanitoPartido1);
        if(lista.get(position).getJuanito().equalsIgnoreCase("1")) //ha ido
            textViewJuanito.setText("x");
        else{
            textViewJuanito.setText("o");
        }

        //lista meri
        TextView textViewMeri =(TextView)view.findViewById(R.id.meriPartido1);
        if(lista.get(position).getMeri().equalsIgnoreCase("1")) //ha ido
            textViewMeri.setText("x");
        else{
            textViewMeri.setText("o");
        }

        //color de la celda segun el indice
        celda=(LinearLayout)view.findViewById(R.id.celdaResultado);
        Context context = parent.getContext();



        if(lista.get(position).getIndiceResultados().equalsIgnoreCase("G")){
            celda.setBackgroundColor(context.getResources().getColor(R.color.greenganar));
        }
        else if (lista.get(position).getIndiceResultados().equalsIgnoreCase("P")){
            celda.setBackgroundColor(context.getResources().getColor(R.color.redperder));
        }
        else if (lista.get(position).getIndiceResultados().equalsIgnoreCase("E")){
            celda.setBackgroundColor(context.getResources().getColor(R.color.darkdarkblue));
        }
        else{
            celda.setBackgroundColor(context.getResources().getColor(R.color.darkdarkblue));
        }

        return view;
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int arg0) {
        return lista.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }
}
