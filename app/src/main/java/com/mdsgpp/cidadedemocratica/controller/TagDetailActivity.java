package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TagDetailActivity extends AppCompatActivity implements OnFragmentInteractionListener, ListProposalFragment.OnFragmentInteractionListener, RequestUpdateListener {

    private Tag tag;
    private EntityContainer<Tag> tagsContainer = EntityContainer.getInstance(Tag.class);
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private View header;
    private TextView tagNameTextView;

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

        getInstanceViews();

        if (!loadedTagIds.contains(tag.getId())) {
            pullTagDetailData();
        } else {
            loadProposalsList(tag.getProposals());
        }

        pullChartData();

        setTitle(captalizeString(this.tag.getName()));

        setInstanceViews();
    }

    private void getInstanceViews() {
        header = getLayoutInflater().inflate(R.layout.header_tag_detail, null, false);
        tagNameTextView = (TextView) header.findViewById(R.id.tagNameTextView);
        barChart = (BarChart) header.findViewById(R.id.barChart);
    }

    private void setInstanceViews(){
        tagNameTextView.setText(getString(R.string.placeholder_tag_detail) + " " + this.tag.getName());
    }

    private void loadProposalsList(ArrayList<Proposal> proposals) {

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, ListProposalFragment.newInstance(proposals, header)).
                commit();
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

            CharterGenerator.createBarChart(barChart,chartData,getString(R.string.name_chart_tag_detail),this);

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
                    if(progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    loadProposalsList(proposals);
                }
            });
        }
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }

    @Override
    public void onPause(){

        super.onPause();
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private String captalizeString(String stringToCaptalize){
        return stringToCaptalize.substring(0, 1).toUpperCase() + stringToCaptalize.substring(1);
    }
}