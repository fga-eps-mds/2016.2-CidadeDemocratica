package com.mdsgpp.cidadedemocratica.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import layout.ListProposalFragment;

/**
 * Created by gabriel on 20/09/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

   CharSequence titles[];
    int numberOfTabs;
    ListProposalFragment tabAll, tabAroundHere, tabLocation;

    public  ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs){
        super(fragmentManager);

        this.titles = titles;
        this.numberOfTabs = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            if(tabAll==null){
                tabAll = new ListProposalFragment();
            }
            return  tabAll;
        }
        else if(position ==1){
            if(tabAroundHere==null){
                tabAroundHere = new ListProposalFragment();
            }
            return  tabAroundHere;
        }

        else{
            if(tabLocation==null){
                tabLocation = new ListProposalFragment();
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
