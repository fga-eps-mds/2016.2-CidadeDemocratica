package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.Tagging;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.TaggingsRequestResponseHandler;

import java.util.ArrayList;

public class TagginsList extends AppCompatActivity implements View.OnClickListener, RequestUpdateListener {

    ListView tagginsListView;
    TextView proposalTitleTextView;
    TextView proposalDescripitionTextView;
    TextView relevanceTextView;
    Button shareButton;

    Proposal proposal;
    private ProgressDialog progressDialog;
    TagListAdapter taggingsAdapter;

    TaggingsRequestResponseHandler taggingsRequestResponseHandler;
    TagRequestResponseHandler tagRequestResponseHandler;

    private static String proposalIdParameterKey = "proposal_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taggins_list);

        DataContainer dataContainer = DataContainer.getInstance();
        Bundle extras = getIntent().getExtras();
        long proposalId = extras.getLong("proposalId");

        proposal = dataContainer.getProposalForId(proposalId);

        proposalTitleTextView = (TextView)findViewById(R.id.titleProposalID);
        proposalTitleTextView.setText(proposal.getTitle());

        proposalDescripitionTextView = (TextView)findViewById(R.id.proposalDescripitionID);
        proposalDescripitionTextView.setText(proposal.getContent());

        relevanceTextView = (TextView)findViewById(R.id.relevanceTextView);
        relevanceTextView.setText(String.valueOf(proposal.getRelevance()));

        tagginsListView = (ListView) findViewById(R.id.listaTagsDaPropostaID);
        ArrayList<Tag> tags = getTagsList();

        pullTaggingsData();

        if (tags == null) {
            Toast.makeText(getApplicationContext(),"Proposta não possui TAGS", Toast.LENGTH_SHORT);
        }
        taggingsAdapter = new TagListAdapter(this, tags);

        tagginsListView.setAdapter(taggingsAdapter);

        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);
    }

    private ArrayList<Tag> getTagsList() {
        if (proposal != null) {

            ArrayList<Tag> tags = new ArrayList<>();
            ArrayList<Tagging> taggings = DataContainer.getInstance().getTaggingsForProposalId(proposal.getId());
            for (Tagging t : taggings) {
                Tag tag = DataContainer.getInstance().getTagForId(t.getTagId());
                if (tag != null) {
                    tags.add(tag);
                }
            }

            return tags;
        } else {
            return new ArrayList<>();
        }
    }

    private void shareProposal(){

        String subjectShare = getString(R.string.title_sharing) + proposal.getTitle();
        String contentShare = subjectShare + getString(R.string.title_sharing_description) + proposal.getContent() + getString(R.string.link_site_cidade_democratica);

        Intent intentShare = new Intent(android.content.Intent.ACTION_SEND);

        intentShare.setType("text/plain");

        intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectShare);
        intentShare.putExtra(android.content.Intent.EXTRA_TEXT, contentShare);

        startActivity(Intent.createChooser(intentShare,getString(R.string.name_action_share)));
    }

    private void pullTaggingsData() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_proposal_detail));
        }

        taggingsRequestResponseHandler = new TaggingsRequestResponseHandler();
        taggingsRequestResponseHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(TaggingsRequestResponseHandler.taggingsEndpointUrl, taggingsRequestResponseHandler);
        requester.setParameter(proposalIdParameterKey, String.valueOf(proposal.getId()));
        requester.request(Requester.RequestType.GET);
    }

    @Override
    public void afterSuccess(JsonHttpResponseHandler handler) {

        if (handler == taggingsRequestResponseHandler) {

            tagRequestResponseHandler = new TagRequestResponseHandler();
            tagRequestResponseHandler.setRequestUpdateListener(this);

            Requester requester = new Requester(TagRequestResponseHandler.tagsEndpointUrl, tagRequestResponseHandler);
            requester.setParameter(proposalIdParameterKey, String.valueOf(proposal.getId()));
            requester.request(Requester.RequestType.GET);

            System.out.println("taggings done");

        } else if (handler == tagRequestResponseHandler) {
            progressDialog.dismiss();
            FeedbackManager.createToast(this, getString(R.string.message_success_load_tags));
            taggingsAdapter.updateData(getTagsList());
        }
    }

    @Override
    public void afterError(JsonHttpResponseHandler handler, String message) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shareButton:
                shareProposal();
                break;
        }

    }
}
