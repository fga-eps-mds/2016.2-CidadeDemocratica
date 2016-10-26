package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.util.ArrayList;

public class TagsList extends AppCompatActivity implements RequestUpdateListener {

    private ProgressDialog progressDialog;
    private final TagListAdapter tagAdapter = new TagListAdapter(this, new ArrayList<Tag>());
    private int preLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_list);

        if (DataContainer.getInstance().getTags().size() == 0) {
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
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getTags();
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
        requester.request(Requester.RequestType.GET);
    }

    private void setDataUpdateListener(TagRequestResponseHandler tagRequestResponseHandler) {

        tagRequestResponseHandler.setRequestUpdateListener(this);
    }

    @Override
    public void afterSuccess(JsonHttpResponseHandler handler) {
        progressDialog.dismiss();
        loadTagsList();
        FeedbackManager.createToast(this, getString(R.string.message_success_load_tags));
        TagRequestResponseHandler.nextPageToRequest++;
    }

    @Override
    public void afterSuccess(JsonHttpResponseHandler handler, Object response) {

    }

    @Override
    public void afterError(JsonHttpResponseHandler handler, String message) {
        progressDialog.dismiss();
        FeedbackManager.createToast(this, message);
    }
}
