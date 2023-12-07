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
import com.example.studiowedding.adapter.ServiceSelectAdapter;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceSelectActivity extends AppCompatActivity implements ServiceSelectAdapter.ItemListener {
    public static final String TAG = "ServiceSelectActivity";
    public static final String SERVICE_SELECTED_EXTRA = "ServiceSelected";
    private ImageView backImageView;
    private RecyclerView serviceSelectRecyclerView;
    private ServiceSelectAdapter serviceSelectAdapter;
    private List<Service> serviceList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_select);

        initView();
        setListeners();
        setUpdateRecyclerView();
        performCallApiGetServices();

        // Tìm kiếm dịch vụ
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchTerm = charSequence.toString().toLowerCase();

                List<Service> filteredServices = new ArrayList<>();
                for (Service service : serviceList) {
                    if (service.getName().toLowerCase().contains(searchTerm)) {
                        filteredServices.add(service);
                    }
                }

                serviceSelectAdapter.setServices(filteredServices);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private void initView() {
        backImageView = findViewById(R.id.backImageView);
        serviceSelectRecyclerView = findViewById(R.id.serviceSelectRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setUpdateRecyclerView() {
        serviceSelectAdapter = new ServiceSelectAdapter(this);
        serviceSelectRecyclerView.setAdapter(serviceSelectAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        serviceSelectRecyclerView.setLayoutManager(layoutManager);
        serviceSelectRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemServiceSelected(Service service) {
        Intent intent = new Intent();
        intent.putExtra(SERVICE_SELECTED_EXTRA, service);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    // Thực hiện gọi API lấy danh sách dịch vụ
    private void performCallApiGetServices() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Service>> call = apiService.getServices();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    List<Service> services = response.body();
                    if (services != null) {
                        serviceSelectAdapter.setServices(services);
                        serviceList = services;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }
}