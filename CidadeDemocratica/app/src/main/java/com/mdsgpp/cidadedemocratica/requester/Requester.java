package com.mdsgpp.cidadedemocratica.requester;

import android.os.AsyncTask;
import android.text.GetChars;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Requester {

    public static enum RequestType {
        POST, GET, PATCH, DELETE
    }

    private String url = "";
    private AsyncTask<Void, Void, Void> requestHandler;

    public Requester(String url, AsyncTask<Void, Void, Void> requestHandler) {
        this.url = url;
        this.requestHandler = requestHandler;
    }

    public void request(RequestType method) {

        this.requestHandler.execute();
    }
}
