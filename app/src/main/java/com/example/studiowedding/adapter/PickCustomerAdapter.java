package com.example.studiowedding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Customer;

import java.util.List;

public class PickCustomerAdapter extends RecyclerView.Adapter<PickCustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private Context context;
    private OnItemClickListner listener;

    public PickCustomerAdapter(List<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListner listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_pick_customer, parent, false);
        return new CustomerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer currentCustomer = customerList.get(position);

        holder.tvName.setText(currentCustomer.getName());
        holder.tvPhone.setText(currentCustomer.getPhone());
        holder.tvAddress.setText(currentCustomer.getAddress());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvPhone, tvAddress;

        public CustomerViewHolder(@NonNull View itemView, OnItemClickListner listener) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameCustomer);
            tvPhone = itemView.findViewById(R.id.tvPhoneCustomer);
            tvAddress = itemView.findViewById(R.id.tvAddressCustomer);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
