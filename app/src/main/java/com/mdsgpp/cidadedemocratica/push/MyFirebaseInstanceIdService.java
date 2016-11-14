package com.mdsgpp.cidadedemocratica.push;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mdsgpp.cidadedemocratica.requester.AuthenticateRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;

/**
 * Created by andreanmasiro on 10/11/16.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService implements RequestUpdateListener {


    private static FirebaseInstanceId firebaseInstanceId = FirebaseInstanceId.getInstance();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = firebaseInstanceId.getToken();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

        AuthenticateRequestResponseHandler handler = new AuthenticateRequestResponseHandler();
        handler.setRequestUpdateListener(this);
        Requester requester = new Requester(AuthenticateRequestResponseHandler.authenticateEndpointUrl, handler);

        requester.setParameter("pushId", refreshedToken);
        requester.async(Requester.RequestMethod.POST);
        System.out.println("Refreshed Token:");
        System.out.println(refreshedToken);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}
