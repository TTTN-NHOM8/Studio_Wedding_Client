package com.example.studiowedding.adapter;// ExampleAdapter.java

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R; // Thêm dòng này nếu chưa thêm
import com.example.studiowedding.model.Product;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.product.AddProductActivity;
import com.example.studiowedding.view.activity.task.ResponseTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(ExampleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product currentItem = dataList.get(position);
        holder.tvgiavay.setText(currentItem.getPrice() + "");
        holder.tvtrangthai.setText(currentItem.getStatus());
        holder.tvvaycuoi.setText(currentItem.getName());
        holder.imgMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.imgMenu);
            popupMenu.getMenuInflater().inflate(R.menu.pop_menu_product, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.Pop_product_update:
                            // Xử lý khi chọn "Sửa"
                            Intent intent = new Intent(v.getContext(), AddProductActivity.class);
                            intent.putExtra("product", currentItem);
                            v.getContext().startActivity(intent);
                            return true;

                        case R.id.Pop_product_delete:
                            // Xử lý khi chọn "Xóa"
                            removeItem(position);
                            ApiClient.getClient().create(ApiService.class).deleteProductId(currentItem.getId()).enqueue(new Callback<ResponseTask>() {
                                @Override
                                public void onResponse(Call<ResponseTask> call, Response<ResponseTask> response) {
                                    Toast.makeText(v.getContext(), "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseTask> call, Throwable t) {
                                    Toast.makeText(v.getContext(), "Có lỗi khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return true;

                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvvaycuoi;
        public TextView tvtrangthai;
        public TextView tvgiavay;
        ImageView imgMenu;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvvaycuoi = itemView.findViewById(R.id.tvvaycuoi);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthai);
            tvgiavay = itemView.findViewById(R.id.tvgiavay);
            imgMenu = itemView.findViewById(R.id.img_pop_product);
        }
    }

    private void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }
}
