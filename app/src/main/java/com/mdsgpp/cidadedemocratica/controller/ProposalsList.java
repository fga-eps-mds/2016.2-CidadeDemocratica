package com.mdsgpp.cidadedemocratica.controller;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mdsgpp.cidadedemocratica.External.SlidingTabLayout;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.ProposalRequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.TagRequestResponseHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.client.cache.Resource;
import layout.ListProposalFragment;


public class ProposalsList extends AppCompatActivity implements ListProposalFragment.OnFragmentInteractionListener{

    private int numberTabs = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposals_list);


        CharSequence titles[] = {getString(R.string.titulo_tab_tudo),getString(R.string.titulo_tab_porAqui),
                                    getString(R.string.titulo_tab_localidade)};

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),titles,numberTabs);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
            @Override
            public int getIndicatorColor(int position){
                return getResources().getColor(R.color.colorAccent);
            }
        });

        tabs.setViewPager(pager);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
