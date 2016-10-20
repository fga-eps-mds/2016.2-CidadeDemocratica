package com.mdsgpp.cidadedemocratica.requester;

import android.os.AsyncTask;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Requester {

    public static enum RequestType {
        POST, GET, PATCH, DELETE
    }

    private String url = "";
    private String urlSuffix = "";
    private AsyncHttpResponseHandler responseHandler;
    private static AsyncHttpClient client = Looper.myLooper() == null ? new SyncHttpClient() : new AsyncHttpClient();

    public Requester(String url, AsyncHttpResponseHandler responseHandler) {
        this.url = url;
        this.responseHandler = responseHandler;
    }

    public void request(RequestType method) {
        if (method == RequestType.GET) {

            client.get(this.url + urlSuffix, this.responseHandler);
        }
    }

    public void setUrlSuffix(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }
}
