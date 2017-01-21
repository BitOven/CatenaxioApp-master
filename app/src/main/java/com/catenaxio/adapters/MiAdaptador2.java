package com.catenaxio.adapters;

/**
 * Created by Antonio on 21/01/2017.
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

import com.catenaxio.R;
import com.catenaxio.beans.Jornadas;
import com.catenaxio.utils.Constantes;

public class MiAdaptador2 extends BaseAdapter {

    private final Activity actividad;
    private Jornadas jornadas;

    public MiAdaptador2(Activity actividad, Jornadas jornadas) {
        super();
        this.actividad = actividad;
        this.jornadas = jornadas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View calendarioView;
        if(convertView==null){
            calendarioView=View.inflate(actividad, R.layout.cellcalendario,null);
        }else{
            calendarioView=convertView;
        }
        //lista jornada
        //TextView textViewJornada =(TextView)view.findViewById(R.id.labelJornada);
        //textViewJornada.setText(lista_jornada.elementAt(position));

        //lista fecha
        TextView textViewFecha =(TextView)calendarioView.findViewById(R.id.labelFecha);
        textViewFecha.setText(jornadas.getJornadas(position).getFecha());

        //lista hora
        TextView textViewHora=(TextView)calendarioView.findViewById(R.id.labelHora);
        textViewHora.setText(jornadas.getJornadas(position).getHora());

        //lista rival
        TextView textViewRival=(TextView)calendarioView.findViewById(R.id.labelRival);
        textViewRival.setText(jornadas.getJornadas(position).getRival());

        //lista lugar
        TextView textViewLugar=(TextView)calendarioView.findViewById(R.id.labelEstadio);
        textViewLugar.setText(jornadas.getJornadas(position).getLugar());

        TextView textViewJornada =(TextView)calendarioView.findViewById(R.id.labelResultado);
        textViewJornada.setText(jornadas.getJornadas(position).getMarcador());

        //ImageView imageView=(ImageView)view.findViewById(R.id.imagenEstadio);
        Context context = parent.getContext();
        //segun el estadio introduzco la imagen
        char[] buffer = new char [1];
                jornadas.getJornadas(position).getResultado().getChars(0,1,buffer,0);
        switch (buffer[0]){
            case Constantes.PENDIENTE:

                if(position%2!=0)calendarioView.setBackgroundColor(context.getResources().getColor(R.color.darkblue));
                else calendarioView.setBackgroundColor(context.getResources().getColor(R.color.blueLigth));
                break;

            case Constantes.GANADO:

                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.green));
                break;

            case Constantes.EMPATADO:

                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.orange));
                break;

            case Constantes.PERDIDO:

                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.red));
                break;

            case Constantes.DESCANSO:

                calendarioView.setBackgroundColor(context.getResources().getColor(R.color.black));
                break;

            case Constantes.APLAZADO:

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
        return jornadas.size();
    }
    @Override
    public Object getItem(int position) {
        return jornadas.getJornadas(position);
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
