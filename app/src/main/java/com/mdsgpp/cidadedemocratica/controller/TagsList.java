package com.mdsgpp.cidadedemocratica.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.util.ArrayList;

public class TagsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_list);

        ListView tagsListView = (ListView) findViewById(R.id.tags_list);

        ArrayList<Tag> tagsList = getTagsList();
        final ArrayAdapter<Tag> tagsAdapter = new ArrayAdapter<Tag>(this,android.R.layout.simple_list_item_1, tagsList);
        tagsListView.setAdapter(tagsAdapter);

        tagsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tag tag = tagsAdapter.getItem(i);
                Long id = tag.getId();

                Intent intent = new Intent(getApplicationContext(), TagDetailActivity.class);
                intent.putExtra("tagId", id);

                startActivity(intent);
            }
        });
    }

    public final static void pullTagData() {
        Requester requester = new Requester("http://cidadedemocraticaapi.herokuapp.com/api/v0/tags", new TagRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
        requester = null;
    }

    private ArrayList<Tag> getTagsList() {
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getTags();
    }
}
