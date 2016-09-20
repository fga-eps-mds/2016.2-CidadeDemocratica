package com.mdsgpp.cidadedemocratica.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taggins_list);


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
        Intent i = getIntent();
        String idPassed = i.getExtras().getString("ProposalID");
        Long proposalId = Long.parseLong(idPassed);
        Proposal proposal = dataContainer.getProposalForId(proposalId);
        return proposal.getTags();

    }

}
