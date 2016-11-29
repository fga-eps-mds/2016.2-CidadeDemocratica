package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mdsgpp.cidadedemocratica.External.StateAxisValueFormatter;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;

import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagDetailActivity extends AppCompatActivity implements OnFragmentInteractionListener, ListProposalFragment.OnFragmentInteractionListener, RequestUpdateListener {

    private Tag tag;
    private EntityContainer<Tag> tagsContainer = EntityContainer.getInstance(Tag.class);
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;

    private BarChart barChart;

    private ProposalRequestResponseHandler proposalRequestResponseHandler;
    private String tagIdParameterKey = "tag_id";

    private static ArrayList<Long> loadedTagIds = new ArrayList<>();
    private RequestResponseHandler chartDataHandler;
    private List<Pair<String, Integer>> chartData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);

        fragmentManager = getSupportFragmentManager();

        Bundle extras = getIntent().getExtras();
        Long tagId = extras.getLong("tagId");
        this.tag = tagsContainer.getForId(tagId);

        TextView tagNameTextView = (TextView)findViewById(R.id.tagNameTextView);
        tagNameTextView.setText(getString(R.string.placeholder_tag_detail) + " " + this.tag.getName());

        if (!loadedTagIds.contains(tag.getId())) {
            pullTagDetailData();
        } else {
            loadProposalsList(tag.getProposals());
        }

        barChart = (BarChart) findViewById(R.id.chartTagDetail);
        pullChartData();
    }

    private void loadProposalsList(ArrayList<Proposal> proposals) {

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, ListProposalFragment.newInstance(proposals)).
                commit();
    }

    private void createChart(){

        String[] stateNames = new String[chartData.size()];
        int i = 0;
        for (Pair<String, Integer> record : chartData) {
            stateNames[i++] = record.first;
        }

        StateAxisValueFormatter stateAxisValueFormatter = new StateAxisValueFormatter(barChart,stateNames);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setScaleEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setTouchEnabled(false);

        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);
        xl.setTextSize(13f);
        xl.setTextColor(Color.BLACK);
        xl.setLabelCount(10);
        xl.setValueFormatter(stateAxisValueFormatter);

        YAxis yl = barChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
        //yl.setTypeface(typeface);
        //yl.setTextSize();
        //yl.setTextColor(Color.GREEN);

        setData(barChart);

        // setting data
        Legend l = barChart.getLegend();
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setFormSize(11f);
        l.setXEntrySpace(4f);
        //l.setTypeface(typeface);
        // mChart.setDrawLegend(false);
    }

    private void setData(BarChart barChart) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        int count = chartData.size() > 10 ? 10 : chartData.size();
        for (int i = 0; i < count; i++) { //10 top states
            yVals.add(new BarEntry(i,chartData.get(i).second));
        }

        String nameLegend = "Quantidade de usos da tag " + this.tag.getName() + " por estado";
        BarDataSet barDataSet = new BarDataSet(yVals, nameLegend);

        int[] colors = new int[]{getResources().getColor(R.color.colorChart1),
                getResources().getColor(R.color.colorChart2),
                getResources().getColor(R.color.colorChart3),
                getResources().getColor(R.color.colorChart4),
                getResources().getColor(R.color.colorChart5)};

        barDataSet.setColors(colors);

        BarData data = new BarData(barDataSet);
        data.setValueTextSize(12f);

        barChart.setData(data);
    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void pullTagDetailData() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_tag_detail));
        }

        proposalRequestResponseHandler = new ProposalRequestResponseHandler();
        proposalRequestResponseHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(ProposalRequestResponseHandler.proposalsEndpointUrl, proposalRequestResponseHandler);
        requester.setParameter(tagIdParameterKey, String.valueOf(tag.getId()));
        requester.async(Requester.RequestMethod.GET);
    }

    private void pullChartData() {
        chartDataHandler = new RequestResponseHandler();
        chartDataHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(TagRequestResponseHandler.tagUserCountByStateEndpointUrl, chartDataHandler);
        requester.setParameter(tagIdParameterKey, String.valueOf(tag.getId()));

        requester.async(Requester.RequestMethod.GET);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

        if (handler == chartDataHandler) {

            JSONArray data = (JSONArray) response;

            for (int i = 0; i < data.length(); ++i) {
                try {
                    JSONObject jsonObject = (JSONObject) data.get(i);
                    String abbrev = jsonObject.getString("state_abrev");
                    int count = jsonObject.getInt("tag_count");

                    chartData.add(new Pair<>(abbrev, count));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            createChart();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    barChart.animateY(2500);
                }
            });

        } else {
            final ArrayList<Proposal> proposals = (ArrayList<Proposal>) response;

            tag.setProposals(proposals);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    loadProposalsList(proposals);
                }
            });
        }
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}