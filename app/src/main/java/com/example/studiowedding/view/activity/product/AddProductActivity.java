package com.example.studiowedding.view.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.databinding.ActivityAccountBinding;
import com.example.studiowedding.databinding.ActivityAddProductBinding;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    ActivityAddProductBinding viewBinding;
    Product product = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewBinding = ActivityAddProductBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        product =  (Product)getIntent().getSerializableExtra("product");
        if(product!=null){
            viewBinding.edProductName.setText(product.getName());
            viewBinding.edProductPrice.setText(product.getPrice()+"");
            viewBinding.btnAddProduct.setText("Cập nhật");
        }
        setContentView(viewBinding.getRoot());
    }

    @Override
    protected void onResume() {
        String[] productArray = getResources().getStringArray(R.array.product_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.spProduct.setAdapter(adapter);
        viewBinding.btnAddProduct.setOnClickListener(v -> {
            if(product!=  null){
                updateProduct();
                return;
            }
            addProduct();
        });
        super.onResume();
    }

    void addProduct() {
        if (viewBinding.edProductPrice.getText().length() == 0 || viewBinding.edProductName.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng điền đủ trường", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiClient.getClient().create(ApiService.class).addProduct(new Product(
                        viewBinding.edProductName.getText().toString(),
                        Float.parseFloat(viewBinding.edProductPrice.getText().toString()),
                        "Sẵn sàng",
                        (String) viewBinding.spProduct.getSelectedItem())).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        AddProductActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void updateProduct() {
        if (viewBinding.edProductPrice.getText().length() == 0 || viewBinding.edProductName.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng điền đủ trường", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiClient.getClient().create(ApiService.class).updateProduct(product.getId()+"",new Product(
                        viewBinding.edProductName.getText().toString(),
                        Float.parseFloat(viewBinding.edProductPrice.getText().toString()),
                        "Sẵn sàng",
                        (String) viewBinding.spProduct.getSelectedItem())).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        AddProductActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}