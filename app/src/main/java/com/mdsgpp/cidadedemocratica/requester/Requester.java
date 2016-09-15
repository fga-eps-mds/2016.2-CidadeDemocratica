package com.mdsgpp.cidadedemocratica.requester;

import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Requester {

    public static enum RequestType {
        POST, GET, PATCH, DELETE
    }

    private String url = "";
    private AsyncHttpResponseHandler responseHandler;
    private AsyncHttpClient client = new AsyncHttpClient();

    public Requester(String url, AsyncHttpResponseHandler responseHandler) {
        this.url = url;
        this.responseHandler = responseHandler;
    }

    public void request(RequestType method) {
        if (method == RequestType.GET) {

            client.get(this.url, this.responseHandler);
        }
    }
}
