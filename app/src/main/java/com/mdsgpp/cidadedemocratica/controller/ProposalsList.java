package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mdsgpp.cidadedemocratica.External.SlidingTabLayout;
import com.mdsgpp.cidadedemocratica.R;

import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;


public class ProposalsList extends AppCompatActivity implements ListProposalFragment.OnFragmentInteractionListener, RequestUpdateListener {

    private int numberOfTabs = 3;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposals_list);
        if (DataContainer.getInstance().getUsers().size() == 0) {
            pullProposalsData();
        } else {
            loadProposalsList();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void pullProposalsData() {
        progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_proposals));
        ProposalRequestResponseHandler proposalRequestResponseHandler = new ProposalRequestResponseHandler();
        setDataUpdateListener(proposalRequestResponseHandler);
        Requester requester = new Requester(ProposalRequestResponseHandler.proposalsEndpointUrl, proposalRequestResponseHandler);
        requester.request(Requester.RequestType.GET);
    }

    private void loadProposalsList() {

        CharSequence titles[] = {getString(R.string.titulo_tab_tudo), getString(R.string.titulo_tab_porAqui),
                getString(R.string.titulo_tab_localidade)};

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, numberOfTabs);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });

        tabs.setViewPager(pager);
    }

    public void setDataUpdateListener(ProposalRequestResponseHandler handler) {
        handler.setRequestUpdateListener(this);
    }

    public void afterSuccess() {
        progressDialog.dismiss();
        loadProposalsList();
        createToast(getString(R.string.message_success_load_proposals));
    }

    private void createToast(String message) {
        FeedbackManager.createToast(this, message);
    }

    public void afterError(String message) {

    }
}
