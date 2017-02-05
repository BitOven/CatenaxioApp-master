package com.catenaxio.utils;

import android.graphics.Color;

import com.catenaxio.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antonio on 21/01/2017.
 */

public class Constantes {

    //resultados
    public static final char PENDIENTE = 'X';
    public static final char GANADO = 'G';
    public static final char EMPATADO = 'E';
    public static final char PERDIDO = 'P';
    public static final char DESCANSO = 'D';
    public static final char APLAZADO = 'A';

    //urls campos
    public static final String JUANDE_LOCATION = "https://goo.gl/maps/tzZzNaKi82r";
    public static final String PERALES_LOCATION = "https://goo.gl/maps/UQv26o5xtpP2";
    public static final String GINER_LOCATION = "https://goo.gl/maps/tndFK6Hs1nx";
    public static final String SECTOR3_LOCATION = "https://goo.gl/maps/b8ABjW6AMN62";
    public static final String M4_LOCATION = "https://goo.gl/maps/pjyvALHC5Cq";
    public static final String BUENAVISTA_LOCATION = "https://goo.gl/maps/wHjGjdRMB2o";

    //preferencias
    public static final String PREF_JORNADA = "jornadaPref";
    public static final String PREF_FECHA = "fechaPref";
    public static final String PREF_HORA = "horaPref";
    public static final String PREF_RIVAL = "rivalPref";
    public static final String PREF_LUGAR = "lugarPref";
    public static final String PREF_MARCADOR = "marcadorPref";
    public static final String PREF_URLCAMPO = "urlcampoPref";
    public static final String PREF_RESULTADO = "resultadoPref";

    //firebase
    public static final String FRBS_ESTADISTICAS = "Estadisticas";
    public static final String FRBS_JUGADORES = "Jugadores";
    public static final String FRBS_ASISTENCIAS = "Asistencias";
    public static final String FRBS_GOLES = "Goles";
    public static final String FRBS_NOMBREJUGADORES = "Nombre";
    public static final String FRBS_PARTIDOSGANADOS = "PG";
    public static final String FRBS_PARTIDOSJUGADOS = "PJ";
    public static final String FRBS_PARTIDOSTOTALES = "PJ";//Está como PJ en el servidor, en distinto nivel de objeto

    public static final String FRBS_CALENDARIO = "Calendario";

    //storage firebase
    public static final String FRBS_CLASIFICACION = "clasificacion";
    public static final String PNG_EXTENSION = ".png";
    public static final String JPG_EXTENSION = ".jpg";
    public static final String JUGADORES_STORAGE = "jugadores";

    public static final Map<String, Integer> IMG_JUGADORES= new HashMap<String, Integer>();

    private static final int [] colores = {
            Color.BLUE,
            Color.CYAN,
            Color.argb(255, 200, 100, 100),
            Color.argb(255, 200, 200, 100),
            Color.argb(255, 100, 200, 100),
            Color.argb(255, 100, 100, 200),
            Color.GREEN,
            Color.MAGENTA,
            Color.YELLOW,
            Color.RED,
            R.color.darkdarkblue,
            R.color.darkpurple,
            R.color.gray,
            R.color.white,
            R.color.black,
    };

    static{
        IMG_JUGADORES.put("abel", R.drawable.abel);
        IMG_JUGADORES.put("dorado", R.drawable.dorado);
        IMG_JUGADORES.put("anton", R.drawable.anton);
        IMG_JUGADORES.put("hector", R.drawable.hector);
        IMG_JUGADORES.put("hugo", R.drawable.hugo);
        IMG_JUGADORES.put("jordan", R.drawable.jordan);
        IMG_JUGADORES.put("juanito", R.drawable.juanito);
        IMG_JUGADORES.put("juanma", R.drawable.juanma);
        IMG_JUGADORES.put("meri", R.drawable.meri);
        IMG_JUGADORES.put("invitado", R.drawable.invitado);
        IMG_JUGADORES.put("fer",R.drawable.fer);
    }

    public static int getColor(int i){
        return colores[i];
    }

}
