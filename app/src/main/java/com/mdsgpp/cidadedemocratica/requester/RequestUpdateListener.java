package com.mdsgpp.cidadedemocratica.requester;

/**
 * Created by luisresende on 18/10/16.
 */

public interface RequestUpdateListener {

    void afterSuccess(RequestResponseHandler handler, Object response);
    void afterError(RequestResponseHandler handler, String message);
}
