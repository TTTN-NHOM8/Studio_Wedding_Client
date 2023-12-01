package com.example.studiowedding.view.activity.customer;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.studiowedding.R;
import com.example.studiowedding.databinding.ActivityUpdateCustomerBinding;
import com.example.studiowedding.model.Customer;
import com.example.studiowedding.model.Product;
import com.example.studiowedding.model.Task;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCustomerActivity extends AppCompatActivity {
    ActivityUpdateCustomerBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityUpdateCustomerBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        Intent intent = getIntent();
        Customer customer = (Customer) intent.getSerializableExtra("customer");
        if(customer!=null){
            fillData(customer);
        }
        viewBinding.btnSave.setOnClickListener(v->{
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<Void> call = apiService.updateCustomer(customer.getId(),new Customer(
                  viewBinding.edNameCustomer.getText().toString(),
                    viewBinding.edPhoneCustomer.getText().toString(),
                    viewBinding.edAddressCustomer.getText().toString()
            ));
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
    }
    void fillData(Customer customer){
        viewBinding.edNameCustomer.setText(customer.getName());
        viewBinding.edAddressCustomer.setText(customer.getAddress());
        viewBinding.edPhoneCustomer.setText(customer.getPhone());
    }










}