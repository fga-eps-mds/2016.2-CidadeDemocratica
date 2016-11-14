package com.mdsgpp.cidadedemocratica.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.model.UserListRow;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/21/16.
 */
public class UserListAdapter extends BaseAdapter {

    private static ArrayList<User> data;
    private Context context;

    public UserListAdapter(Context context, ArrayList<User> data){
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

        UserListRow row;
        User currentUser = data.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_list_row,viewGroup,false);
            row = new UserListRow();
            row.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            view.setTag(row);

        }else{
            row = (UserListRow) view.getTag();
        }

        row.nameTextView.setText(currentUser.getName());
        row.nameTextView.setTextColor(Color.BLACK);

        return view;
    }

    public void updateData(ArrayList<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
