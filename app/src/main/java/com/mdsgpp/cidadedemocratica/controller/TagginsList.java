package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.util.ArrayList;

public class TagginsList extends AppCompatActivity implements RequestUpdateListener, MenuItem.OnMenuItemClickListener {

    ListView tagginsListView;
    TextView proposalTitleTextView;
    TextView proposalDescripitionTextView;
    TextView relevanceTextView;
    private MenuItem favoriteItem;
    private Boolean isFavorite;
    private View header;

    Proposal proposal;
    private ProgressDialog progressDialog;
    TagListAdapter taggingsAdapter;

    TagRequestResponseHandler tagRequestResponseHandler;

    private static ArrayList<Long> loadedProposalIds = new ArrayList<>();

    private static String proposalIdParameterKey = "proposal_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taggins_list);

        getInstanceViews();

        tagginsListView = (ListView) findViewById(R.id.listaTagsDaPropostaID);

        if (!loadedProposalIds.contains(proposal.getId())) {
            pullTaggingsData();
        } else {
            setTagsListViewAdapter(proposal.getTags());
        }

    }

    private void getInstanceViews(){
        header = getLayoutInflater().inflate(R.layout.fragment_header_taggins, null, false);

        EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

        Bundle extras = getIntent().getExtras();
        long proposalId = extras.getLong("proposalId");

        proposal = proposalsContainer.getForId(proposalId);

        proposalTitleTextView = (TextView)header.findViewById(R.id.titleProposalID);
        proposalTitleTextView.setText(proposal.getTitle());

        proposalDescripitionTextView = (TextView)header.findViewById(R.id.proposalDescripitionID);
        proposalDescripitionTextView.setText(proposal.getContent());

        relevanceTextView = (TextView)header.findViewById(R.id.relevanceTextView);
        relevanceTextView.setText(String.valueOf(proposal.getRelevance()));

    }

    private void shareProposal(){

        String subjectShare = getString(R.string.title_sharing) + " " + proposal.getTitle();
        String contentShare = getString(R.string.link_site_cidade_democratica) + "topico/" + this.proposal.getId() + "-" + this.proposal.getSlug() + "\n" + subjectShare + getString(R.string.title_sharing_description) + proposal.getContent();

        Intent intentShare = new Intent(android.content.Intent.ACTION_SEND);

        intentShare.setType("text/plain");

        intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectShare);
        intentShare.putExtra(android.content.Intent.EXTRA_TEXT, contentShare);

        startActivity(Intent.createChooser(intentShare,getString(R.string.name_action_share)));
    }

    private void setTagsListViewAdapter(ArrayList<Tag> tags) {

        taggingsAdapter = new TagListAdapter(this, tags);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tagginsListView.setAdapter(taggingsAdapter);
                tagginsListView.addHeaderView(header);
            }
        });
    }

    private void pullTaggingsData() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_proposal_detail));
        }

        tagRequestResponseHandler = new TagRequestResponseHandler();
        tagRequestResponseHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(TagRequestResponseHandler.tagsEndpointUrl, tagRequestResponseHandler);
        requester.setParameter(proposalIdParameterKey, String.valueOf(proposal.getId()));
        requester.async(Requester.RequestMethod.GET);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

        final TagginsList self = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                FeedbackManager.createToast(self, getString(R.string.message_success_load_tags));
            }
        });

        ArrayList<Tag> tags = (ArrayList<Tag>) response;
        proposal.setTags(tags);

        setTagsListViewAdapter(tags);
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_like, menu);
        favoriteItem = menu.findItem(R.id.action_favorite);
        favoriteItem.setOnMenuItemClickListener(this);
        isFavorite = false;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_favorite:
                //TODO: Chamar método para favoritar a proposta
                if (isFavorite){
                    isFavorite = false;
                    favoriteItem.setIcon(R.drawable.favorite_icon);
                }else {
                    isFavorite = true;
                    favoriteItem.setIcon(R.drawable.favorite_icon_filled);
                }
                break;
            case R.id.action_share:
                shareProposal();
                break;
        }
        return false;
    }
}
