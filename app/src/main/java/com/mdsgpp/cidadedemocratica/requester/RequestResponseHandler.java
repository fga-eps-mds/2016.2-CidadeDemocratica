package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 01/11/16.
 */

public class RequestResponseHandler {

    public RequestUpdateListener requestUpdateListener;

    public void onSuccess(int statusCode, Map<String, List<String>> headers, JSONObject response) {
        afterSuccess(response);
    }

    public void onSuccess(int statusCode, Map<String, List<String>> headers, JSONArray response) {
        afterSuccess(response);
    }

    public void onSuccess(int statusCode, Map<String, List<String>> headers, String response) {
        afterSuccess(response);
    }

    public void onFailure(int statusCode, Map<String, List<String>> headers, JSONArray errorResponse) {
        String errorMessage = "Failed with status code: " + statusCode;
        System.out.println(errorMessage);
        afterError(errorMessage);
    }

    public RequestUpdateListener getRequestUpdateListener() {
        return requestUpdateListener;
    }

    public void setRequestUpdateListener(RequestUpdateListener requestUpdateListener) {
        this.requestUpdateListener = requestUpdateListener;
    }

    protected void afterSuccess(Object response) {
        if (requestUpdateListener != null) {
            requestUpdateListener.afterSuccess(this, response);
        } else { }
    }

    protected void afterError(String message) {
        if (requestUpdateListener != null) {
            requestUpdateListener.afterError(this, message);
        } else { }
    }
}
