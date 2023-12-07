package com.example.studiowedding.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.view.activity.customer.UpdateCustomerActivity;
import com.example.studiowedding.R;
import com.example.studiowedding.model.Customer;


import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.Viewhodel> implements Filterable {
    List<Customer> listCustomers;
    private List<Customer> listCustomersOld;

    public CustomerAdapter(List<Customer> listCustomers) {
        this.listCustomers = listCustomers;
        this.listCustomersOld = listCustomers;
    }
    public void updateCustomer(List<Customer> newCustomerList){
        listCustomers.clear();
        listCustomers.addAll(newCustomerList);
        notifyDataSetChanged();
    }

    @Override
    public CustomerAdapter.Viewhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewhodel_listcustomer,parent,false);

        return new Viewhodel(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.Viewhodel holder, int position) {
        holder.TVNameCustomer.setText(listCustomers.get(position).getName());
        holder.TVPhoneCustome.setText(listCustomers.get(position).getPhone());
        holder.TVAddressCustome.setText(listCustomers.get(position).getAddress());
        Customer customer = listCustomers.get(position);
        holder.imgUpdateClient.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.popup_menu_options);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.action_update:
                        Intent intent = new Intent(v.getContext(), UpdateCustomerActivity.class);
                        intent.putExtra("customer",customer);
                        v.getContext().startActivity(intent);
                        return true;
                }
                return false;
            });
            popupMenu.show();
        });

    }

    @Override
    public int getItemCount() {
        return listCustomers.size();
    }

    @Override
    public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String strSearch = charSequence.toString();
                    if (strSearch.isEmpty()){
                        listCustomers = listCustomersOld;
                    }else {
                        List<Customer> list = new ArrayList<>();
                        for (Customer customer: listCustomersOld){
                            if (customer.getPhone().toLowerCase().contains(strSearch.toLowerCase())){
                                list.add(customer);
                            }
                        }
                        listCustomers = list;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = listCustomers;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    listCustomers = (List<Customer>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

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

