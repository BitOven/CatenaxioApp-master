package com.catenaxio.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.catenaxio.R;
import com.catenaxio.beans.Jornada;
import com.catenaxio.beans.Jornadas;

/**
 * Created by Antonio on 22/01/2017.
 */

public class Preferencias {

    private static final int JORNADAS_STANDARD = 22;

    public static void cargarPreferenciasCalendario(Context cntx, Jornadas jornadasCalendario, String temporadaParseada){
        Jornadas jornadas=jornadasCalendario;
        SharedPreferences sharedPref = cntx.getSharedPreferences(cntx.getString(R.string.shared_pref_key), cntx.MODE_PRIVATE);

        for(int i=0; i<JORNADAS_STANDARD; i++){
            Jornada contenedor = new Jornada();
            contenedor.setNumJornada(sharedPref.getInt(Constantes.PREF_JORNADA+temporadaParseada+i,i+1));
            contenedor.setFecha(sharedPref.getString(Constantes.PREF_FECHA+temporadaParseada+i,"99-99-9999"));
            contenedor.setHora(sharedPref.getString(Constantes.PREF_HORA+temporadaParseada+i,"00:00"));
            contenedor.setRival(sharedPref.getString(Constantes.PREF_RIVAL+temporadaParseada+i,"Sin Datos"));
            contenedor.setLugar(sharedPref.getString(Constantes.PREF_LUGAR+temporadaParseada+i,"Vacío"));
            contenedor.setMarcador(sharedPref.getString(Constantes.PREF_MARCADOR+temporadaParseada+i,"?-?"));
            contenedor.setUrlCampo(sharedPref.getString(Constantes.PREF_URLCAMPO+temporadaParseada+i," "));
            contenedor.setResultado(sharedPref.getString(Constantes.PREF_RESULTADO+temporadaParseada+i,"X"));
            jornadas.addJornada(contenedor);
        }
    }

    /**
     *
     * @param cntx
     * @param jornadasCalendario
     * @param temporadaParseada
     */
    public static void guardarPreferenciasCalendario(Context cntx, Jornadas jornadasCalendario, String temporadaParseada){
        SharedPreferences settings = cntx.getSharedPreferences(cntx.getString(R.string.shared_pref_key), cntx.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        for(int i=0; i<jornadasCalendario.size(); i++){
            editor.putInt(Constantes.PREF_JORNADA+temporadaParseada+i, jornadasCalendario.getJornadas(i).getNumJornada());
            editor.putString(Constantes.PREF_FECHA+temporadaParseada+i,jornadasCalendario.getJornadas(i).getFecha());
            editor.putString(Constantes.PREF_HORA+temporadaParseada+i,jornadasCalendario.getJornadas(i).getHora());
            editor.putString(Constantes.PREF_RIVAL+temporadaParseada+i,jornadasCalendario.getJornadas(i).getRival());
            editor.putString(Constantes.PREF_LUGAR+temporadaParseada+i,jornadasCalendario.getJornadas(i).getLugar());
            editor.putString(Constantes.PREF_MARCADOR+temporadaParseada+i,jornadasCalendario.getJornadas(i).getMarcador());
            editor.putString(Constantes.PREF_URLCAMPO+temporadaParseada+i,jornadasCalendario.getJornadas(i).getUrlCampo());
            editor.putString(Constantes.PREF_RESULTADO+temporadaParseada+i,jornadasCalendario.getJornadas(i).getResultado());
            editor.apply();
        }


    }

}
