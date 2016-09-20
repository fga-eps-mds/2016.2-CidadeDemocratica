package com.mdsgpp.cidadedemocratica.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TaggingsRequestResponseHandler;

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
        ArrayList<Tag> tagginsList = new ArrayList<Tag>();
        if(getTagginsList().size()>0){
            tagginsList=getTagginsList();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Proposta não possui TAGS",Toast.LENGTH_SHORT);
        }
        ArrayAdapter<Tag> tagginsAdapter = new ArrayAdapter<Tag>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                tagginsList
        );

        tagginsListView.setAdapter(tagginsAdapter);
    }

    public final static void pullTagginsData() {
        Requester requester = new Requester("http://cidadedemocraticaapi.herokuapp.com/api/v0/taggings", new TaggingsRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
    }

    private ArrayList<Tag> getTagginsList() {
        DataContainer dataContainer = DataContainer.getInstance();
        Bundle extra = getIntent().getExtras();
        String idPassed = extra.getString("ProposalId");
        if(idPassed != null)
        {
            Long proposalId = Long.parseLong(idPassed);
            Proposal proposal = dataContainer.getProposalForId(proposalId);
            return proposal.getTags();
        }
        return  null;


    }

    private String getDescription()
    {
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

    private String getProposalTitle()
    {
        Bundle extra = getIntent().getExtras();
        if(extra!=null)
        {
            String proposalTitlePassed = extra.getString("ProposalTitle");
            return proposalTitlePassed;
        }
        return null;
    }

}
