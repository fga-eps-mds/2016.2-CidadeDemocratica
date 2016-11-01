package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 01/11/16.
 */

public class RequestResponseHandler extends JsonHttpResponseHandler {


    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if (statusCode == 200) {

            ArrayList<HashMap<String, Object>> responseArray = new ArrayList<>();
            for (int i = 0; i < response.length(); ++i) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    HashMap<String, Object> map = jsonToHashMap(object);
                    responseArray.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            afterSuccess(responseArray);
        }
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

    protected RequestUpdateListener requestUpdateListener;

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
