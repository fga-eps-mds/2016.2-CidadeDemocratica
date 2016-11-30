package com.mdsgpp.cidadedemocratica.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.controller.FeedbackManager;
import com.mdsgpp.cidadedemocratica.controller.ProposalListAdapter;
import com.mdsgpp.cidadedemocratica.controller.TagginsList;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProposalsNearStateListFragment extends Fragment implements RequestUpdateListener {


    private ListProposalFragment.OnFragmentInteractionListener mListener;
    private ListView proposalListView;
    private TextView stateTextView;
    private Spinner spinnerState;
    private ProgressDialog progressDialog;
    private EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

    private String stateName;
    private final String proposalStateKey = "federal_unity_code";

    private ArrayList<String> loadedStates = new ArrayList<>();

    public ArrayList<Proposal> proposals;

    ProposalListAdapter proposalAdapter;

    int preLast = 0;
    private ProposalRequestResponseHandler proposalLocationRequestResponseHandler;

    public ProposalsNearStateListFragment() {
        // Required empty public constructor
    }

    public static ProposalsNearStateListFragment newInstance(String stateName, ArrayList<Proposal> proposals) {

        ProposalsNearStateListFragment fragment = new ProposalsNearStateListFragment();

        fragment.stateName = stateName;
        fragment.proposals = proposals;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proposals_near_state_list, container, false);

        stateTextView = (TextView) view.findViewById(R.id.stateName);
        stateTextView.setText(getString(R.string.proposals_from_state) + " ");

        spinnerState = (Spinner) view.findViewById(R.id.spinnerState);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String stateSelected = adapterView.getItemAtPosition(position).toString();
                if (stateSelected!=stateName){
                    stateName = stateSelected;
                    stateTextView.setText(getString(R.string.proposals_from_state) + " ");
                    requestProposalsFromState();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proposalAdapter = new ProposalListAdapter(getContext().getApplicationContext(), this.proposals);

        proposalAdapter.updateData(proposals);
        proposalListView = (ListView) view.findViewById(R.id.proposalsListId);

        proposalListView.setAdapter(proposalAdapter);

        proposalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Proposal proposalClicked = (Proposal)proposalAdapter.getItem(i);
                Long id = proposalClicked.getId();
                String proposalName = proposalClicked.getTitle();

                Intent intent = new Intent(getActivity().getApplicationContext(),TagginsList.class);
                intent.putExtra("proposalId", id);
                startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListProposalFragment.OnFragmentInteractionListener) {
            mListener = (ListProposalFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void requestProposalsFromState(){
        if (!loadedStates.contains(stateName)) {
            progressDialog = FeedbackManager.createProgressDialog(getActivity(), "Carregando propostas de " + stateName);

            proposalLocationRequestResponseHandler = new ProposalRequestResponseHandler();
            proposalLocationRequestResponseHandler.setRequestUpdateListener(this);

            Requester requester = new Requester(ProposalRequestResponseHandler.proposalsEndpointUrl, proposalLocationRequestResponseHandler);
            requester.setParameter(proposalStateKey, stateName);

            requester.async(Requester.RequestMethod.GET);
            loadedStates.add(stateName);

        } else {
            afterSuccess(null, loadedProposalsFromState());
        }
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        final Object finalResponse = response;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                proposals = (ArrayList<Proposal>)finalResponse;
                proposalAdapter.updateData(proposals);
            }
        });
        progressDialog.dismiss();
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {

    }

    private ArrayList<Proposal> loadedProposalsFromState() {
        try {
            ArrayList<Proposal> proposals = proposalsContainer.getForField("stateAbbrev", stateName);
            return proposals;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
