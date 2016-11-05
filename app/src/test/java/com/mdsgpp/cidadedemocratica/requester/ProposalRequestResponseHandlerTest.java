package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Proposal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andreanmasiro on 03/11/16.
 */

public class ProposalRequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    ProposalRequestResponseHandler handler;
    ArrayList<Proposal> response;
    Proposal proposal;
    int id = 2572;
    int userId = 6418;
    String errorMessage = null;

    @Override
    protected void setUp() throws Exception {
        handler = new ProposalRequestResponseHandler();
        handler.setRequestUpdateListener(this);
    }

    @Test
    public void testOnSuccess() throws JSONException {

        JSONArray proposalJson = new JSONArray("[{\"id\":" + id + ",\"user_id\":" + userId + ",\"titulo\":\"Palestra de conscientização  sobre o Lixo\",\"descricao\":\"Muitas pessoas no meu bairro só jogam o lixo na rua ,e por isso a ideia sobre a palestra assim os moradores poderiam se unir e se conscientizar. \",\"slug\":\"palestra-de-conscientizacao-sobre-o-lixo\",\"comments_count\":2,\"adesoes_count\":8,\"relevancia\":2500,\"seguidores_count\":3,\"competition_id\":2,\"site\":null,\"city_name\":\"Sorocaba\",\"state_name\":\"São Paulo\",\"state_abrev\":\"SP\"}]");

        handler.onSuccess(200, null, proposalJson);
        assertEquals(proposal.getId(), id);
        assertEquals(proposal.getUserId(), userId);
    }

    @Test
    public void testOnFailure() {
        int statusCode = 500;
        handler.onFailure(statusCode, null, null);
        assertEquals(String.valueOf(statusCode), errorMessage);
    }

    @Test
    public void testCompareProposal() {
        Proposal p1 = new Proposal(0, "", "", 0, 0);
        Proposal p2 = new Proposal(0, "", "", 0, 0);

        assertEquals(p1.compareTo(p2), handler.compare(p1, p2));
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        this.response = (ArrayList<Proposal>) response;
        proposal = this.response.get(0);
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        errorMessage = message;
    }
}
