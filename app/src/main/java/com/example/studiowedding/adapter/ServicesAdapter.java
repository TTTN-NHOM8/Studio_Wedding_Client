package com.example.studiowedding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private String[] dataSanPham; // Dữ liệu cho tab "Sản Phẩm"
    private String[] dataDichVu;  // Dữ liệu cho tab "Dịch Vụ"

    public ServicesAdapter(String[] dataSanPham, String[] dataDichVu) {
        this.dataSanPham = dataSanPham;
        this.dataDichVu = dataDichVu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set dữ liệu cho mỗi mục trong RecyclerView dựa vào tab được chọn
        // Nếu tab "Sản Phẩm" được chọn, sử dụng dataSanPham; nếu tab "Dịch Vụ" được chọn, sử dụng dataDichVu
        if (dataSanPham != null) {
            holder.textView.setText(dataSanPham[position]);
        }
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng mục dựa vào tab được chọn
        return (dataSanPham != null) ? dataSanPham.length : (dataDichVu != null) ? dataDichVu.length : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textView = view.findViewById(R.id.rcvProduct);
        }
    }
}
