package com.mdsgpp.cidadedemocratica.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    TextView userName;
    ListView userProposals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = (TextView) findViewById(R.id.userName);
        userName.setText(getUserName());

        userProposals = (ListView) findViewById(R.id.proposalsList);
        ArrayList<Proposal> proposalsFromUsers = new ArrayList<Proposal>();
        if(getProposalList().size() > 0){
            proposalsFromUsers = getProposalList();
        }
        else {
            Toast.makeText(getApplicationContext(),"Este usuário não possui propostas",Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<Proposal> proposalAdapter= new ArrayAdapter<Proposal>(this,android.R.layout.simple_list_item_1,android.R.id.text1,proposalsFromUsers);
        userProposals.setAdapter(proposalAdapter);

    }

    public String getUserName(){
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            String userName = extra.getString("UserName");
            return userName;
        }
        return null;
    }

    public ArrayList<Proposal> getProposalList(){
        DataContainer dataContainer = DataContainer.getInstance();
        Bundle extra = getIntent().getExtras();
        String userId = extra.getString("UserId");
        if(userId != null ){
            Long idUser = Long.parseLong(userId);
            User userSelected = dataContainer.getUserForId(idUser);
            return userSelected.getProposals();
        }
        return null;

    }
}
