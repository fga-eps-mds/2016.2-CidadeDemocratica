package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/17/16.
 */
public class TaggingsRequestResponseHandler extends JsonHttpResponseHandler {

    private DataContainer dataContainer = DataContainer.getInstance();
    private final String taggingTagIdKey = "tag_id";
    private final String taggingTaggableIdKey = "taggable_id";
    private final String taggingTaggableTypeKey = "taggable_type";

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if (statusCode == 200) {

            HashMap<Long, ArrayList<Tag>> taggings = new HashMap<Long, ArrayList<Tag>>();
            for (int i = 0; i < response.length(); ++i) {
                try {

                    JSONObject taggingJson = response.getJSONObject(i);
                    String taggableType = taggingJson.getString(taggingTaggableTypeKey);

                    if (taggableType == "Topico") {

                        long tagId = taggingJson.getLong(taggingTagIdKey);
                        long proposalId = taggingJson.getLong(taggingTaggableIdKey);

                        Tag tag = dataContainer.getTagForId(tagId);

                        if (tag != null) {

                            ArrayList<Tag> tags;
                            if (taggings.containsKey(proposalId)) {
                                tags = taggings.get(proposalId);
                            } else {
                                tags = new ArrayList<Tag>();
                            }
                            tags.add(tag);
                            taggings.put(proposalId, tags);
                        } else { /* Tag does not exist */ }

                    } else { /* Taggable is not a topic */ }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Set<Long> keySet = taggings.keySet();
            for (Long key : keySet) {

                Proposal proposal = dataContainer.getProposalForId(key);
                if (proposal != null) {
                    proposal.setTags(taggings.get(key));
                } else { /* Proposal does not exist */ }
            }
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
