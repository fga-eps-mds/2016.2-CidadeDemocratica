package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class TagRequestResponseHandler extends JsonHttpResponseHandler {

    DataContainer dataContainer = DataContainer.getInstance();

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

        if (statusCode == 200) {
            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (int i = 0; i < response.length(); ++i) {
                try {
                    JSONObject tagJson = response.getJSONObject(i);
                    String tagName = tagJson.getString("name");

                    Tag tag = new Tag(tagName, 0);
                    tags.add(tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            dataContainer.setTags(tags);
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
