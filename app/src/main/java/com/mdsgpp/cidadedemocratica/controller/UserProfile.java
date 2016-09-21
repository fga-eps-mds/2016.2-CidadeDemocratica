package com.mdsgpp.cidadedemocratica.controller;

import android.net.Uri;
import android.provider.ContactsContract;
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
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity implements ListProposalFragment.OnFragmentInteractionListener {

    User user;
    TextView userName;
    TextView descriptionTextView;
    TextView relevanceTextView;
    ListView userProposals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Bundle extras = getIntent().getExtras();
        DataContainer dataContainer = DataContainer.getInstance();
        long userId = extras.getLong("userId");
        user = dataContainer.getUserForId(userId);

        userName = (TextView) findViewById(R.id.nameTextView);
        userName.setText(user.getName());

        descriptionTextView = (TextView)findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(user.getDescription());

        relevanceTextView = (TextView)findViewById(R.id.relevanceTextView);
        relevanceTextView.setText(String.valueOf(user.getRelevance()));

        ArrayList<Proposal> proposals = getProposalList();
        if(proposals == null){
            Toast.makeText(getApplicationContext(),"Usuário não possui propostas", Toast.LENGTH_SHORT);
        }

        ListProposalFragment proposalFragment = ListProposalFragment.newInstance(proposals);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, proposalFragment).commit();

    }

    private ArrayList<Proposal> getProposalList() {
        if (user != null) {
            return user.getProposals();
        } else {
            return null;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
