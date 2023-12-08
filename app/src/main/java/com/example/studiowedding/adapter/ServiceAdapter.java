package com.example.studiowedding.adapter;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.utils.FormatUtils;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    public static final String MENU_BODY_UPDATE_TITLE = "Cập nhật";
    public static final String MENU_BODY_DELETE_TITLE = "Xoá";
    public static final String MENU_HEADER_TITLE = "Menu";
    private List<Service> services;
    private final ItemListener itemListener;

    public ServiceAdapter(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setServices(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        if (services != null) {
            Service service = services.get(position);
            holder.serviceNameTextView.setText(service.getName());
            holder.servicePriceTextView.setText(FormatUtils.formatCurrencyVietnam(service.getPrice()));
            holder.imgMoreMenu.setOnClickListener(view -> {
                final CharSequence[] options = {MENU_BODY_UPDATE_TITLE, MENU_BODY_DELETE_TITLE};
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(MENU_HEADER_TITLE)
                        .setItems(options, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    itemListener.startUpdateServieActivity(service);
                                    break;
                                case 1:
                                    itemListener.showConfirmDeleteService(service);
                                    break;
                                default:
                                    break;
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return services == null ? 0 : services.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView, servicePriceTextView;
        ImageView imgMoreMenu;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            servicePriceTextView = itemView.findViewById(R.id.servicePriceTextView);
            imgMoreMenu = itemView.findViewById(R.id.imgMoreMenu);
        }
    }

    public interface ItemListener {
        void startUpdateServieActivity(Service service);
        void showConfirmDeleteService(Service service);
    }
}
