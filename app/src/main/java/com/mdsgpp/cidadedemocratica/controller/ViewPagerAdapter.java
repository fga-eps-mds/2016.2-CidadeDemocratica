package com.mdsgpp.cidadedemocratica.controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;
import com.mdsgpp.cidadedemocratica.view.ProposalsNearbyListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

/**
 * Created by gabriel on 20/09/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numberOfTabs;
    ListProposalFragment tabAll;
    ProposalsNearbyListFragment tabAroundHere;
    ListProposalFragment tabLocation;
    Context context;
    ProgressDialog progressDialog;
    ArrayList<Proposal> proposalsByState;
    String stateName;
    EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

    public ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs, String stateName, ArrayList<Proposal> proposalsByState) {
        super(fragmentManager);

        this.titles = titles;
        this.numberOfTabs = numberOfTabs;
        this.proposalsByState = proposalsByState;
        this.stateName = stateName;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<Proposal> proposals = proposalsContainer.getAll();
        if (position == 0) {
            if (tabAll == null) {
                tabAll = ListProposalFragment.newInstance(proposals);
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

    private void updateLocation(Location location) {

    }
}
