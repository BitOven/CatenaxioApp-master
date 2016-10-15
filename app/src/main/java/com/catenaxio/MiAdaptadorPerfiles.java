package com.catenaxio;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by Antonio on 14/10/2016.
 */
public class MiAdaptadorPerfiles extends BaseAdapter {

    private Activity actividad;
    private Vector<String> lista_perfiles;

    public MiAdaptadorPerfiles(Activity actividad, Vector<String> lista_nombres){
        super();
        this.actividad=actividad;
        lista_perfiles=lista_nombres;
    }

    @Override
    public int getCount() {
        return lista_perfiles.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_perfiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View perfilesView;
        if(convertView==null){
            perfilesView=View.inflate(actividad,R.layout.cellusuarios,null);
        }else{
            perfilesView=convertView;
        }

        TextView perfilesTextView = (TextView) perfilesView.findViewById(R.id.perfilUsuario);
        perfilesTextView.setText(lista_perfiles.get(position));

        ImageView imagenJugador=(ImageView)perfilesView.findViewById(R.id.imagenJugadorUsuarios);

        if(position==0){ //juanito
            imagenJugador.setImageResource(R.drawable.juanito);
        }
        else if(position==1){ //juanma
            imagenJugador.setImageResource(R.drawable.juanma);
        }
        else if(position==2){ //hugo4
            imagenJugador.setImageResource(R.drawable.hugo);
        }
        else if(position==3){ //meri
            imagenJugador.setImageResource(R.drawable.meri);
        }
        else if(position==8){ //abel
            imagenJugador.setImageResource(R.drawable.dorado);
        }
        else if(position==4){ //hector
            imagenJugador.setImageResource(R.drawable.hector);
        }
        else if(position==5){ //anton
            imagenJugador.setImageResource(R.drawable.anton);
        }
        else if(position==6){ //jordan
            imagenJugador.setImageResource(R.drawable.jordan);
        }
        else if(position==7){ //dorado
            imagenJugador.setImageResource(R.drawable.abel);
        }else if(position==10){//ex-jugador
            imagenJugador.setImageResource(R.drawable.invitado);
        }
        else if(position==11){//simpatizante
            imagenJugador.setImageResource(R.drawable.catenaxio_escudo);
        }else if(position==9){//Fer
        imagenJugador.setImageResource(R.drawable.fer);
    }

        return perfilesView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
