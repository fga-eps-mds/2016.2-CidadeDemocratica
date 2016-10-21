package com.mdsgpp.cidadedemocratica.requester;

/**
 * Created by luisresende on 18/10/16.
 */

public interface RequestUpdateListener {

    void afterSuccess();
    void afterError(String message);
}
