package com.mdsgpp.cidadedemocratica.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.ProposalListRow;

import java.util.ArrayList;

/**
 * Created by gabriel on 20/09/16.
 */
public class ProposalListAdapter extends BaseAdapter {

    private static ArrayList<Proposal> data;
    private Context context;

    public ProposalListAdapter(Context context, ArrayList<Proposal> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ProposalListRow row;
        Proposal currentProposal = data.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.proposal_list_row,viewGroup,false);
            row = new ProposalListRow();
            row.titleTextVIew = (TextView) view.findViewById(R.id.titleTextView);
            row.descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
            view.setTag(row);

        }else{
            row = (ProposalListRow)view.getTag();

        }

        row.titleTextVIew.setText(currentProposal.getTitle());
        row.titleTextVIew.setTextColor(Color.BLACK);
        row.descriptionTextView.setText(currentProposal.getContent());

        return view;
    }
}
