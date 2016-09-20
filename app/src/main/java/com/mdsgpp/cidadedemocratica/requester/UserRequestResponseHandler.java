package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class UserRequestResponseHandler extends JsonHttpResponseHandler {
    private final int success = 200;

    DataContainer dataContainer = DataContainer.getInstance();

    private final String userNameKey = "nome";
    private final String userProposalCountKey = "topicos_count";
    private final String userIdKey = "id";
    private final String userRelevanceKey = "relevancia";

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        if(statusCode == success){
            ArrayList<User> users = new ArrayList<User>();

            for(int i = 0; i < response.length(); ++i){
                try{
                    JSONObject userJson = response.getJSONObject(i);
                    String userName = userJson.getString(userNameKey);
                    long userProposals = userJson.getLong(userProposalCountKey);
                    long userId = userJson.getLong(userIdKey);
                    long userRelevace = userJson.getLong(userRelevanceKey);

                    User user = new User(userName,userProposals,userId,userRelevace);
                    users.add(user);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
            dataContainer.setUsers(users);

        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
