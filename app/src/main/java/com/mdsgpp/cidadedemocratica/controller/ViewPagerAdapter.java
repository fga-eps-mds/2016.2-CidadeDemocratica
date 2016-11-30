package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;
import com.mdsgpp.cidadedemocratica.view.ProposalsNearStateListFragment;
import com.mdsgpp.cidadedemocratica.view.ProposalsNearbyListFragment;

import java.util.ArrayList;

/**
 * Created by gabriel on 20/09/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numberOfTabs;
    ListProposalFragment tabAll;
    ProposalsNearbyListFragment tabAroundHere;
    ProposalsNearStateListFragment tabLocation;
    Context context;
    ProgressDialog progressDialog;
    ArrayList<Proposal> proposalsByState;
    ArrayList<Proposal> favoriteProposals;
    String stateName;
    EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

    public ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs, String stateName,
                            ArrayList<Proposal> proposalsByState, ArrayList<Proposal> favoriteProposals) {
        super(fragmentManager);

        this.titles = titles;
        this.numberOfTabs = numberOfTabs;
        this.proposalsByState = proposalsByState;
        this.stateName = stateName;
        this.favoriteProposals = favoriteProposals;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<Proposal> proposals = proposalsContainer.getAll();
        if (position == 0) {
            if (tabAll == null) {
                tabAll = ListProposalFragment.newInstance(proposals, favoriteProposals);
            }
            return tabAll;

        } else if (position == 1) {
            if (tabAroundHere == null) {
                tabAroundHere = ProposalsNearbyListFragment.newInstance(this.stateName,this.proposalsByState);
            }
            return  tabAroundHere;
        }

        else {
            if(tabLocation == null){
                tabLocation = ProposalsNearStateListFragment.newInstance(this.stateName,this.proposalsByState);
            }
            return  tabLocation;
        }
    }

    public void setFavoriteProposals(ArrayList<Proposal> favoriteProposals) {
        this.favoriteProposals = favoriteProposals;

    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    private void updateLocation(Location location) {

    }
}
