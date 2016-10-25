package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by luisresende on 18/10/16.
 */

public interface RequestUpdateListener {

    void afterSuccess(JsonHttpResponseHandler handler);
    void afterError(JsonHttpResponseHandler handler, String message);
}
