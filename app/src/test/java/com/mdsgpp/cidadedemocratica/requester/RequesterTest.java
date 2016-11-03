package com.mdsgpp.cidadedemocratica.requester;

import android.test.ApplicationTestCase;
import android.app.Application;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
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
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        this.response = response;
        signal.countDown();
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}
