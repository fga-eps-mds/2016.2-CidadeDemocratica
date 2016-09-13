package com.mdsgpp.cidadedemocratica.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

public class TagsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_list);
    }

    public final static void pullTagData() {
        Requester requester = new Requester("https://cidadedemocratica.herokuapp.com/data/tags", new TagRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
        requester = null;
}
