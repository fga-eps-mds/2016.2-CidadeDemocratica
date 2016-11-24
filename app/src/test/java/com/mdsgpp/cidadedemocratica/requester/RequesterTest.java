package com.mdsgpp.cidadedemocratica.requester;

import android.test.ApplicationTestCase;
import android.app.Application;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    String userToken = null;
    private AuthenticateRequestResponseHandler authenticationHandler;

    public RequesterTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        response = null;
        errorMessage = null;
    }

    @Test
    public void testGetAsync() throws InterruptedException {
        RequestResponseHandler handler = new RequestResponseHandler();
        handler.setRequestUpdateListener(this);
        requester = new Requester("https://google.com", handler);
        requester.async(Requester.RequestMethod.GET);
        signal.await(500, TimeUnit.SECONDS);
        assertNotNull(response);
    }

    @Test
    public void testGetSync() {
        RequestResponseHandler handler = new RequestResponseHandler();
        handler.setRequestUpdateListener(this);
        requester = new Requester("https://google.com", handler);
        requester.sync(Requester.RequestMethod.GET);
        assertNotNull(response);
    }

    @Test
    public void testGetInvalid() throws InterruptedException {
        RequestResponseHandler handler = new RequestResponseHandler();
        handler.setRequestUpdateListener(this);

        Requester requester = new Requester("http://www.naoexiste.com", handler);
        requester.async(Requester.RequestMethod.GET);
        signal.await();
        assertNotNull(errorMessage);
    }

    @Test
    public void testPost() {
        RequestResponseHandler handler = new RequestResponseHandler();
        handler.setRequestUpdateListener(this);

        Requester r = new Requester("https://posttestserver.com/post.php", handler);
        r.setParameter("test", "done");

        r.sync(Requester.RequestMethod.POST);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        if (handler == authenticationHandler) {
            userToken = (String) response;
        } else {
            this.response = response;
            signal.countDown();
        }
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
    public void testSetParameters() {

        requester = new Requester("", new RequestResponseHandler());
        assertEquals(requester.getParameters(), "");

        requester.setParameter("id", String.valueOf(new Integer(10)));
        assertEquals(requester.getParameters(), "id=10");
    }

    @Test
    public void testGetToken() {
        String token = "3baisu288r9";
        Requester.setUserToken(token);

        assertEquals(token, Requester.getUserToken());
    }

    @Test
    public void testSetHeader() {

        requester = new Requester("", new RequestResponseHandler());
        assertEquals(requester.getParameters(), "");

        HashMap<String, String> headers = new HashMap<>();

        assertTrue(requester.getHeaders().isEmpty());

        String k1 = "k1";
        String k2 = "k2";
        String k3 = "k3";

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";

        headers.put(k1, v1);
        headers.put(k2, v2);
        headers.put(k3, v3);

        requester.setHeader(k1, v1);
        requester.setHeader(k2, v2);
        requester.setHeader(k3, v3);

        assertEquals(headers, requester.getHeaders());
    }
}
