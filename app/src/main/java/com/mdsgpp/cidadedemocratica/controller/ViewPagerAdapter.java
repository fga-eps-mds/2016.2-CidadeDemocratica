package com.mdsgpp.cidadedemocratica.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;
import com.mdsgpp.cidadedemocratica.view.ProposalsNearbyListFragment;

import java.util.ArrayList;
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
    CountDownLatch signal = new CountDownLatch(1);

    EntityContainer<Proposal> proposalsContainer = EntityContainer.getInstance(Proposal.class);

    public ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs, Context context) {
        super(fragmentManager);

        this.titles = titles;
        this.numberOfTabs = numberOfTabs;

        this.context = context;
    }


    @SuppressWarnings("MissingPermission")
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
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                String provider = LocationManager.GPS_PROVIDER;

                LocationListener listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        updateLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };

                //TODO: pegar localização, pegar propostas da localidade, instanciar tabAroundHere com o estado,
                // retornar

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
