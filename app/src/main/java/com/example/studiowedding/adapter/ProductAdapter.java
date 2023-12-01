package com.example.studiowedding.adapter;// ExampleAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R; // Thêm dòng này nếu chưa thêm
import com.example.studiowedding.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ExampleViewHolder> {
    private List<Product> dataList;

    public ProductAdapter(List<Product> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Product currentItem = dataList.get(position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvvaycuoi;
        public TextView tvtrangthai;
        public TextView tvgiavay;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvvaycuoi = itemView.findViewById(R.id.tvvaycuoi);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthai);
            tvgiavay = itemView.findViewById(R.id.tvgiavay);
        }
    }
}
