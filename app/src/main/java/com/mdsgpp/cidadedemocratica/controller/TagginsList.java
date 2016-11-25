package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import java.util.HashSet;
import java.util.Set;

public class TagginsList extends AppCompatActivity implements RequestUpdateListener, MenuItem.OnMenuItemClickListener {

    ListView tagginsListView;
    TextView proposalTitleTextView;
    TextView proposalDescripitionTextView;
    TextView relevanceTextView;
    private MenuItem favoriteItem;
    private View header;

    Proposal proposal;
    private ProgressDialog progressDialog;
    TagListAdapter taggingsAdapter;

    TagRequestResponseHandler tagRequestResponseHandler;
    RequestResponseHandler favoriteRequestResponseHandler;

    private static ArrayList<Long> loadedProposalIds = new ArrayList<>();

    private static String proposalIdParameterKey = "proposal_id";
    private String favoriteProposalsKey = "favoriteProposalsKey";

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

        favoriteRequestResponseHandler = new RequestResponseHandler();
        favoriteRequestResponseHandler.setRequestUpdateListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (favoriteItem != null) {
            updateFavoriteIcon();
        }
    }

    private void updateFavoriteIcon() {
        if (isFavorite()) {
            favoriteItem.setIcon(R.drawable.favorite_icon_filled);
        } else {
            favoriteItem.setIcon(R.drawable.favorite_icon);

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

        if (handler == tagRequestResponseHandler) {
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
        } else if (handler == favoriteRequestResponseHandler) {

            System.out.print("(un)favoriting proposal");
        }
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

        updateFavoriteIcon();

        return super.onCreateOptionsMenu(menu);
    }

    private boolean isFavorite() {

        SharedPreferences preferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        Set<String> favoriteIds = preferences.getStringSet(favoriteProposalsKey, null);

        String stringId = String.valueOf(proposal.getId());
        return favoriteIds.contains(stringId);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_favorite:
                favoriteProposal();
                if (isFavorite()){
                    favoriteItem.setIcon(R.drawable.favorite_icon_filled);
                }else {
                    favoriteItem.setIcon(R.drawable.favorite_icon);
                }
                break;
            case R.id.action_share:
                shareProposal();
                break;
        }
        return false;
    }

    private void favoriteProposal() {
        Requester requester = new Requester(RequestResponseHandler.favoriteProposalsEndpoint, favoriteRequestResponseHandler);
        requester.setParameter("id", String.valueOf(proposal.getId()));
        requester.async(Requester.RequestMethod.POST);
        recordFavoriteProposal();
    }

    private void recordFavoriteProposal() {
        SharedPreferences preferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> favoriteIds = preferences.getStringSet(favoriteProposalsKey, null);

        if (favoriteIds == null) {
            favoriteIds = new HashSet<>();
        }
        String stringId = String.valueOf(proposal.getId());
        if (favoriteIds.contains(stringId)) {
            favoriteIds.remove(stringId);
        } else {
            favoriteIds.add(stringId);
        }

        editor.putStringSet(favoriteProposalsKey, favoriteIds);
        editor.apply();
    }

}