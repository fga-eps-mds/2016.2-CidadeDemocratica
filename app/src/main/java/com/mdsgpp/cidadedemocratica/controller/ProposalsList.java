package com.mdsgpp.cidadedemocratica.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.io.Serializable;
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
        final ArrayAdapter<Proposal> proposalAdapter = new ArrayAdapter<Proposal>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                proposalList
        );
        proposalListView.setAdapter(proposalAdapter);

        proposalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Proposal proposalClicked = proposalAdapter.getItem(i);
                Long id = proposalClicked.getId();
                String proposalStringID = Long.toString(id);
                Intent intent = new Intent(getApplicationContext(),TagginsList.class);
                intent.putExtra("ProposalId", proposalStringID);
                startActivity(intent);

            }
        });


    }

    public final static void pullProposalData() {
        Requester requester = new Requester("http://cidadedemocraticaapi.herokuapp.com/api/v0/propouses", new ProposalRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
        requester = null;
    }


    private ArrayList<Proposal> getProposalList() {
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getProposals();
    }
}
