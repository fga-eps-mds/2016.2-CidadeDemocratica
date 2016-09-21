package com.mdsgpp.cidadedemocratica.controller;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

public class TagDetailActivity extends AppCompatActivity implements OnFragmentInteractionListener, ListProposalFragment.OnFragmentInteractionListener {

    private Tag tag;
    private DataContainer dataContainter = DataContainer.getInstance();
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);

        fragmentManager = getSupportFragmentManager();

        Bundle extras = getIntent().getExtras();
        Long tagId = extras.getLong("tagId");
        this.tag = dataContainter.getTagForId(tagId);

        TextView tagNameTextView = (TextView)findViewById(R.id.tagNameTextView);
        tagNameTextView.setText(this.tag.getName());

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, ListProposalFragment.newInstance(this.tag.getProposals())).
                commit();

    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
