package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.UserRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity implements ListProposalFragment.OnFragmentInteractionListener, RequestUpdateListener {

    User user;
    TextView userName;
    TextView descriptionTextView;
    TextView relevanceTextView;
    ListView userProposals;
    private View header;

    private ProgressDialog progressDialog;
    private ProposalRequestResponseHandler proposalRequestResponseHandler;
    private static String userIdParameterKey = "user_id";
    private UserRequestResponseHandler userRequestResponseHandler;

    private static ArrayList<Long> loadedUserIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getInstanceViews();

        if (!loadedUserIds.contains(user.getId())) {
            pullUsersProposals();
        } else {
            loadProposalsList();
        }

    }

    private void getInstanceViews(){
        header = getLayoutInflater().inflate(R.layout.fragment_header_user_profile, null, false);

        EntityContainer<User> usersContainer = EntityContainer.getInstance(User.class);

        long userId = getIntent().getExtras().getLong("userId");
        user = usersContainer.getForId(userId);

        userName = (TextView) header.findViewById(R.id.nameTextView);
        userName.setText(user.getName());

        descriptionTextView = (TextView)header.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(user.getDescription());

        relevanceTextView = (TextView)header.findViewById(R.id.relevanceTextView);
        relevanceTextView.setText(String.valueOf(user.getRelevance()));
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void loadProposalsList() {


        ArrayList<Proposal> proposals = user.getProposals();
        if(proposals == null) {
            Toast.makeText(getApplicationContext(),"Usuário não possui propostas", Toast.LENGTH_SHORT);
        }

        ListProposalFragment proposalFragment = ListProposalFragment.newInstance(proposals, header);
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
        requester.async(Requester.RequestMethod.GET);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                loadProposalsList();
            }
        });

        ArrayList<Proposal> proposals = (ArrayList<Proposal>) response;
        user.setProposals(proposals);

        loadedUserIds.add(user.getId());
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }
}
