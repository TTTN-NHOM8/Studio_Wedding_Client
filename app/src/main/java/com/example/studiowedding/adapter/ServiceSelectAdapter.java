package com.example.studiowedding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.utils.FormatUtils;


import java.util.List;

public class ServiceSelectAdapter extends RecyclerView.Adapter<ServiceSelectAdapter.ServiceSelectViewHolder> {
    private List<Service> services;
    private final ItemListener itemListener;

    public ServiceSelectAdapter(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setServices(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_service_select, parent, false);
        return new ServiceSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceSelectViewHolder holder, int position) {
        if (services != null) {
            Service service = services.get(position);
            holder.serviceNameTextView.setText(service.getName());
            holder.servicePriceTextView.setText(FormatUtils.formatCurrencyVietnam(service.getPrice()));

            if(itemListener != null) {
                holder.itemView.setOnClickListener(view -> {
                    itemListener.onItemServiceSelected(service);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return services == null ? 0 : services.size();
    }

    public static class ServiceSelectViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView, servicePriceTextView;

        public ServiceSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            servicePriceTextView = itemView.findViewById(R.id.servicePriceTextView);
        }
    }

    public interface ItemListener {
        void onItemServiceSelected(Service service);
    }
}
