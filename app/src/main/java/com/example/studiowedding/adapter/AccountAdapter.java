package com.example.studiowedding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studiowedding.R;

public class AccountAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] rolesArray;

    public AccountAdapter(Context context, int resource, String[] rolesArray) {
        super(context, resource, rolesArray);
        this.context = context;
        this.rolesArray = rolesArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.item_account, parent, false);
        }

        TextView textView = row.findViewById(R.id.tvRoleName);
        textView.setText(rolesArray[position]);



        return row;
    }
}
