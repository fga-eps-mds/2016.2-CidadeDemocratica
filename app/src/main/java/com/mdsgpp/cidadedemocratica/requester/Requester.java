package com.mdsgpp.cidadedemocratica.requester;

import android.os.AsyncTask;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.util.HashMap;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Requester {

    public static enum RequestType {
        POST, GET, PATCH, DELETE
    }

    private String url = "";
    private HashMap<String, String> parameters = new HashMap<String, String>();
    private AsyncHttpResponseHandler responseHandler;
    private static AsyncHttpClient client = Looper.myLooper() == null ? new SyncHttpClient() : new AsyncHttpClient();
    private static SyncHttpClient syncClient = new SyncHttpClient();

    public Requester(String url, AsyncHttpResponseHandler responseHandler) {
        this.url = url;
        this.responseHandler = responseHandler;
    }

    public void request(RequestType method) {
        if (method == RequestType.GET) {

            String endpoint = this.url;
            for (String key : parameters.keySet()) {
                endpoint += "?" + key + "=" + parameters.get(key);
            }
            client.get(endpoint, this.responseHandler);
        }
    }

    public void syncRequest(RequestType method) {
        if (method == RequestType.GET) {

            String endpoint = this.url;
            for (String key : parameters.keySet()) {
                endpoint += "?" + key + "=" + parameters.get(key);
            }
            syncClient.get(endpoint, this.responseHandler);
        }
    }

    public void setParameter(String key, String parameter) {
        parameters.put(key, parameter);
    }
}
