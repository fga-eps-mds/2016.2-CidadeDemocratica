package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectStateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RequestUpdateListener {

    private ListView statesListView;

    private ProgressDialog progressDialog;
    private String federalUnityCodeParameterKey = "federal_unity_code";

    private RequestResponseHandler chartDataHandler;
    private ArrayList<Integer> chartDataValue = new ArrayList<>();
    private ArrayList<String> chartDataName = new ArrayList<>();
    private String stateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_state);

        statesListView = (ListView) findViewById(R.id.statesListView);
        statesListView.setOnItemClickListener(this);

        setTitle(getString(R.string.name_activity_select_state));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        stateSelected = ((AppCompatTextView) view).getText().toString();
        pullChartData();
    }

    private void pullChartData() {
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_loading_chart));

        chartDataHandler = new RequestResponseHandler();
        chartDataHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(TagRequestResponseHandler.tagStateUseCountEndpointUrl, chartDataHandler);
        requester.setParameter(federalUnityCodeParameterKey, stateSelected);
        requester.async(Requester.RequestMethod.GET);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

        progressDialog.dismiss();
        chartDataValue = new ArrayList<>();
        chartDataName = new ArrayList<>();

        JSONArray data = (JSONArray) response;

        for (int i = 0; i < data.length(); ++i) {
            try {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String name = jsonObject.getString("name");
                int count = jsonObject.getInt("tag_count");

                chartDataName.add(captalizeString(name));
                chartDataValue.add(count);
                if (chartDataName.size()==10){
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.reverse(chartDataValue);
        Collections.reverse(chartDataName);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(ChartActivity.keyValue,chartDataValue);
        bundle.putStringArrayList(ChartActivity.keyName,chartDataName);
        bundle.putString(ChartActivity.keyState,stateSelected);
        Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        progressDialog.dismiss();
        FeedbackManager.createToast(this,message);
    }

    private String captalizeString(String stringToCaptalize){
        return stringToCaptalize.substring(0, 1).toUpperCase() + stringToCaptalize.substring(1);
    }

}
