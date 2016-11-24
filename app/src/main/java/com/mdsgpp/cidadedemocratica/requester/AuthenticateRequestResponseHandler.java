package com.mdsgpp.cidadedemocratica.requester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by andreanmasiro on 13/11/16.
 */

public class AuthenticateRequestResponseHandler extends RequestResponseHandler {

    public static String authenticateEndpointUrl = "http://cidadedemocraticaapi.herokuapp.com/api/v0/authenticate";

    @Override
    public void onSuccess(int statusCode, Map<String, List<String>> headers, JSONObject response) {

        if (statusCode == 200) {
            List<String> methods = headers.get("Method");
            String method = methods.get(0);

            if (method.equals("GET")) {
                String token = null;

                try {
                    token = response.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               afterSuccess(token);
            } else if (method.equals("POST")) {

                super.onSuccess(statusCode, headers, response);
            }
        }
    }

    @Override
    public void onFailure(int statusCode, Map<String, List<String>> headers, JSONArray errorResponse) {
        afterError(String.valueOf(statusCode));
    }
}
