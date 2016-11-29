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
public class ProposalListSectionAdapter extends BaseAdapter {

    private ArrayList<Object> data;
    private Context context;
    private static final int TYPE_PROPOSAL = 0;
    private static final int TYPE_DIVIDER = 1;

    public ProposalListSectionAdapter(Context context, ArrayList<Object> data){
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Proposal) {
            return TYPE_PROPOSAL;
        }else {
            return TYPE_DIVIDER;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_PROPOSAL);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ProposalListRow row;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int type = getItemViewType(i);

        if (view == null) {
            switch (type) {
                case TYPE_PROPOSAL:
                    view = inflater.inflate(R.layout.proposal_list_row,viewGroup,false);
                    break;
                case TYPE_DIVIDER:
                    view = inflater.inflate(R.layout.proposal_header_list_row,viewGroup,false);
                    break;
            }
        }

        switch (type) {
            case TYPE_PROPOSAL:
                Proposal currentProposal = (Proposal) data.get(i);
                view = inflater.inflate(R.layout.proposal_list_row,viewGroup,false);
                row = new ProposalListRow();
                row.titleTextView = (TextView) view.findViewById(R.id.titleTextView);
                row.descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
                row.titleTextView.setText(currentProposal.getTitle());
                row.titleTextView.setTextColor(Color.BLACK);
                row.descriptionTextView.setText(currentProposal.getContent());
                view.setTag(row);
                break;
            case TYPE_DIVIDER:
                view = inflater.inflate(R.layout.proposal_header_list_row,viewGroup,false);
                TextView title = (TextView)view.findViewById(R.id.headerTitle);
                String titleString = (String)getItem(i);
                title.setText(titleString);
                break;
        }

        return view;
    }

    public void updateData(ArrayList<Object> data) {

        this.data = data;
        notifyDataSetChanged();
    }
}
