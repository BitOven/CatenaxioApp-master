package com.catenaxio.activities;

import android.app.Activity;
import android.os.Bundle;

import com.catenaxio.R;
import com.catenaxio.beans.JugadorQuesito;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.utils.Constantes;
import com.catenaxio.utils.MiParseador;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Antonio on 28/01/2017.
 */

public class QuesitoActivity2 extends Activity {

    private PieChartView viewChart;
    private PieChartData pieChart;
    private List<JugadorQuesito> jugadores;
    private List<SliceValue> listPieChartValues;
    private List<SliceValue> listAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesito2);
//
        viewChart = (PieChartView) findViewById(R.id.chart);

        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            jugadores = (List<JugadorQuesito>) extras.getSerializable("jugadores");
            pieChart = new PieChartData();
            listAux= new ArrayList<SliceValue>();

            for(int j=0; j<jugadores.size(); j++){
                int goles = jugadores.get(j).getGoles();
                if(goles>0){
                    SliceValue value = new SliceValue();
                    value.setValue(goles);
                    value.setLabel(jugadores.get(j).getNombre()+": "+goles+" ("+jugadores.get(j).getPorcentajeGoles()+"%)");
                    value.setColor(Constantes.getColor(j));
                    listAux.add(value);
                }
            }

            listPieChartValues=ordenarListSliceValues(listAux);
            pieChart.setValues(listPieChartValues);
            pieChart.setHasLabels(true);
            pieChart.setSlicesSpacing(3);
            pieChart.setCenterText1("GOLES");
            pieChart.setCenterText2("Temporada "+ MiParseador.getTemporadaEnPreferencias(this));
            pieChart.setCenterCircleScale((float)0.55);
            pieChart.setHasCenterCircle(true);
            viewChart.setPieChartData(pieChart);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        pieChart.finish();
    }

    private List<SliceValue> ordenarListSliceValues(List<SliceValue> targetList) {
        ArrayList<SliceValue> listaOrdenada = new ArrayList<>();
        final int numeroJugadores= targetList.size();

        for(int i=0; i<numeroJugadores; i++){
            SliceValue max = new SliceValue();
            max.setValue(0);

            for (SliceValue sValue : targetList) {
                if (sValue.getValue() >= max.getValue()) {
                   max=sValue;
                }
            }
            listaOrdenada.add(max);
            targetList.remove(max);
        }

        return listaOrdenada;
    }
}
