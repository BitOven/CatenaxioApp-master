package com.catenaxio;

/**
 * Created by hugoizquierdo on 8/30/14.
 */
import java.util.Vector;
import android.content.*;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MiAdaptador extends BaseAdapter {
    private final Activity actividad;
    private final Vector<String> lista_jornada;
    private final Vector<String> lista_fecha;
    private final Vector<String> lista_hora;
    private final Vector<String> lista_rival;
    private final Vector<String> lista_lugar;
    private final Vector<Integer> lista_estadio;
    private Vector<String> lista_resultados;


    public MiAdaptador(Activity actividad, Vector<String> jor, Vector<String> fecha, Vector<String> hora, Vector<String> rival,
                       Vector<Integer> estadios, Vector<String> lugares, Vector<String>result) {
        super();
        this.actividad = actividad;
        this.lista_jornada = jor;
        this.lista_fecha=fecha;
        this.lista_hora=hora;
        this.lista_rival=rival;
        this.lista_estadio=estadios;
        this.lista_lugar=lugares;
        this.lista_resultados=result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View calendarioView;
        if(convertView==null){
            calendarioView=View.inflate(actividad,R.layout.cellcalendario,null);
        }else{
            calendarioView=convertView;
        }
        //lista jornada
        //TextView textViewJornada =(TextView)view.findViewById(R.id.labelJornada);
        //textViewJornada.setText(lista_jornada.elementAt(position));

        //lista fecha
        TextView textViewFecha =(TextView)calendarioView.findViewById(R.id.labelFecha);
        textViewFecha.setText(lista_fecha.get(position));

        //lista hora
        TextView textViewHora=(TextView)calendarioView.findViewById(R.id.labelHora);
        textViewHora.setText(lista_hora.get(position));

        //lista rival
        TextView textViewRival=(TextView)calendarioView.findViewById(R.id.labelRival);
        textViewRival.setText(lista_rival.get(position));

        //lista lugar
        TextView textViewLugar=(TextView)calendarioView.findViewById(R.id.labelEstadio);
        textViewLugar.setText(lista_lugar.get(position));

        TextView textViewJornada =(TextView)calendarioView.findViewById(R.id.labelResultado);
        textViewJornada.setText(lista_resultados.get(position));

        //ImageView imageView=(ImageView)view.findViewById(R.id.imagenEstadio);
        Context context = parent.getContext();
        //segun el estadio introduzco la imagen
        switch (lista_estadio.get(position)){
            case 0:

                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.darkblue));
                break;
            case 1:

                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.blueLigth));
                break;

            case 2:
//ganados
                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.green));
                break;

            case 3:
//empates
                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.orange));
                break;

            case 4:
//derrotas
                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.red));
                break;

            case 5:
//descanso
                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.black));
                break;

            case 6:
//aplazado
                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.purple));
                break;


            default:
                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.blueLigth));
                break;
        }
        return calendarioView;
    }
    @Override
    public int getCount() {
        return lista_jornada.size();
    }
    @Override
    public Object getItem(int position) {
        return lista_jornada.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
