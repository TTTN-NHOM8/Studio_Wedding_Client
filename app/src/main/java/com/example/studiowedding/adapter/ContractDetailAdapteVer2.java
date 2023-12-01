package com.example.studiowedding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.model.ContractDetail;
import com.example.studiowedding.utils.FormatUtils;

import java.util.List;

public class ContractDetailAdapteVer2  extends RecyclerView.Adapter<ContractDetailAdapteVer2.ContractDetailViewHolder>{
    private List<ContractDetail> contractDetails;
    private Context context;

    public ContractDetailAdapteVer2(List<ContractDetail> contractDetails, Context context) {
        this.contractDetails = contractDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ContractDetailAdapteVer2.ContractDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_detail_contract, parent, false);
        return new ContractDetailAdapteVer2.ContractDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractDetailAdapteVer2.ContractDetailViewHolder holder, int position) {
        if (contractDetails != null) {
            // Ẩn view
            holder.dateOfHireTextView.setVisibility(View.GONE);
            holder.dateOfReturnTextView.setVisibility(View.GONE);
            holder.productNameTextView.setVisibility(View.GONE);
            holder.productPriceTextView.setVisibility(View.GONE);

            holder.locationTextView.setVisibility(View.GONE);
            holder.dateOfPerformTextView.setVisibility(View.GONE);
            holder.serviceName.setVisibility(View.GONE);
            holder.servicePrice.setVisibility(View.GONE);

            ContractDetail contractDetail = contractDetails.get(position);
            /*
             * Nếu địa điểm null thì sẽ là HĐCT với gói sản phẩm
             * Ngược lại sẽ là HĐCT với gói dịch vụ
             */
            if (contractDetail.getLocation() == null) {
                // Hiển thị view HĐCT với gói sản phẩm
                holder.dateOfHireTextView.setVisibility(View.VISIBLE);
                holder.dateOfReturnTextView.setVisibility(View.VISIBLE);
                holder.productNameTextView.setVisibility(View.VISIBLE);
                holder.productPriceTextView.setVisibility(View.VISIBLE);

                // Gán dữ liệu cho view
                holder.dateOfHireTextView.setText(contractDetail.getDateOfHire());
                holder.dateOfReturnTextView.setText(contractDetail.getDateOfReturn());
                holder.productNameTextView.setText(contractDetail.getProductName());
                holder.productPriceTextView.setText(FormatUtils.formatCurrencyVietnam(contractDetail.getProductPrice()));
            } else {
                // Hiển thị view HĐCT với dịch vụ
                holder.locationTextView.setVisibility(View.VISIBLE);
                holder.dateOfPerformTextView.setVisibility(View.VISIBLE);
                holder.serviceName.setVisibility(View.VISIBLE);
                holder.servicePrice.setVisibility(View.VISIBLE);

                // Gán dữ liệu cho view
                holder.locationTextView.setText(contractDetail.getLocation());
                holder.dateOfPerformTextView.setText(contractDetail.getDateOfPerform());
                holder.serviceName.setText(contractDetail.getServiceName());
                holder.servicePrice.setText(FormatUtils.formatCurrencyVietnam(contractDetail.getServicePrice()));
            }

        }
    }

    @Override
    public int getItemCount() {
        return contractDetails == null ? 0 : contractDetails.size();
    }
    public static class ContractDetailViewHolder extends RecyclerView.ViewHolder {
        TextView locationTextView, dateOfPerformTextView, serviceName, servicePrice;
        TextView dateOfHireTextView, dateOfReturnTextView, productNameTextView, productPriceTextView;
        ImageView imgPopupMenuDetailContractItem;

        public ContractDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            // View sản phẩm
            dateOfHireTextView = itemView.findViewById(R.id.dateOfHireTextView);
            dateOfReturnTextView = itemView.findViewById(R.id.dateOfReturnTextView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);

            // View dịch vụ
            locationTextView = itemView.findViewById(R.id.locationTextView);
            dateOfPerformTextView = itemView.findViewById(R.id.dateOfPerformTextView);
            serviceName = itemView.findViewById(R.id.serviceNameTextView);
            servicePrice = itemView.findViewById(R.id.servicePriceTextView);

            // Img menu
            imgPopupMenuDetailContractItem = itemView.findViewById(R.id.imgPopupMenuDetailContractItem);

            imgPopupMenuDetailContractItem.setVisibility(View.GONE);
        }
    }
}
