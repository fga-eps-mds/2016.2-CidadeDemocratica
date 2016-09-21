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
    TextView proposalTitle;
    TextView proposalDescripition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taggins_list);

        proposalTitle = (TextView)findViewById(R.id.titleProposalID);
        proposalTitle.setText(getProposalTitle());


        proposalDescripition = (TextView)findViewById(R.id.proposalDescripitionID);
        proposalDescripition.setText(getDescription());

        tagginsListView = (ListView) findViewById(R.id.listaTagsDaPropostaID);
        ArrayList<Tag> tags = getTagsList();

        if (tags== null) {
            Toast.makeText(getApplicationContext(),"Proposta não possui TAGS", Toast.LENGTH_SHORT);
        }
        TagListAdapter tagginsAdapter = new TagListAdapter(this, tags);

        tagginsListView.setAdapter(tagginsAdapter);
    }

    private ArrayList<Tag> getTagsList() {
        DataContainer dataContainer = DataContainer.getInstance();
        Bundle extra = getIntent().getExtras();
        String idPassed = extra.getString("ProposalId");
        if(idPassed != null) {
            Long proposalId = Long.parseLong(idPassed);
            Proposal proposal = dataContainer.getProposalForId(proposalId);
            return proposal.getTags();
        }

        return null;
    }

    private String getDescription() {
        DataContainer dataContainer = DataContainer.getInstance();
        Bundle extra = getIntent().getExtras();
        String idPassed = extra.getString("ProposalId");
        if(idPassed != null)
        {
            Long proposalId = Long.parseLong(idPassed);
            Proposal proposal = dataContainer.getProposalForId(proposalId);
            return proposal.getContent();
        }
        return  null;
    }

    private String getProposalTitle() {
        Bundle extra = getIntent().getExtras();
        if(extra!=null) {
            String proposalTitlePassed = extra.getString("ProposalTitle");
            return proposalTitlePassed;
        }
        return null;
    }

}
