package com.mdsgpp.cidadedemocratica.controller;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.util.ArrayList;
import java.util.List;

public class ProposalsList extends AppCompatActivity {

    private ListView proposalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposals_list);

        proposalListView = (ListView) findViewById(R.id.prosals_listID);

        ArrayList<Proposal> proposalList = getProposalList();
        ArrayAdapter<Proposal> proposalAdapter = new ArrayAdapter<Proposal>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                proposalList
        );
        proposalListView.setAdapter(proposalAdapter);
    }

    public final static void pullProposalData() {
        Requester requester = new Requester("https://cidadedemocratica.herokuapp.com/data/topicos", new TagRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
        requester = null;
    }


    private ArrayList<Proposal> getProposalList() {
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getProposals();
    }
}
