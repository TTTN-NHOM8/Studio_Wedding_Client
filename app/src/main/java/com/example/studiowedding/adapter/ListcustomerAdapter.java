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

import com.example.studiowedding.view.activity.customer.UpdateCustomerActivity;
import com.example.studiowedding.R;
import com.example.studiowedding.model.Customer;


import java.util.ArrayList;

public class ListcustomerAdapter extends RecyclerView.Adapter<ListcustomerAdapter.Viewhodel> {
    ArrayList<Customer> listCustomersModels;

    public ListcustomerAdapter(ArrayList<Customer> listCustomersModels) {
        this.listCustomersModels = listCustomersModels;
    }


    @Override
    public ListcustomerAdapter.Viewhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewhodel_listcustomer,parent,false);
        return new Viewhodel(inflate);
    }
    View.OnClickListener myClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, UpdateCustomerActivity.class);
            context.startActivity(intent);
        }
    };

    @Override
    public void onBindViewHolder(@NonNull ListcustomerAdapter.Viewhodel holder, int position) {
        holder.TVNameCustomer.setText(listCustomersModels.get(position).getName());
        holder.TVPhoneCustome.setText(listCustomersModels.get(position).getPhone());
        holder.TVAddressCustome.setText(listCustomersModels.get(position).getAddress());
        holder.imgUpdateClient.setOnClickListener(myClickListener2);
        Customer customer = listCustomersModels.get(position);
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

