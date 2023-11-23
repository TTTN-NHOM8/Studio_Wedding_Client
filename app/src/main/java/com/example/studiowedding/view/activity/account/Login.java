package com.example.studiowedding.view.activity.account;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Account;
import com.example.studiowedding.network.AccountResponse;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ApiService apiService;
    private EditText edtIdNhanVien, edtMatKhau;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = ApiClient.getApiService();

        edtIdNhanVien = findViewById(R.id.edTenDn);
        edtMatKhau = findViewById(R.id.edPasswork);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idNhanVien = edtIdNhanVien.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                if (idNhanVien.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(idNhanVien, matKhau);
            }
        });
    }

    private void login(String idNhanVien, String matKhau) {
        Call<AccountResponse> call = apiService.loginAccount(idNhanVien, matKhau);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse accountResponse = response.body();
                    String status = accountResponse.getStatus();
                    if ("success".equals(status)) {
                        Account userAccount = accountResponse.getUserAccount();
                        if (userAccount != null) {
                            String vaitro = userAccount.getVaitro();
                            if(vaitro.equals("1")){
                                Toast.makeText(Login.this, "Đăng nhập thành công admin " , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Login.this, "Đăng nhập thành công nhân viên " , Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Không có thông tin tài khoản", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("API Error", "Call failed: " + t.getMessage());
                Toast.makeText(Login.this, "Đăng nhập không thành công: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
