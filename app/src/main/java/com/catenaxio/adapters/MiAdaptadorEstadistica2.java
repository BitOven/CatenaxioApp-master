package com.catenaxio.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catenaxio.R;
import com.catenaxio.beans.Jugadores;

import java.util.Vector;

/**
 * Created by Antonio on 22/01/2017.
 */

public class MiAdaptadorEstadistica2 extends BaseAdapter {

    private final Context actividad;

    private Jugadores jugadores;

    public MiAdaptadorEstadistica2(Context actividad, Jugadores jugadores) {
        super();
        this.actividad = actividad;
        this.jugadores = jugadores;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        //System.out.println("cargo position"+position);
        View statsView;
        if(convertView==null){
            statsView=View.inflate(actividad, R.layout.cellestadisticas,null);
        }else{
            statsView=convertView;
        }
        //partidos jugados
        TextView textViewJornada =(TextView)statsView.findViewById(R.id.partidosJugados);
        textViewJornada.setText(""+jugadores.getJugadores(position).getPartidosJugados());

        //partidos totales
        TextView textViewFecha =(TextView)statsView.findViewById(R.id.partidoTitular);
        textViewFecha.setText(""+jugadores.getPartidosTotales());

        //goles
        TextView textViewHora=(TextView)statsView.findViewById(R.id.goles);
        textViewHora.setText(""+jugadores.getJugadores(position).getGoles());

        //asistencias
        TextView textViewRival=(TextView)statsView.findViewById(R.id.asistencias);
        textViewRival.setText(""+jugadores.getJugadores(position).getAsistencias());

        //partidos ganados
        TextView textViewPartidoGanados=(TextView)statsView.findViewById(R.id.partidosGanados);
        textViewPartidoGanados.setText(""+jugadores.getJugadores(position).getPartidosGanados());

        //porcentaje goles
        TextView textViewPorcentajeGoles=(TextView)statsView.findViewById(R.id.porcentaje_goles);
        textViewPorcentajeGoles.setText(""+jugadores.getJugadores(position).getPorcentajeGoles());

        //lista jornada FIREBASE
        ImageView imagenJugador=(ImageView)statsView.findViewById(R.id.imagenJugadorEstadistica);
        if(jugadores.getJugadores(position).getImageResource()!=0){
            imagenJugador.setImageResource(jugadores.getJugadores(position).getImageResource());
        }else{
            imagenJugador.setImageBitmap(jugadores.getJugadores(position).getImagen());
        }

        return statsView;
    }

    public int getCount() {
        return jugadores.size();
    }

    public Object getItem(int arg0) {
        return jugadores.getJugadores(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
