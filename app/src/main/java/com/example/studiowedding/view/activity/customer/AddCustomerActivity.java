package com.example.studiowedding.view.activity.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtArrder;
    private Button btnAddCustomer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        initView();
        onClick();

    }
    private void insertCustomer() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtArrder.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(AddCustomerActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() != 10) {
            Toast.makeText(AddCustomerActivity.this, "Số điện thoại phải có 10 chữ số", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Customer> call = apiService.insertCustomer(name, phone, address);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {
                Toast.makeText(AddCustomerActivity.this, "successfull", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<Customer> call, @NonNull Throwable t) {
                Log.e("Tag" , t.getMessage());
                Toast.makeText(AddCustomerActivity.this, "Failed to insert", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClick() {
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCustomer();
            }
        });
    }
    private void initView() {
        edtName = (EditText)findViewById(R.id.edtName);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtArrder = (EditText)findViewById(R.id.edtArrder);
        btnAddCustomer = (Button) findViewById(R.id.btnAddCustomer);
    }

}