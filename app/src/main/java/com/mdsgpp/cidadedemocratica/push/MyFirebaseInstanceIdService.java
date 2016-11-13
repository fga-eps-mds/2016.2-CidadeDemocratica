package com.mdsgpp.cidadedemocratica.push;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by andreanmasiro on 10/11/16.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    private static FirebaseInstanceId firebaseInstanceId = FirebaseInstanceId.getInstance();


    public static String getId() {
        return firebaseInstanceId.getId();
    }

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
        System.out.println(refreshedToken);
    }
}
