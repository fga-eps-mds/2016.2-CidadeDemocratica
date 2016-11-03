package com.mdsgpp.cidadedemocratica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.controller.ProposalsList;
import com.mdsgpp.cidadedemocratica.controller.TagsList;
import com.mdsgpp.cidadedemocratica.controller.UsersList;

import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TaggingsRequestResponseHandler;

public class MainActivity extends AppCompatActivity {

    ListView proposalListView;
    final String proposalsEndpointUrl = "http://cidadedemocraticaapi.herokuapp.com/api/v0/proposals";
    final String taggingsEndpointUrl = "http://cidadedemocraticaapi.herokuapp.com/api/v0/taggings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //pullProposalData();
        // Semaphore stops until loads tags & proposals
        //pullTagginsData();

    }

    public void pullProposalData() {
        Requester requester = new Requester(proposalsEndpointUrl, new ProposalRequestResponseHandler());
        requester.getAsync();
    }

    public void pullTagginsData() {
        Requester requester = new Requester(taggingsEndpointUrl, new TaggingsRequestResponseHandler());
        requester.getAsync();
    }

    public  void showProposalList(View view){
        Intent proposalIntent = new Intent(this, ProposalsList.class);
        startActivity(proposalIntent);

    }

    public void showTagsList(View view){
        Intent tagsIntent = new Intent(this,TagsList.class);
        startActivity(tagsIntent);
    }
    public void showUsersList(View view){
        Intent usersIntent = new Intent(this,UsersList.class);
        startActivity(usersIntent);
    }

}
