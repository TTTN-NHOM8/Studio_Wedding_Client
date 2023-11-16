package com.example.studiowedding.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.Client.FindPhoneActivity;
import com.example.studiowedding.Client.UpdateClientActivity;
import com.example.studiowedding.R;
import com.example.studiowedding.model.ListclientModel;


import java.util.ArrayList;

public class ListClientAdapter extends RecyclerView.Adapter<ListClientAdapter.Viewhodel> {
    ArrayList<ListclientModel> listCustomersModels;

    public ListClientAdapter(ArrayList<ListclientModel> listCustomersModels) {
        this.listCustomersModels = listCustomersModels;
    }


    @Override
    public ListClientAdapter.Viewhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewhodel_listclient,parent,false);
        return new Viewhodel(inflate);
    }
    View.OnClickListener myClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, UpdateClientActivity.class);
            context.startActivity(intent);
        }
    };

    @Override
    public void onBindViewHolder(@NonNull ListClientAdapter.Viewhodel holder, int position) {
        holder.TVNameCustomer.setText(listCustomersModels.get(position).getName());
        holder.TVPhoneCustome.setText(listCustomersModels.get(position).getPhone());
        holder.TVAddressCustome.setText(listCustomersModels.get(position).getAddress());
        holder.imgUpdateClient.setOnClickListener(myClickListener2);
        ListclientModel customer = listCustomersModels.get(position);
        String picUrl= "";
        switch (position){
            case 0:{
                picUrl = "menu_option_item";
                holder.imgUpdateClient.setImageResource(R.drawable.menu_option_item);
                holder.imgUpdateClient.setOnClickListener(myClickListener2);

                break;
            }

        }



    }

    @Override
    public int getItemCount() {
        return listCustomersModels.size();
    }

    public class Viewhodel extends RecyclerView.ViewHolder {
        TextView TVNameCustomer, TVPhoneCustome,TVAddressCustome;
        ImageView imgUpdateClient;
        public Viewhodel(@NonNull View itemView) {
            super(itemView);

            imgUpdateClient = itemView.findViewById(R.id.ImgUpdateClient);
            TVNameCustomer = itemView.findViewById(R.id.TVNameCustomer);
            TVPhoneCustome = itemView.findViewById(R.id.TVPhoneCustome);
            TVAddressCustome = itemView.findViewById(R.id.TVAddressCustome);
        }
    }
}

