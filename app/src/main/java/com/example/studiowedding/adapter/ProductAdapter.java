package com.example.studiowedding.adapter;// ExampleAdapter.java

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studiowedding.R; // Thêm dòng này nếu chưa thêm
import com.example.studiowedding.model.Product;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.product.AddProductActivity;
import com.example.studiowedding.view.activity.task.ResponseTask;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

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
        holder.tvgiavay.setText(formatCurrency(currentItem.getPrice()));
        if(currentItem.getStatus().trim().equals("Sẵn sàng")){
            String hexColor = "#226403"; // Ví dụ màu cam
            int color = Color.parseColor(hexColor);
            holder.tvtrangthai.setTextColor(color);
        }else if (currentItem.getStatus().trim().equals("Chưa sẵn sàng")){
            String hexColor = "#D83C3C"; // Ví dụ màu cam
            int color = Color.parseColor(hexColor);
            holder.tvtrangthai.setTextColor(color);
        }else if (currentItem.getStatus().trim().equals("Có phát sinh")){
            String hexColor = "#E1D140"; // Ví dụ màu cam
            int color = Color.parseColor(hexColor);
            holder.tvtrangthai.setTextColor(color);
        }else{
            String hexColor = "#3081D0"; // Ví dụ màu cam
            int color = Color.parseColor(hexColor);
            holder.tvtrangthai.setTextColor(color);
        }
        holder.tvtrangthai.setText(currentItem.getStatus());
        Log.d("123", "1 "+ currentItem.getImgUrl());
        if(currentItem.getImgUrl()!=null){
            Glide.with( holder.itemView.getContext())
                    .load(currentItem.getImgUrl())
                    .into(holder.imgProduct);
        }
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Xác nhận");  // Tiêu đề của thông báo
                            builder.setMessage("Bạn có muốn xóa sản phẩm này ?");  // Nội dung thông báo

                            // Nút tích cực (ví dụ: Đồng ý)
                            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
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
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            return true;

                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        });
    }


    private String formatCurrency(float amount) {
        // Chọn Locale tiếng Việt để định dạng số
        Locale vietnameseLocale = new Locale("vi", "VN");

        // Tạo một đối tượng NumberFormat cho tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);

        // Định dạng số theo định dạng tiền tệ
        String formattedAmount = currencyFormatter.format(amount);

        // Thêm ký tự đơn vị tiền tệ (VND) vào đầu chuỗi
        Currency vietnameseCurrency = Currency.getInstance(vietnameseLocale);
        formattedAmount = formattedAmount.replace(vietnameseCurrency.getSymbol(), vietnameseCurrency.getCurrencyCode());
        return formattedAmount;
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
        ImageView imgProduct;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvvaycuoi = itemView.findViewById(R.id.tvvaycuoi);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthai);
            tvgiavay = itemView.findViewById(R.id.tvgiavay);
            imgMenu = itemView.findViewById(R.id.img_pop_product);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            Log.d("123", "ExampleViewHolder: "+imgProduct);
        }
    }

    private void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }
}
