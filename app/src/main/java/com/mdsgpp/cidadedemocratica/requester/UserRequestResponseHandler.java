package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class UserRequestResponseHandler extends JsonHttpResponseHandler {


    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
