package com.mdsgpp.cidadedemocratica.view;


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

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.controller.ProposalListAdapter;
import com.mdsgpp.cidadedemocratica.controller.TagginsList;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.Requester;

import java.util.ArrayList;


public class ListProposalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView proposalListView;

    private ArrayList<Proposal> proposals;

    public ListProposalFragment() {
        // Required empty public constructor
    }

    public static ListProposalFragment newInstance(ArrayList<Proposal> proposals) {
        ListProposalFragment fragment = new ListProposalFragment();

        Bundle args = new Bundle();
        fragment.proposals = proposals;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        proposalListView = (ListView) view.findViewById(R.id.proposalsListId);

        final ProposalListAdapter proposalAdapter = new ProposalListAdapter(getContext().getApplicationContext(), this.proposals);
        proposalListView.setAdapter(proposalAdapter);

        proposalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Proposal proposalClicked = (Proposal)proposalAdapter.getItem(i);
                Long id = proposalClicked.getId();
                String proposalName = proposalClicked.getTitle();
                String proposalStringID = Long.toString(id);
                Intent intent = new Intent(getActivity().getApplicationContext(),TagginsList.class);
                intent.putExtra("ProposalId", proposalStringID);
                intent.putExtra("ProposalTitle",proposalName);
                startActivity(intent);

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public final static void pullProposalData() {
        Requester requester = new Requester("http://cidadedemocraticaapi.herokuapp.com/api/v0/proposals", new ProposalRequestResponseHandler());
        requester.request(Requester.RequestType.GET);
        requester = null;
    }

    private ArrayList<Proposal> getProposalList() {
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getProposals();
    }
}
