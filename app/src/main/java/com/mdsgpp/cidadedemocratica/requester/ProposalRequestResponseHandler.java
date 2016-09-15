package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class ProposalRequestResponseHandler extends JsonHttpResponseHandler {

    DataContainer dataContainer = DataContainer.getInstance();
    private final String jsonProposalType = "Proposta";

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if (statusCode == 200) {

            ArrayList<Proposal> proposals = new ArrayList<Proposal>();

            for (int i = 0; i < response.length(); ++i) {

                try {
                    JSONObject topicJson = response.getJSONObject(i);
                    String topicType = topicJson.getString("type");

                    if (topicType == jsonProposalType) {

                        String jsonTitleKey = "titulo";
                        String jsonContentKey = "descricao";

                        String title = topicJson.getString(jsonTitleKey);
                        String content = topicJson.getString(jsonContentKey);

                        Proposal proposal = new Proposal(title, content, null);

                        proposals.add(proposal);

                    } else { // Not a proposal

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            
            dataContainer.addProposals(proposals);
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
