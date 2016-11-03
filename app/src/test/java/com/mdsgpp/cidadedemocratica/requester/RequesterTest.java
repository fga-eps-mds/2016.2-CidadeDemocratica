package com.mdsgpp.cidadedemocratica.requester;

import android.provider.Settings;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.app.Application;

import com.loopj.android.http.JsonHttpResponseHandler;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * Created by andreanmasiro on 01/11/16.
 */

public class RequesterTest extends ApplicationTestCase<Application> implements RequestUpdateListener {

    Requester requester;
    Object response = null;
    RequestResponseHandler responseHandler = new RequestResponseHandler();
    CountDownLatch signal = null;

    public RequesterTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        requester = new Requester("http://cidadedemocraticaapi.herokuapp.com/api/v0/tags/1", responseHandler);
        responseHandler.setRequestUpdateListener(this);
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        response = null;
    }

    @Test
    public void testGet() throws IOException, JSONException, InterruptedException {
        requester.setMethod(Requester.RequestMethod.GET);
        requester.getAsync();
        signal.await();
        assertNotNull(response);
    }

    @Override
    public void afterSuccess(JsonHttpResponseHandler handler, Object response) {
        this.response = response;
        signal.countDown();
    }

    @Override
    public void afterError(JsonHttpResponseHandler handler, String message) {

    }
}
