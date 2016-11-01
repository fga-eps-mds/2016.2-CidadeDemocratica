package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 01/11/16.
 */

public class RequestResponseHandler extends JsonHttpResponseHandler {


    protected RequestUpdateListener requestUpdateListener;

    protected void afterSuccess(Object response) {
        if (requestUpdateListener != null) {
            requestUpdateListener.afterSuccess(this, response);
        } else { }
    }

    protected void afterError(String message) {
        if (requestUpdateListener != null) {
            requestUpdateListener.afterError(this, message);
        } else { }
    }
}
