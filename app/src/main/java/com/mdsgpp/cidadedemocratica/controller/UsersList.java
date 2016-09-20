package com.mdsgpp.cidadedemocratica.controller;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.UserRequestResponseHandler;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        ListView usersListView = (ListView) findViewById(R.id.usersList);

    }

    public final static void pullUsersData(){
        Requester requester = new Requester("http://cidadedemocraticaapi.herokuapp.com/api/v0/users", new UserRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
    }

    private ArrayList<User> getUsersList(){
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getUsers();
    }
}
