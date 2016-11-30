package com.mdsgpp.cidadedemocratica.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.util.Pair;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mdsgpp.cidadedemocratica.External.StateAxisValueFormatter;
import com.mdsgpp.cidadedemocratica.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisresende on 29/11/16.
 */

public class CharterGenerator {

    public static void createBarChart(BarChart barChart, List<Pair<String, Integer>> chartData, String title, Context context){

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setScaleEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        YAxis yl = barChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);

        createChart(barChart,chartData,title,context);
    }

    private static void createChart(Chart barChart, List<Pair<String, Integer>> chartData, String title, Context context){

        int count = chartData.size() > 10 ? 10 : chartData.size();

        String[] stateNames = new String[count];
        for (int i=0; i<count; ++i) {
            Pair<String, Integer> record = chartData.get(i);
            stateNames[i] = record.first;
        }

        StateAxisValueFormatter stateAxisValueFormatter = new StateAxisValueFormatter(stateNames);

        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);

        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);
        xl.setTextSize(11f);
        xl.setTextColor(Color.BLACK);
        xl.setLabelCount(count);
        xl.setValueFormatter(stateAxisValueFormatter);

        // setting data
        Legend l = barChart.getLegend();
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setFormSize(11f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        setData(barChart, chartData, title,context);
    }

    private static void setData(Chart barChart, List<Pair<String, Integer>> chartData, String title, Context context) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        int count = chartData.size() > 10 ? 10 : chartData.size();
        for (int i = 0; i < count; i++) { //10 top states
            yVals.add(new BarEntry(i,chartData.get(i).second));
        }

        BarDataSet barDataSet = new BarDataSet(yVals, title);

        int[] colors = new int[]{context.getResources().getColor(R.color.colorChart1),
                context.getResources().getColor(R.color.colorChart2),
                context.getResources().getColor(R.color.colorChart3),
                context.getResources().getColor(R.color.colorChart4),
                context.getResources().getColor(R.color.colorChart5)};

        barDataSet.setColors(colors);

        BarData data = new BarData(barDataSet);
        data.setValueTextSize(12f);

        barChart.setData(data);
    }

}
