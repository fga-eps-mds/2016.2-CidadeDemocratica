package com.mdsgpp.cidadedemocratica.controller;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mdsgpp.cidadedemocratica.External.SlidingTabLayout;
import com.mdsgpp.cidadedemocratica.R;

import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;


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
