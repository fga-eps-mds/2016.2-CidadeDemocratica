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
import com.mdsgpp.cidadedemocratica.controller.ProposalListSectionAdapter;
import com.mdsgpp.cidadedemocratica.controller.ProposalsList;
import com.mdsgpp.cidadedemocratica.controller.TagginsList;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class ListProposalFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView proposalListView;

    public ArrayList<Proposal> proposals;
    public ArrayList<Proposal> favoriteProposals;
    private View header;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    ProposalListSectionAdapter proposalListAdapter;
    EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

    int preLast = 0;
    public ListProposalFragment() {
        // Required empty public constructor
    }

    public static ListProposalFragment newInstance(ArrayList<Proposal> proposals) {


        ListProposalFragment fragment = new ListProposalFragment();

        fragment.proposals = proposals;

        return fragment;
    }

    public static ListProposalFragment newInstance(ArrayList<Proposal> proposals, View header) {


        ListProposalFragment fragment = ListProposalFragment.newInstance(proposals);
        fragment.header = header;

        return fragment;
    }

    public static ListProposalFragment newInstance(ArrayList<Proposal> proposals, ArrayList<Proposal> proposalsFavorite) {


        ListProposalFragment fragment = new ListProposalFragment();

        fragment.proposals = proposals;
        fragment.favoriteProposals = proposalsFavorite;

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

        if (favoriteProposals!=null){

        }else {
        }

        proposalListView = (ListView) view.findViewById(R.id.proposalsListId);

        ArrayList<Object> adapterData = getAdapterData();



        proposalListAdapter = new ProposalListSectionAdapter(getContext().getApplicationContext(), adapterData);
//        proposalListAdapter.updateData(proposals);


        proposalListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                proposalListView.smoothScrollToPosition(preLast);
            }
        });

        proposalListView.setAdapter(proposalListAdapter);

        if (header!=null){
            proposalListView.addHeaderView(header);
        }

        proposalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (header!=null){
                    i--;
                }
                if (i>=0){
                    Proposal proposalClicked = (Proposal)proposalListAdapter.getItem(i);
                    Long id = proposalClicked.getId();

                    Intent intent = new Intent(getActivity().getApplicationContext(),TagginsList.class);
                    intent.putExtra("proposalId", id);
                    startActivity(intent);
                }
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

    private ArrayList<Object> getAdapterData() {
        ArrayList<Object> adapterData = new ArrayList<>();
        adapterData.add("Favoritas");
        adapterData.addAll(favoriteProposals);
        adapterData.add("Todas");
        adapterData.addAll(proposals);

        return adapterData;
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

    public void setFavoriteProposals(ArrayList<Proposal> favoriteProposals) {
        this.favoriteProposals = favoriteProposals;
        proposalListAdapter.updateData(getAdapterData());
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

    public ListView getProposalListView() {
        return proposalListView;
    }
}
