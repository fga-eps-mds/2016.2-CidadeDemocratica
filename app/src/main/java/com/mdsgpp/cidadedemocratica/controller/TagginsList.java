package com.mdsgpp.cidadedemocratica.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import java.util.ArrayList;

public class TagginsList extends AppCompatActivity {

    ListView tagginsListView;
    TextView proposalTitleTextView;
    TextView proposalDescripitionTextView;
    TextView relevanceTextView;

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
    }

    private ArrayList<Tag> getTagsList() {
        if (proposal != null) {
            return proposal.getTags();
        } else {
            return null;
        }
    }

}
