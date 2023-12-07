package com.example.studiowedding.view.activity.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.utils.FormatUtils;
import com.example.studiowedding.view.fragment.ServicesFragment;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateServiceActivity extends AppCompatActivity {

    private ConstraintLayout container;
    private EditText serviceNameEditText, servicePriceEditText;
    private RelativeLayout updateButton;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);

        initView();
        setListeners();
        serviceNameEditText.setText(getServiceFromIntent().getName());
        servicePriceEditText.setText(String.valueOf((long) getServiceFromIntent().getPrice()));
    }

    public Service getServiceFromIntent() {
        return (Service) getIntent().getSerializableExtra(ServicesFragment.SERVICE_UPDATE_EXTRA);
    }

    private void initView() {
        container = findViewById(R.id.container);
        serviceNameEditText = findViewById(R.id.serviceNameEditText);
        servicePriceEditText = findViewById(R.id.servicePriceEditText);
        updateButton = findViewById(R.id.updateButton);
        backImageView = findViewById(R.id.backImageView);
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> finish());
        updateButton.setOnClickListener(view -> performAddService());
    }

    private void performAddService() {
        String serviceName = serviceNameEditText.getText().toString().trim();
        String servicePrice = servicePriceEditText.getText().toString().trim();

        if (isValidDataInput(serviceName, servicePrice)) {
            // Gọi API thêm dịch vụ
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ServiceResponse> call = apiService.updateService(getServiceFromIntent().getId(), serviceName, Float.parseFloat(servicePrice));
            call.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    if (response.isSuccessful()) {
                        ServiceResponse serviceResponse = response.body();
                        if (serviceResponse != null) {
                            if (serviceResponse.isSuccess()) {
                                showSnackbar("Cập nhật dịch vụ thành công");
                                new Handler().postDelayed(() -> finish(), 1500);
                            } else if (serviceResponse.isFailure()) {
                                showSnackbar("Không có cập nhật nào.");
                                new Handler().postDelayed(() -> finish(), 1500);
                            } else {
                                showSnackbar("Xảy ra lỗi khi cập nhật.");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {

                }
            });
        }
    }

    private boolean isValidDataInput(String serviceName, String servicePrice) {
        if (serviceName.isEmpty() || servicePrice.isEmpty()) {
            showSnackbar("Vui lòng nhập đầy đủ thông tin.");
            return false;
        }

        if (serviceName.matches(".*[$#%^&*(){}:;_<>?+=].*")) {
            showSnackbar("Tên dịch vụ kh hợp lệ");
            return false;
        }

        // Kiểm tra giá thuê có hợp lệ hay không
        try {
            float price = Float.parseFloat(servicePrice);
        } catch (NumberFormatException e) {
            showSnackbar("Giá thuê không hợp lệ.");
            return false;
        }


        return true;
    }

    // Hiển thị thông báo
    private void showSnackbar(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }
}