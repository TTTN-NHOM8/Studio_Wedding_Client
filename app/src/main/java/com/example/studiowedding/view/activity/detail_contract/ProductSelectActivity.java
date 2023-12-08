package com.example.studiowedding.view.activity.detail_contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.studiowedding.R;
//import com.example.studiowedding.adapter.ProductSelectAdapter;
import com.example.studiowedding.adapter.ProductSelectAdapter;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSelectActivity extends AppCompatActivity implements ProductSelectAdapter.ItemListener {
    public static final String TAG = "ProductSelectActivity";
    public static final String PRODUCT_SELECTED_EXTRA = "ProductSelected";
    private ImageView backImageView;
    private EditText searchEditText;
    private RecyclerView productSelectRecyclerView;
    private ProductSelectAdapter productSelectAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_select);

        initView();
        setListeners();
        setUpRecyclerView();
        perfromCallAPIGetProducts();

        // Tìm kiếm sản phẩm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchTerm = charSequence.toString().toLowerCase();

                List<Product> filteredProducts = new ArrayList<>();
                for (Product product : productList) {
                    if (product.getName().toLowerCase().contains(searchTerm)) {
                        filteredProducts.add(product);
                    }
                }

                productSelectAdapter.setProducts(filteredProducts);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initView() {
        backImageView = findViewById(R.id.backImageView);
        productSelectRecyclerView = findViewById(R.id.productSelectRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setUpRecyclerView() {
        productSelectAdapter = new ProductSelectAdapter(this);
        productSelectRecyclerView.setAdapter(productSelectAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        productSelectRecyclerView.setLayoutManager(layoutManager);
        productSelectRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemProductSelected(Product product) {
        Intent intent = new Intent();
        intent.putExtra(PRODUCT_SELECTED_EXTRA, product);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    // Thực hiện gọi api lấy danh sách sản phẩm
    private void perfromCallAPIGetProducts() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Product>> call = apiService.getProductsByStatusReady();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    if (products != null) {
                        productSelectAdapter.setProducts(products);
                        productList = products;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }

}