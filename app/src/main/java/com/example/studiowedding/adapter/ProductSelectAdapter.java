package com.example.studiowedding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.utils.FormatUtils;


import java.util.List;

public class ProductSelectAdapter extends RecyclerView.Adapter<ProductSelectAdapter.ProductSelectViewHolder> {
    private List<Product> products;
    private final ItemListener itemListener;

    public ProductSelectAdapter(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product_select, parent, false);
        return new ProductSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSelectViewHolder holder, int position) {
        if (products != null) {
            Product product = products.get(position);
            holder.productNameTextView.setText(product.getName());
            holder.productPriceTextView.setText(FormatUtils.formatCurrencyVietnam(product.getPrice()));
            Glide.with(holder.itemView)
                    .load(product.getImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.img_default)
                    .into(holder.imageView);
            if (itemListener != null) {
                holder.itemView.setOnClickListener(view -> {
                    itemListener.onItemProductSelected(product);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public static class ProductSelectViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, productPriceTextView, productStatusTextView;
        ImageView imageView;

        public ProductSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productStatusTextView = itemView.findViewById(R.id.productStatusTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public interface ItemListener {
        void onItemProductSelected(Product product);
    }
}
