package com.catenaxio.beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.catenaxio.utils.Constantes;
import com.catenaxio.utils.MiParseador;
import com.catenaxio.utils.Validador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

/**
 * Created by Antonio on 20/01/2017.
 * @author Antonio "BitOven" Merillas
 */

public class Jugador {

    private String nombre;
    private int dorsal;
    private int convocatoria;
    private Bitmap imagen;
    private int imageResource;
    private int partidosGanados;
    private int partidosJugados;
    private int asistencias;
    private int goles;

    private static int golesTotales;
    private static int partidosTotales;

    static {
        golesTotales=0;
        partidosTotales=0;
    }

    //metodos


    public String getPorcentajeGoles(){
        float result;
        if(golesTotales!=0){
         result = (float)goles/(float)golesTotales *100;
            return MiParseador.parsearFormatoPorcentaje(result);
        }else {
            return "0";
        }
    }

    //getter y setters

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        if(Validador.validarNumPositivo(partidosJugados)){
            this.partidosJugados = partidosJugados;
        }
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        if(Validador.validarNumPositivo(dorsal)){
            this.dorsal = dorsal;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        if(Validador.validarNumPositivo(partidosGanados)){
            this.partidosGanados = partidosGanados;
        }
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        if(Validador.validarNumPositivo(asistencias)){
            this.asistencias = asistencias;
        }
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        if(Validador.validarNumPositivo(goles)) {
            this.goles = goles;
        }
    }

    public static int getPartidosTotales() {
        return partidosTotales;
    }

    public static void setPartidosTotales(int partidosTotales) {
        if(Validador.validarNumPositivo(partidosTotales)){
            Jugador.partidosTotales = partidosTotales;
        }
    }

    public static void resetPartidosTotales() {
        Jugador.partidosTotales = 0;
    }

    public static int getGolesTotales() {
        return golesTotales;
    }

    public static void setGolesTotales(int golesTotales) {
        if(Validador.validarNumPositivo(golesTotales)){
            Jugador.golesTotales = golesTotales;
        }
    }

    public static void resetGolesTotales() {
        Jugador.golesTotales = 0;
    }

    public int getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(int convocatoria) {
        this.convocatoria = convocatoria;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
