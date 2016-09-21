package com.mdsgpp.cidadedemocratica.controller;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.UserRequestResponseHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.jar.Attributes;

public class UsersList extends AppCompatActivity {

    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        usersListView = (ListView) findViewById(R.id.userList);

        ArrayList<User> usersList = getUsersList();
        final UserListAdapter userAdapter = new UserListAdapter(this, usersList);
        usersListView.setAdapter(userAdapter);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                User userClicked = (User)userAdapter.getItem(position);
                long id = userClicked.getId();
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                intent.putExtra("userId", id);
                startActivity(intent);
            }
        });

    }

    private ArrayList<User> getUsersList(){
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getUsers();
    }
}
