package com.mdsgpp.cidadedemocratica.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import java.util.ArrayList;

public class TagginsList extends AppCompatActivity implements View.OnClickListener{

    ListView tagginsListView;
    TextView proposalTitleTextView;
    TextView proposalDescripitionTextView;
    TextView relevanceTextView;
    Button shareButton;

    Proposal proposal;

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

        if (tags == null) {
            Toast.makeText(getApplicationContext(),"Proposta não possui TAGS", Toast.LENGTH_SHORT);
        }
        TagListAdapter tagginsAdapter = new TagListAdapter(this, tags);

        tagginsListView.setAdapter(tagginsAdapter);

        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);
    }

    private ArrayList<Tag> getTagsList() {
        if (proposal != null) {
            return proposal.getTags();
        } else {
            return null;
        }
    }

    private void shareProposal(){

        String subjectShare = getString(R.string.title_sharing) + proposal.getTitle();
        String contentShare = subjectShare + getString(R.string.title_sharing_description) + proposal.getContent() + getString(R.string.link_site_cidade_democratica) + "/topico/" + this.proposal.getId() + "-" + this.proposal.getSlug();

        Intent intentShare =new Intent(android.content.Intent.ACTION_SEND);

        intentShare.setType("text/plain");

        intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT,subjectShare);
        intentShare.putExtra(android.content.Intent.EXTRA_TEXT, contentShare);

        startActivity(Intent.createChooser(intentShare,getString(R.string.name_action_share)));
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
