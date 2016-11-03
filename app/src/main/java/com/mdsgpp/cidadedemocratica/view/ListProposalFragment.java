package com.mdsgpp.cidadedemocratica.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.controller.ProposalListAdapter;
import com.mdsgpp.cidadedemocratica.controller.ProposalsList;
import com.mdsgpp.cidadedemocratica.controller.TagginsList;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.persistence.DataUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.Requester;

import java.util.ArrayList;


public class ListProposalFragment extends Fragment implements DataUpdateListener {

    private OnFragmentInteractionListener mListener;
    private ListView proposalListView;

    public ArrayList<Proposal> proposals;

    ProposalListAdapter proposalAdapter;

    int preLast = 0;
    public ListProposalFragment() {
        // Required empty public constructor
    }

    public static ListProposalFragment newInstance(ArrayList<Proposal> proposals) {


        ListProposalFragment fragment = new ListProposalFragment();

        Bundle args = new Bundle();
        fragment.proposals = proposals;

        DataContainer.getInstance().setDataUpdateListener(fragment);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        proposalAdapter = new ProposalListAdapter(getContext().getApplicationContext(), this.proposals);

        proposalAdapter.updateData(proposals);
        proposalListView = (ListView) view.findViewById(R.id.proposalsListId);

        proposalAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                proposalListView.smoothScrollToPosition(preLast);
            }
        });

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

        proposalListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int itemCountTrigger = totalItemCount/2;
                final int lastItem = firstVisibleItem + visibleItemCount;

                Activity ac = getActivity();
                if (ac != null) {
                    if (getActivity().getClass() == ProposalsList.class) {
                        if(lastItem == totalItemCount - (itemCountTrigger > 15 ? 15 : itemCountTrigger)) {
                            if(preLast != lastItem) {
                                preLast = lastItem;
                                ((ProposalsList)getActivity()).pullProposalsData();
                            }

                        }
                    } else {

                    }
                }
            }
        });
        return view;
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

    @Override
    public void tagsUpdated() {

    }

    @Override
    public void proposalsUpdated() {
        Activity ac = getActivity();
        if (ac != null) {
            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    proposalAdapter.updateData(DataContainer.getInstance().getProposals());
                }
            });
        }

    }

    @Override
    public void usersUpdated() {

    }

    @Override
    public void taggingsUpdated() {

    }
}
