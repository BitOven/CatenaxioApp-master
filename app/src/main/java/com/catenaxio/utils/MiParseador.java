package com.catenaxio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.catenaxio.R;

import java.text.DecimalFormat;

/**
 * Created by Antonio on 21/01/2017.
 */

public class MiParseador {

    public static String[] parsearFechaYHora(String fechaYhora){
        String resultado[] = fechaYhora.split(" ");
        if(resultado.length==2){
            return resultado;
        }else{
            String[] defaultArr={"99-99-9999","00:00"};
            return defaultArr;
        }
    }

    /**
     *
     * @param strTemporada devuelve formato "Calendario2016"
     * @return
     */
    public static String parsearCalendarioElegido(String strTemporada){
        String[] calendarioArr = strTemporada.split("-");
        String calendario = Constantes.FRBS_CALENDARIO+calendarioArr[0];
        return calendario;
    }

    /**
     *
     * @param cntx
     * @return año de inicio de la temporada
     */
    public static String parsearTemporadaAYear(Context cntx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        String strTemporada = prefs.getString(cntx.getString(R.string.pref_temporada_key),cntx.getString(R.string.pref_temporada_default));
        String[] calendario = strTemporada.split("-");
        return calendario[0];
    }

    public static String getTemporadaEnPreferencias(Context cntx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        String strTemporada = prefs.getString(cntx.getString(R.string.pref_temporada_key),cntx.getString(R.string.pref_temporada_default));
        return strTemporada;
    }

    /**
     *
     * @param lugar
     * @return url if campo was found, blanck space otherwise
     */
    public static String urlCampoFinder(String lugar){
        if(lugar.contains("Juan")){
            return Constantes.JUANDE_LOCATION;
        }else if(lugar.contains("Perales")){
            return Constantes.PERALES_LOCATION;
        }else if(lugar.contains("Giner")){
            return Constantes.GINER_LOCATION;
        }else if(lugar.contains("Sector")){
            return Constantes.SECTOR3_LOCATION;
        }else if(lugar.contains("4")){
            return Constantes.M4_LOCATION;
        }else if(lugar.contains("Buenavista")){
            return Constantes.BUENAVISTA_LOCATION;
        }
        return " ";
    }

    public static String parsearFormatoPorcentaje(float resultado){
        DecimalFormat decimales = new DecimalFormat("0.0");
        return (decimales.format(resultado));
    }
}
