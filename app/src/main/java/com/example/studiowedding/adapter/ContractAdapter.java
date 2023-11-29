package com.example.studiowedding.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.interfaces.OnItemClickListner;
import com.example.studiowedding.model.Contract;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ViewHolder> {
    private List<Contract> contractList;
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private OnItemClickListner itemClickListener;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###");


    public ContractAdapter(List<Contract> contractList, Context context) {
        this.contractList = contractList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_contract, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (contractList != null) {
            Contract contract = contractList.get(position);
            String tongTienFormat=decimalFormat.format(Float.parseFloat(String.valueOf(contractList.get(position).getTongTien())));

            holder.tvIdHD.setText(contract.getIdHopDong());
            holder.tvDateCreate.setText(sdf.format(contract.getNgayTao()));
            holder.tvAmount.setText(tongTienFormat +" đ");
            holder.tvName.setText(contract.getTenKH());

            String trangThaiThanhToan = contract.getTrangThaiThanhToan();
            holder.tvPaymentStatus.setText(trangThaiThanhToan);

            int paymentStatusColor = trangThaiThanhToan.equals("Đã thanh toán") ?
                    ContextCompat.getColor(context, R.color.dark_green) :
                    ContextCompat.getColor(context, R.color.red_light);
            holder.tvPaymentStatus.setTextColor(paymentStatusColor);

            String trangThaiHopDong = contract.getTrangThaiHopDong();
            String trangThaiPhatSinh = contract.getTrangThaiPhatSinh();


            int statusHDColor;
            if ("Có phát sinh".equals(trangThaiPhatSinh)) {
                holder.tvStatusHD.setText(trangThaiPhatSinh);
                statusHDColor = ContextCompat.getColor(context, R.color.red_light);
            } else {
                holder.tvStatusHD.setText(trangThaiHopDong);

                switch (trangThaiHopDong) {
                    case "Đã hoàn thành":
                        statusHDColor = ContextCompat.getColor(context, R.color.blue_g);
                        break;
                    case "Đang thực hiện":
                        statusHDColor = ContextCompat.getColor(context, R.color.earthy);
                        break;
                    default:
                        statusHDColor = ContextCompat.getColor(context, android.R.color.black);
                }
            }

            holder.tvStatusHD.setTextColor(statusHDColor);

            holder.position = position;
        }
    }

    @Override
    public int getItemCount() {
        return contractList == null ? 0 : contractList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIdHD, tvDateCreate, tvAmount, tvName, tvPaymentStatus, tvStatusHD;
        public ImageView imgMenu;
        public int position;

        public ViewHolder(View view) {
            super(view);

            tvIdHD = view.findViewById(R.id.tvIdHDItem);
            tvDateCreate = view.findViewById(R.id.tvDateCreateHDItem);
            tvAmount = view.findViewById(R.id.tvTotalAmountHDItem);
            tvName = view.findViewById(R.id.tvNameHDItem);
            tvPaymentStatus = view.findViewById(R.id.tvPaymentStatusHDItem);
            tvStatusHD = view.findViewById(R.id.tvStatusHDItem);

            imgMenu = view.findViewById(R.id.imgPopupItemContract);

            imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(position);
                    }
                }
            });

        }
    }

}
