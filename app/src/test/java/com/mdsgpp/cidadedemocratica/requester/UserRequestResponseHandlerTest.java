package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.User;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 04/11/16.
 */

public class UserRequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    int id = 0;
    String name = "";
    UserRequestResponseHandler handler = new UserRequestResponseHandler();
    ArrayList<User> response = null;
    String errorMessage = null;

    @Override
    protected void setUp() throws Exception {
        handler.setRequestUpdateListener(this);
    }

    @Override
    protected void tearDown() throws Exception {
        response = null;
        errorMessage = null;
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}
