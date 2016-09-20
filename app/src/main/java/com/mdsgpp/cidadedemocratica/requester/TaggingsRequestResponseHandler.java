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

    HashMap<Long, ArrayList<Tag>> proposalTaggings = new HashMap<Long, ArrayList<Tag>>();
    HashMap<Long, ArrayList<Proposal>> tagTaggings = new HashMap<Long, ArrayList<Proposal>>();

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

        if (statusCode == 200) {

            for (int i = 0; i < response.length(); ++i) {
                try {

                    JSONObject taggingJson = response.getJSONObject(i);
                    String taggableType = taggingJson.getString(taggingTaggableTypeKey);

                    if (taggableType.equals("Topico")) {

                        long tagId = taggingJson.getLong(taggingTagIdKey);
                        long proposalId = taggingJson.getLong(taggingTaggableIdKey);

                        Tag tag = dataContainer.getTagForId(tagId);
                        Proposal proposal = dataContainer.getProposalForId(proposalId);

                        if (tag != null && proposal != null) {

                            addTagForProposalId(proposalId, tag);
                            addProposalForTagId(tagId, proposal);

                        } else { /* Tag or Proposal does not exist */ }

                    } else { /* Taggable is not a topic */ }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            setProposalsTags();
            setTagsProposals();
        }
    }

    private void setTagsProposals() {

        Set<Long> tagIdKeySet = tagTaggings.keySet();
        for (Long key : tagIdKeySet) {
            Tag tag = dataContainer.getTagForId(key);
            tag.setProposals(tagTaggings.get(key));
        }
    }

    private void setProposalsTags() {

        Set<Long> proposalIdKeySet = proposalTaggings.keySet();
        for (Long key : proposalIdKeySet) {
            Proposal proposal = dataContainer.getProposalForId(key);
            proposal.setTags(proposalTaggings.get(key));
        }
    }

    private void addProposalForTagId(long tagId, Proposal proposal) {

        ArrayList<Proposal> proposals;
        if (tagTaggings.containsKey(tagId)) {
            proposals = tagTaggings.get(tagId);
        } else {
            proposals = new ArrayList<Proposal>();
        }

        proposals.add(proposal);

        tagTaggings.put(tagId, proposals);
    }

    private void addTagForProposalId(long proposalId, Tag tag) {

        ArrayList<Tag> tags;
        if (proposalTaggings.containsKey(proposalId)) {
            tags = proposalTaggings.get(proposalId);
        } else {
            tags = new ArrayList<Tag>();
        }

        tags.add(tag);

        proposalTaggings.put(proposalId, tags);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
