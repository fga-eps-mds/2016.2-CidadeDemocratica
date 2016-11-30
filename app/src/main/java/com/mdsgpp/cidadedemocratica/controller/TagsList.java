package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.util.ArrayList;

public class TagsList extends AppCompatActivity implements RequestUpdateListener, MenuItem.OnMenuItemClickListener {

    private ProgressDialog progressDialog;
    private final TagListAdapter tagAdapter = new TagListAdapter(this, new ArrayList<Tag>());
    private int preLast;

    EntityContainer<Tag> tagsContainer = EntityContainer.getInstance(Tag.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_list);
        setTitle(R.string.tags);

        if (tagsContainer.getAll().size() < 30) {
            pullTagData();
        } else {
            loadTagsList();
        }

        ListView tagsList = (ListView) findViewById(R.id.tagsList);
        tagsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;

                if(lastItem == totalItemCount - 15) {
                    if(preLast != lastItem) {
                        preLast = lastItem;
                        pullTagData();
                    }
                }


            }
        });

    }

    private ArrayList<Tag> getTags() {
        return tagsContainer.getAll();
    }

    private void loadTagsList() {

        ListView tagsListView = (ListView) findViewById(R.id.tagsList);

        ArrayList<Tag> tags = getTags();
        tagAdapter.updateData(tags);

        if (tagsListView.getAdapter() == null) {
            tagsListView.setAdapter(tagAdapter);
        }

        tagsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tag tag = (Tag)tagAdapter.getItem(i);
                Long id = tag.getId();

                Intent intent = new Intent(getApplicationContext(), TagDetailActivity.class);
                intent.putExtra("tagId", id);

                startActivity(intent);
            }
        });

    }

    public void pullTagData() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.message_load_tags));
        }
        TagRequestResponseHandler tagRequestResponseHandler = new TagRequestResponseHandler();
        setDataUpdateListener(tagRequestResponseHandler);

        Requester requester = new Requester(TagRequestResponseHandler.tagsEndpointUrl, tagRequestResponseHandler);
        requester.setParameter("page", String.valueOf(TagRequestResponseHandler.nextPageToRequest));
        requester.async(Requester.RequestMethod.GET);
    }

    private void setDataUpdateListener(TagRequestResponseHandler tagRequestResponseHandler) {

        tagRequestResponseHandler.setRequestUpdateListener(this);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        final TagsList self = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                loadTagsList();
                FeedbackManager.createToast(self, getString(R.string.message_success_load_tags));
            }
        });

        TagRequestResponseHandler.nextPageToRequest++;
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        progressDialog.dismiss();
        FeedbackManager.createToast(this, message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_graph, menu);
        MenuItem graphItem = menu.findItem(R.id.action_graph);
        graphItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_graph:
                Intent intent = new Intent(getApplicationContext(), SelectStateActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
