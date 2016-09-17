package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/17/16.
 */
public class TaggingsRequestResponseHandler extends JsonHttpResponseHandler {

    private final String taggingTagIdKey = "tag_id";
    private final String taggingTaggableIdKey = "taggable_id";
    private final String taggingTaggableTypeKey = "taggable_type";

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if (statusCode == 200) {

            for (int i = 0; i < response.length(); ++i) {
                try {

                    JSONObject taggingJson = response.getJSONObject(i);
                    String taggableType = taggingJson.getString(taggingTaggableTypeKey);

                    if (taggableType == "Topico") {

                        long tagId = taggingJson.getLong(taggingTagIdKey);
                        long proposalId = taggingJson.getLong(taggingTaggableIdKey);

                        //Add tag reference to proposal object
                    } else { /* Taggable is not a topic */ }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
