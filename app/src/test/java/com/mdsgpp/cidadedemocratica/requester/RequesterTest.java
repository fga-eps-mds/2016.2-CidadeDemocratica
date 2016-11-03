package com.mdsgpp.cidadedemocratica.requester;

import android.test.ApplicationTestCase;
import android.app.Application;

import org.json.JSONArray;
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
    String url = "http://cidadedemocraticaapi.herokuapp.com/api/v0/tags/1";
    String errorMessage = null;

    public RequesterTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        requester = new Requester(url, responseHandler);
        responseHandler.setRequestUpdateListener(this);
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        response = null;
    }

    @Test
    public void testGet() throws InterruptedException {
        requester.setMethod(Requester.RequestMethod.GET);
        requester.getAsync();
        signal.await();
        assertNotNull(response);
    }

    @Test
    public void testGetInvalid() throws InterruptedException {
        RequestResponseHandler handler = new RequestResponseHandler();
        handler.setRequestUpdateListener(this);

        Requester requester = new Requester("http://www.naoexiste.com", handler);
        requester.setMethod(Requester.RequestMethod.GET);
        requester.getAsync();
        signal.await();
        assertNotNull(errorMessage);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        this.response = response;
        signal.countDown();
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        errorMessage = message;
        signal.countDown();
    }

    @Test
    public void testGetProtocolDescription() {
        assertEquals(Requester.getProtocolDescription(Requester.ValidProtocol.FTP), "ftp");
        assertEquals(Requester.getProtocolDescription(Requester.ValidProtocol.HTTP), "http");
        assertEquals(Requester.getProtocolDescription(Requester.ValidProtocol.HTTPS), "https");
    }

    @Test
    public void testGetRequestMethodDescription() {
        assertEquals(Requester.getRequestMethodDescription(Requester.RequestMethod.GET), "GET");
        assertEquals(Requester.getRequestMethodDescription(Requester.RequestMethod.POST), "POST");
        assertEquals(Requester.getRequestMethodDescription(Requester.RequestMethod.PATCH), "PATCH");
        assertEquals(Requester.getRequestMethodDescription(Requester.RequestMethod.DELETE), "DELETE");
    }

    @Test
    public void testGetMethod() {
        requester.setMethod(Requester.RequestMethod.GET);
        assertEquals(requester.getMethod(), Requester.RequestMethod.GET);

        requester.setMethod(Requester.RequestMethod.POST);
        assertEquals(requester.getMethod(), Requester.RequestMethod.POST);
    }

    @Test
    public void testSetParameters() {

        assertEquals(requester.getUrlWithParameters(), url);

        requester.setParameter("id", String.valueOf(new Integer(10)));
        assertEquals(requester.getUrlWithParameters(), url + "?id=10");
    }

    @Test
    public void testGetRequestResponseHandler() {
        assertEquals(this, responseHandler.getRequestUpdateListener());
    }
}
