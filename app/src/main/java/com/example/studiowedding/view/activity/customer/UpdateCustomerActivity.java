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
        viewBinding.btnSave.setOnClickListener(v -> {
            if (isValidData()) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<Void> call = apiService.updateCustomer(customer.getId(), new Customer(
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
            }
        });

    }
    void fillData(Customer customer){
        viewBinding.edNameCustomer.setText(customer.getName());
        viewBinding.edAddressCustomer.setText(customer.getAddress());
        viewBinding.edPhoneCustomer.setText(customer.getPhone());
    }
    private boolean isValidData() {
        String name = viewBinding.edNameCustomer.getText().toString().trim();
        String phone = viewBinding.edPhoneCustomer.getText().toString().trim();

        if (name.isEmpty() || !isNameValid(name)) {
            Toast.makeText(this, "Tên không hợp lệ. Xin vui lòng nhập lại", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty() || !isPhoneValid(phone)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ. Xin vui lòng nhập lại.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isNameValid(String name) {
        // Add your name validation logic here
        return name.matches("[a-zA-Z ]+");
    }

    private boolean isPhoneValid(String phone) {
        // Add your phone number validation logic here
        // Check if it starts with "0", is a number, and has a length of at most 10
        return phone.matches("^0[0-9]{9}$");
    }

}










