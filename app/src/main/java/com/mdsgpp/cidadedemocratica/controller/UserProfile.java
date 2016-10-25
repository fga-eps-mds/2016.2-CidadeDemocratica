package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TaggingsRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.UserRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity implements ListProposalFragment.OnFragmentInteractionListener, RequestUpdateListener {

    User user;
    TextView userName;
    TextView descriptionTextView;
    TextView relevanceTextView;
    ListView userProposals;

    private ProgressDialog progressDialog;
    private ProposalRequestResponseHandler proposalRequestResponseHandler;
    private static String userIdParameterKey = "user_id";
    private UserRequestResponseHandler userRequestResponseHandler;

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



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void loadProposalsList(ArrayList<Proposal> proposals) {

        if(proposals == null) {
            Toast.makeText(getApplicationContext(),"Usuário não possui propostas", Toast.LENGTH_SHORT);
        }

        ListProposalFragment proposalFragment = ListProposalFragment.newInstance(proposals);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, proposalFragment).commit();
    }

    private void pullUsersProposals() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_proposal_detail));
        }

        proposalRequestResponseHandler = new ProposalRequestResponseHandler();
        proposalRequestResponseHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(ProposalRequestResponseHandler.proposalsEndpointUrl, proposalRequestResponseHandler);
        requester.setParameter(userIdParameterKey, String.valueOf(user.getId()));
        requester.request(Requester.RequestType.GET);
    }

    @Override
    public void afterSuccess(JsonHttpResponseHandler handler) {
        ArrayList<Proposal> proposals = DataContainer.getInstance().getProposalsForUserId(user.getId());
        loadProposalsList(proposals);
    }

    @Override
    public void afterError(JsonHttpResponseHandler handler, String message) {

    }
}
