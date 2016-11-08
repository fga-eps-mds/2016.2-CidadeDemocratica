package com.mdsgpp.cidadedemocratica.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import java.util.ArrayList;

/**
 * Created by gabriel on 20/09/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numberOfTabs;
    ListProposalFragment tabAll, tabAroundHere, tabLocation;

    EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

    public ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs){
        super(fragmentManager);

        this.titles = titles;
        this.numberOfTabs = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        ArrayList<Proposal> proposals = proposalsContainer.getAll();
        if(position == 0){
            if(tabAll == null){
                tabAll = ListProposalFragment.newInstance(proposals);
            }
            return  tabAll;
        }
        else if(position == 1){
            if(tabAroundHere == null){
                tabAroundHere = ListProposalFragment.newInstance(proposals);
            }
            return  tabAroundHere;
        }

        else{
            if(tabLocation == null){
                tabLocation = ListProposalFragment.newInstance(proposals);
            }
            return  tabLocation;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
