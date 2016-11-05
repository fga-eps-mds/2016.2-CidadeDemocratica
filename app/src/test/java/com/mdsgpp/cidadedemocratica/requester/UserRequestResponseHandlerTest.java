package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 04/11/16.
 */

public class UserRequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    int id = 99;
    String name = "Henrique Parra Parra Filho";
    UserRequestResponseHandler handler = new UserRequestResponseHandler();
    String errorMessage = null;
    User user = null;

    @Override
    protected void setUp() throws Exception {
        handler.setRequestUpdateListener(this);
    }

    @Override
    protected void tearDown() throws Exception {
        user = null;
        errorMessage = null;
    }

    @Test
    public void testOnSuccess() throws JSONException {
        JSONArray userJson = new JSONArray("[{\"nome\":\"" + name + "\",\"descricao\":\"Cada vez mais jundiaiense e acreditando no poder do cidadão para transformar as coisas.\",\"sexo\":\"m\",\"aniversario\":\"1989-08-16T00:00:00.000Z\",\"id\":" + id + ",\"state\":\"active\",\"type\":\"Cidadao\",\"topicos_count\":29,\"comments_count\":1168,\"adesoes_count\":489,\"relevancia\":406500,\"inspirations_count\":14,\"city_name\":\"Jundiaí\",\"state_name\":\"São Paulo\",\"state_abrev\":\"SP\"}]");
        handler.onSuccess(200, null, userJson);

        assertEquals(name, user.getName());
        assertEquals(id, user.getId());
    }

    @Test
    public void testOnFailure() {
        int statusCode = 500;
        handler.onFailure(statusCode, null, null);

        assertEquals(String.valueOf(statusCode), errorMessage);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        ArrayList<User> users = (ArrayList<User>) response;
        user = users.get(0);
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        errorMessage = message;
    }
}
