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

    public void onSuccess(int statusCode, Map<String, List<String>> headers, JSONArray response) {
        afterSuccess(jsonArrayToHashMapArray(response));
    }

    public void onFailure(int statusCode, Map<String, List<String>> headers, JSONArray errorResponse) {
        System.out.println("Failed with status code: " + statusCode);
    }

    private HashMap<String, Object> jsonToHashMap(JSONObject json) {

        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = json.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            Object value = null;
            try {
                value = json.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            map.put(key, value);
            keys.remove();
        }

        return map;
    }

    private ArrayList<HashMap<String, Object>> jsonArrayToHashMapArray(JSONArray jsonArray) {

        ArrayList<HashMap<String, Object>> responseArray = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                HashMap<String, Object> map = jsonToHashMap(object);
                responseArray.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return responseArray;
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
