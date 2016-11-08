package com.mdsgpp.cidadedemocratica.requester;

import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class UserRequestResponseHandler extends RequestResponseHandler implements Comparator<User> {

    private final int success = 200;

    EntityContainer<User> usersContainer = EntityContainer.getInstance(User.class);

    private final String userNameKey = "nome";
    private final String userDescriptionKey = "descricao";
    private final String userProposalCountKey = "topicos_count";
    private final String userIdKey = "id";
    private final String userRelevanceKey = "relevancia";

    public static int nextPageToRequest = 1;
    public static final String usersEndpointUrl = "http://cidadedemocraticaapi.herokuapp.com/api/v0/users";

    @Override
    public void onSuccess(int statusCode, Map<String, List<String>> headers, JSONArray response) {
        if(statusCode == success){
            ArrayList<User> users = new ArrayList<User>();

            for(int i = 0; i < response.length(); ++i){
                try{
                    JSONObject userJson = response.getJSONObject(i);
                    String userName = userJson.getString(userNameKey);
                    String userDescription = userJson.getString(userDescriptionKey);
                    long userProposals = userJson.getLong(userProposalCountKey);
                    long userId = userJson.getLong(userIdKey);
                    long userRelevance = userJson.getLong(userRelevanceKey);

                    User user = new User(userName, userDescription, userProposals, userId, userRelevance);
                    users.add(user);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            Collections.sort(users, this);
            users.removeAll(usersContainer.getAll());
            usersContainer.addAll(users);
            afterSuccess(users);
        }
    }

    @Override
    public void onFailure(int statusCode, Map<String, List<String>> headers, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, errorResponse);
        afterError(String.valueOf(statusCode));
    }

    @Override
    public int compare(User u1, User u2) {
        return u1.compareTo(u2);
    }
}
