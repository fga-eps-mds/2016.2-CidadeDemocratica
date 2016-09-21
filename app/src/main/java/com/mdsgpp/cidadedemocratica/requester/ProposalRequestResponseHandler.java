package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class ProposalRequestResponseHandler extends JsonHttpResponseHandler {

    DataContainer dataContainer = DataContainer.getInstance();
    private final String jsonProposalType = "Proposta";
    private final String proposalTitleKey = "titulo";
    private final String proposalContentKey = "descricao";
    private final String proposalIdKey = "id";
    private final String proposalRelevanceKey = "relevancia";
    private final String proposalUserIdKey = "user_id";

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if (statusCode == 200) {

            ArrayList<Proposal> proposals = new ArrayList<Proposal>();

            for (int i = 0; i < response.length(); ++i) {

                try {
                    JSONObject topicJson = response.getJSONObject(i);
                    String topicType = topicJson.getString("titulo");

                    long id = topicJson.getLong(proposalIdKey);
                    String title = topicJson.getString(proposalTitleKey);
                    String content = topicJson.getString(proposalContentKey);
                    long relevance = topicJson.getLong(proposalRelevanceKey);
                    long userId = topicJson.getLong(proposalUserIdKey);


                    Proposal proposal = new Proposal(id, title, content, relevance, userId);

                    proposals.add(proposal);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Collections.sort(proposals, new Comparator<Proposal>() {
                @Override
                public int compare(Proposal p1, Proposal p2) {
                    return p1.compareTo(p2);
                }
            });
            dataContainer.setProposals(proposals);
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}