package com.mdsgpp.cidadedemocratica.requester;

import android.app.ProgressDialog;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class UserRequestResponseHandler extends JsonHttpResponseHandler {

    private final int success = 200;

    DataContainer dataContainer = DataContainer.getInstance();

    private final String userNameKey = "nome";
    private final String userDescriptionKey = "descricao";
    private final String userProposalCountKey = "topicos_count";
    private final String userIdKey = "id";
    private final String userRelevanceKey = "relevancia";

    public static final String tagsUsersEndpointUrl = "http://cidadedemocraticaapi.herokuapp.com/api/v0/users";

    private RequestUpdateListener requestUpdateListener;

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
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

            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    return u1.compareTo(u2);
                }
            });
            dataContainer.setUsers(users);
            requestUpdateListener.afterSuccess();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        requestUpdateListener.afterError(String.valueOf(statusCode));
    }

    public RequestUpdateListener getRequestUpdateListener() {
        return requestUpdateListener;
    }

    public void setRequestUpdateListener(RequestUpdateListener requestUpdateListener) {
        this.requestUpdateListener = requestUpdateListener;
    }
}
