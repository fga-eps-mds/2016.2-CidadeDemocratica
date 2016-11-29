package com.mdsgpp.cidadedemocratica.External;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by luisresende on 29/11/16.
 */

public class StateAxisValueFormatter implements IAxisValueFormatter {

    private BarChart barChart;
    private String [] orderStates;

    public StateAxisValueFormatter (BarChart barChart, String[] orderStates){
        this.barChart = barChart;
        this.orderStates = orderStates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return orderStates[(int) value];
    }
}
