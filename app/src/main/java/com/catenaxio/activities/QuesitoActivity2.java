package com.catenaxio.activities;

import android.app.Activity;
import android.os.Bundle;

import com.catenaxio.R;
import com.catenaxio.beans.Jugadores;
import com.catenaxio.utils.Constantes;

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
    private Jugadores jugadores;
    private List<SliceValue> listPieChartValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesito2);
//
        viewChart = (PieChartView) findViewById(R.id.chart);

        if(getIntent()!=null){
            Bundle extras = getIntent().getExtras();
            jugadores = (Jugadores) extras.getSerializable("jugadores");
            pieChart = new PieChartData();
            listPieChartValues= new ArrayList<SliceValue>();

            for(int j=0; j<jugadores.size(); j++){
                int goles = jugadores.getJugadores(j).getGoles();
                if(goles>0){
                    SliceValue value = new SliceValue();
                    value.setValue(goles);
                    value.setLabel(jugadores.getJugadores(j).getNombre()+": "+goles+" ("+jugadores.getJugadores(j).getPorcentajeGoles()+"%)");
                    value.setColor(Constantes.getColor(j));
                    listPieChartValues.add(value);
                }
            }

            pieChart.setValues(listPieChartValues);
            pieChart.setHasLabels(true);
            pieChart.setSlicesSpacing(3);
            viewChart.setPieChartData(pieChart);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        pieChart.finish();
    }
}
