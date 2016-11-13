package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

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

        handler.onSuccess(200, null, tokenJson);
        assertEquals(token, response);
    }

    @Test
    public void testOnFailure() {
        int statusCode = 500;
        handler.onFailure(statusCode, null, null);
        assertEquals(String.valueOf(statusCode), errorMessage);
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
