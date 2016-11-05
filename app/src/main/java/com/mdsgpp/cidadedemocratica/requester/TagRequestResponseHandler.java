package com.mdsgpp.cidadedemocratica.requester;

import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class TagRequestResponseHandler extends RequestResponseHandler implements Comparator<Tag> {

    DataContainer dataContainer = DataContainer.getInstance();

    private final String tagNameKey = "name";
    private final String tagIdKey = "id";
    private final String tagRelevanceKey = "relevancia";

    public static int nextPageToRequest = 1;

    public static final String tagsEndpointUrl = "http://cidadedemocraticaapi.herokuapp.com/api/v0/tags";

    @Override
    public void onSuccess(int statusCode, Map<String, List<String>> headers, JSONArray response) {

        if (statusCode == 200) {
            ArrayList<Tag> tags = new ArrayList<>();
            for (int i = 0; i < response.length(); ++i) {
                try {
                    JSONObject tagJson = response.getJSONObject(i);
                    String tagName = tagJson.getString(tagNameKey);
                    long tagId = tagJson.getLong(tagIdKey);
                    long tagRelevance = tagJson.getLong(tagRelevanceKey);

                    Tag tag = new Tag(tagId, tagName, 0, tagRelevance);
                    tags.add(tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Collections.sort(tags, this);
            tags.removeAll(dataContainer.getTags());
            dataContainer.addTags(tags);
            afterSuccess(tags);
        }

    }

    @Override
    public void onFailure(int statusCode, Map<String, List<String>> headers, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, errorResponse);
        afterError(String.valueOf(statusCode));
    }

    @Override
    public int compare(Tag t1, Tag t2) {
        return t1.compareTo(t2);
    }
}
