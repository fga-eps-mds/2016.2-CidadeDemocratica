package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
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

public class SelectChartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RequestUpdateListener {

    private ListView typesChartListView;

    private ProgressDialog progressDialog;

    private String typeChartSelect;

    private RequestResponseHandler chartDataHandler;
    private ArrayList<Integer> chartDataValueRelevance = new ArrayList<>();
    private ArrayList<Integer> chartDataValueProposal = new ArrayList<>();
    private ArrayList<String> chartDataName = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chart);

        typesChartListView = (ListView) findViewById(R.id.typesChartsListView);
        typesChartListView.setOnItemClickListener(this);

        setTitle(getString(R.string.name_activity_select_chart));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        typeChartSelect = ((AppCompatTextView) view).getText().toString();
        pullChartData();
    }

    private void pullChartData() {
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_loading_chart));

        chartDataHandler = new RequestResponseHandler();
        chartDataHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(ProposalRequestResponseHandler.proposalsStateCountRelevanceEndpointUrl, chartDataHandler);
        requester.async(Requester.RequestMethod.GET);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

        progressDialog.dismiss();
        chartDataValueProposal = new ArrayList<>();
        chartDataValueRelevance = new ArrayList<>();
        chartDataName = new ArrayList<>();

        JSONArray data = (JSONArray) response;

        for (int i = 0; i < data.length(); ++i) {
            try {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String stateAbrev = jsonObject.getString("state_abrev");
                int proposalsRelevanceSum = jsonObject.getInt("proposals_relevance_sum");
                int proposalCount = jsonObject.getInt("proposal_count");

                chartDataName.add(stateAbrev);
                chartDataValueProposal.add(proposalCount);
                chartDataValueRelevance.add(proposalsRelevanceSum);
                if (chartDataName.size()==10){
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.reverse(chartDataValueProposal);
        Collections.reverse(chartDataValueRelevance);
        Collections.reverse(chartDataName);

        Bundle bundle = new Bundle();

        if (typeChartSelect.equals(getResources().getStringArray(R.array.types_charts_array)[0])){
            bundle.putIntegerArrayList(ChartActivity.keyValue,chartDataValueProposal);
        }else {
            bundle.putIntegerArrayList(ChartActivity.keyValue,chartDataValueRelevance);
        }

        bundle.putStringArrayList(ChartActivity.keyName,chartDataName);
        bundle.putString(ChartActivity.keyTitle,typeChartSelect);
        Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        progressDialog.dismiss();
        FeedbackManager.createToast(this,message);
    }

}
