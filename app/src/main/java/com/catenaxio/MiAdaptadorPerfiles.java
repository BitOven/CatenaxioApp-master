package com.catenaxio;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        return perfilesView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
