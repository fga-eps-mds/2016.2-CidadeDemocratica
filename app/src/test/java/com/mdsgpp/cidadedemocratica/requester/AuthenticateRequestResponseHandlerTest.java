package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreanmasiro on 13/11/16.
 */

public class AuthenticateRequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    AuthenticateRequestResponseHandler handler;
    String response;
    String token = "3bfgo98slc";
    String errorMessage = null;

    @Override
    protected void setUp() throws Exception {
        handler = new AuthenticateRequestResponseHandler();
        handler.setRequestUpdateListener(this);
    }

    @Test
    public void testOnSuccess() throws JSONException {

        JSONObject tokenJson = new JSONObject("{error: null, token: " + token + "}");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Method", new ArrayList<String>(Arrays.asList("GET")));

        handler.onSuccess(200, headers, tokenJson);
        assertEquals(token, response);
    }

    @Test
    public void testOnFailure() {
        int statusCode = 500;
        handler.onFailure(statusCode, null, null);
        assertEquals(String.valueOf(statusCode), errorMessage);
    }

    @Test (expected = JSONException.class)
    public void testOnSuccessException() {
        JSONObject tokenJson = null;
        try {
            tokenJson = new JSONObject("{error: null" + "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Method", new ArrayList<String>(Arrays.asList("GET")));

        handler.onSuccess(200, headers, tokenJson);
    }

    @Test
    public void testOnSuccessPost() throws JSONException {

        String response = "response";
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Method", new ArrayList<>(Arrays.asList("POST")));


        handler.onSuccess(200, headers, response);
        assertEquals(this.response, response);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        this.response = (String) response;
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        errorMessage = message;
    }
}
