package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private RequestUpdateListener requestUpdateListener;

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
            ArrayList<Proposal> proposals = tagTaggings.get(key);
            Collections.sort(proposals, new Comparator<Proposal>() {
                @Override
                public int compare(Proposal p1, Proposal p2) {
                    return p1.compareTo(p2);
                }
            });
            tag.setProposals(proposals);
        }
    }

    private void setProposalsTags() {

        Set<Long> proposalIdKeySet = proposalTaggings.keySet();
        for (Long key : proposalIdKeySet) {
            Proposal proposal = dataContainer.getProposalForId(key);
            ArrayList<Tag> tags = proposalTaggings.get(key);
            Collections.sort(tags, new Comparator<Tag>() {
                @Override
                public int compare(Tag t1, Tag t2) {
                    return t1.compareTo(t2);
                }
            });
            proposal.setTags(tags);
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

    public RequestUpdateListener getRequestUpdateListener() {
        return requestUpdateListener;
    }

    public void setRequestUpdateListener(RequestUpdateListener requestUpdateListener) {
        this.requestUpdateListener = requestUpdateListener;
    }
}
