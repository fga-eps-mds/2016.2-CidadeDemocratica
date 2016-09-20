package com.mdsgpp.cidadedemocratica;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.controller.ProposalsList;
import com.mdsgpp.cidadedemocratica.controller.TagginsList;
import com.mdsgpp.cidadedemocratica.controller.TagsList;

import layout.ListProposalFragment;

public class MainActivity extends AppCompatActivity {

    ListView proposalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TagsList.pullTagData();
        ListProposalFragment.pullProposalData();
        TagginsList.pullTagginsData();


    }

    public  void showProposalList(View view){
        Intent proposalIntent = new Intent(this, ProposalsList.class);
        startActivity(proposalIntent);

    }

    public void showTagsList(View view){
        Intent tagsIntent = new Intent(this,TagsList.class);
        startActivity(tagsIntent);
    }
}
