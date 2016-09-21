package com.mdsgpp.cidadedemocratica.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

public class TagDetailActivity extends AppCompatActivity {

    private Tag tag;
    private DataContainer dataContainter = DataContainer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);

        Bundle extras = getIntent().getExtras();
        Long tagId = extras.getLong("tagId");
        this.tag = dataContainter.getTagForId(tagId);

        TextView tagNameTextView = (TextView)findViewById(R.id.tagNameTextView);
        tagNameTextView.setText(this.tag.getName());

        ListView proposalsListView = (ListView)findViewById(R.id.proposalsListView);

        ArrayAdapter<Proposal> proposalsAdapter = new ArrayAdapter<Proposal>(this,
                android.R.layout.simple_list_item_1, tag.getProposals());

        proposalsListView.setAdapter(proposalsAdapter);
    }
}
