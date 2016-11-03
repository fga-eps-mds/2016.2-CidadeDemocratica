package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TaggingsRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import java.util.ArrayList;

public class TagDetailActivity extends AppCompatActivity implements OnFragmentInteractionListener, ListProposalFragment.OnFragmentInteractionListener, RequestUpdateListener {

    private Tag tag;
    private DataContainer dataContainter = DataContainer.getInstance();
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;

    private TaggingsRequestResponseHandler taggingsRequestResponseHandler;
    private ProposalRequestResponseHandler proposalRequestResponseHandler;
    private String tagIdParameterKey = "tag_id";

    private static ArrayList<Long> loadedTagIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);

        fragmentManager = getSupportFragmentManager();

        Bundle extras = getIntent().getExtras();
        Long tagId = extras.getLong("tagId");
        this.tag = dataContainter.getTagForId(tagId);

        TextView tagNameTextView = (TextView)findViewById(R.id.tagNameTextView);
        tagNameTextView.setText(this.tag.getName());

        if (!loadedTagIds.contains(tag.getId())) {
            pullTaggingsData();
        } else {
            loadProposalsList(tag.getProposals());
        }
        
    }

    private void loadProposalsList(ArrayList<Proposal> proposals) {

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, ListProposalFragment.newInstance(proposals)).
                commit();
    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void pullTaggingsData() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_tag_detail));
        }

        proposalRequestResponseHandler = new ProposalRequestResponseHandler();
        proposalRequestResponseHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(ProposalRequestResponseHandler.proposalsEndpointUrl, proposalRequestResponseHandler);
        requester.setParameter(tagIdParameterKey, String.valueOf(tag.getId()));
        requester.request(Requester.RequestMethod.GET);
    }

    @Override
    public void afterSuccess(JsonHttpResponseHandler handler, Object response) {
        progressDialog.dismiss();
        ArrayList<Proposal> proposals = (ArrayList<Proposal>) response;

        tag.setProposals(proposals);
        loadProposalsList(proposals);
    }

    @Override
    public void afterError(JsonHttpResponseHandler handler, String message) {

    }
}
