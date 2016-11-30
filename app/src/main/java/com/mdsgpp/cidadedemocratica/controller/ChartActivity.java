package com.mdsgpp.cidadedemocratica.controller;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.mdsgpp.cidadedemocratica.R;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private HorizontalBarChart horizontalBarChart;
    private List<Pair<String, Integer>> chartData = new ArrayList<>();
    private ArrayList<Integer> chartDataValue = new ArrayList<>();
    private ArrayList<String> chartDataName = new ArrayList<>();

    public static String keyName = "name";
    public static String keyValue = "value";
    public static String keyState = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        barChart = (BarChart) findViewById(R.id.barChart);
        horizontalBarChart = (HorizontalBarChart) findViewById(R.id.horizontalChart);

        Bundle extras = getIntent().getExtras();
        chartDataName = extras.getStringArrayList(keyName);
        chartDataValue = extras.getIntegerArrayList(keyValue);
        setTitle(extras.getString(keyState));

        populateChart();
    }



    private void populateChart(){
        barChart.setVisibility(View.GONE);
        horizontalBarChart.setVisibility(View.VISIBLE);
        generateChartData();
        CharterGenerator.createBarChart(horizontalBarChart,chartData,getString(R.string.name_chart_tag_state),this);
        horizontalBarChart.animateY(2500);
    }

    private void generateChartData(){
        for (int i=0; i<chartDataName.size(); i++){
            chartData.add(new Pair<>(chartDataName.get(i), chartDataValue.get(i)));
        }
    }
}
