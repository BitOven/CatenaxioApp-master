package com.catenaxio.utils;

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

    public static String parsearCalendarioElegido(String strTemporada){
        String[] calendarioArr = strTemporada.split("-");
        String calendario = "Calendario"+calendarioArr[0];
        return calendario;
    }
}
