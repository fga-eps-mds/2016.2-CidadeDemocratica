package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.UserRequestResponseHandler;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {

    private ListView usersListView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        if (DataContainer.getInstance().getUsers().size() == 0) {
            pullUsersData();
        } else {
            loadUsersList();
        }
    }

    private void loadUsersList(){
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

    private void pullUsersData() {
        progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_users));
        UserRequestResponseHandler userRequestResponseHandler = new UserRequestResponseHandler();
        setDataUpdateListener(userRequestResponseHandler);
        Requester requester = new Requester(UserRequestResponseHandler.tagsUsersEndpointUrl, userRequestResponseHandler);
        requester.request(Requester.RequestType.GET);
    }

    private void createToast(String message){
        FeedbackManager.createToast(this, message);
    }

    private void setDataUpdateListener(UserRequestResponseHandler userRequestResponseHandler){

        userRequestResponseHandler.setRequestUpdateListener(new RequestUpdateListener() {
            @Override
            public void afterSuccess() {
                progressDialog.dismiss();
                loadUsersList();
                createToast(getString(R.string.message_success_load_users));
            }

            @Override
            public void afterError(String message) {
                progressDialog.dismiss();
                createToast(message);
            }
        });

    }
}
