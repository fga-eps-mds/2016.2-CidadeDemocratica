package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by andreanmasiro on 24/11/16.
 */

public class RequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    RequestResponseHandler handler = new RequestResponseHandler();
    String response = null;

    @Override
    protected void setUp() throws Exception {
        handler.setRequestUpdateListener(this);
    }

    @Test
    public void testOnSuccessString() {
        String response = "response";
        handler.onSuccess(200, null, response);
        assertEquals(response, this.response);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        this.response = (String) response;
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}
