package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by andreanmasiro on 24/11/16.
 */

public class RequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    RequestResponseHandler handler = new RequestResponseHandler();
    String responseString = null;
    JSONObject responseJSONObject = null;
    JSONArray responseJSONArray = null;

    @Override
    protected void setUp() throws Exception {
        handler.setRequestUpdateListener(this);
    }

    @Test
    public void testOnSuccessString() {
        String response = "responseString";
        handler.onSuccess(200, null, response);
        assertEquals(response, this.responseString);
    }

    @Test
    public void testOnSuccessJObject() {
        JSONObject response = null;
        try {
            response = new JSONObject("{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.onSuccess(200, null, response);
        assertEquals(response, this.responseJSONObject);
    }

    @Test
    public void testOnSuccessJArray() {
        JSONArray response = null;
        try {
            response = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.onSuccess(200, null, response);
        assertEquals(response, this.responseJSONArray);
    }

    @Test
    public void testRequestUpdateListener() {
        RequestResponseHandler handler = new RequestResponseHandler();
        assertNull(handler.getRequestUpdateListener());

        handler.setRequestUpdateListener(this);

        assertEquals(this, handler.getRequestUpdateListener());
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        if (response instanceof String) {
            this.responseString = (String) response;
        } else if (response instanceof JSONObject) {
            this.responseJSONObject = (JSONObject) response;
        } else if (response instanceof JSONArray) {
            this.responseJSONArray = (JSONArray) response;
        }

    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}
