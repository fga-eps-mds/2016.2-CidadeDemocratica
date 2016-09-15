package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class ProposalRequestResponseHandler extends JsonHttpResponseHandler {

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        DataContainer dataContainer = DataContainer.getInstance();
        if(statusCode == 200)
        {
            for(int i=0; i<response.length();++i)
            {

                try {
                    JSONObject proposalJson = response.getJSONObject(i);
                    String proposalTitle = proposalJson.getString("titulo");
                    String proposalID = proposalJson.getString("id");
                    String proposalDescription = proposalJson.getString("descricao");
                    String proposalRelevance = proposalJson.getString("relevancia");

                    Proposal proposal = new Proposal(proposalTitle, proposalID, proposalDescription, proposalRelevance);
                    dataContainer.addProposal(proposal);


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
