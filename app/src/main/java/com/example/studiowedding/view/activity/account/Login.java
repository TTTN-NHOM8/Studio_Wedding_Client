package com.example.studiowedding.view.activity.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studiowedding.R;
import com.example.studiowedding.model.Account;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.MainActivity;
import com.example.studiowedding.view.activity.task.SeeTaskActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ApiService apiService;
    private EditText edtIdNhanVien, edtMatKhau;
    private Button btnLogin;
    private ImageView btnTogglePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = ApiClient.getApiService();

        edtIdNhanVien = findViewById(R.id.edTenDn);
        edtMatKhau = findViewById(R.id.edPasswork);
        btnLogin = findViewById(R.id.btnLogin);
        btnTogglePassword = findViewById(R.id.ivTogglePassword);

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

        btnTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }
        });
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        // Toggle password visibility in the EditText
        edtMatKhau.setTransformationMethod(
                isPasswordVisible ? HideReturnsTransformationMethod.getInstance() :
                        PasswordTransformationMethod.getInstance());

        // Toggle the eye icon
        btnTogglePassword.setImageResource(
                isPasswordVisible ? R.drawable.baseline_visibility_24 : R.drawable.baseline_visibility_off_24);
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
                            String vaitro = userAccount.getVaiTro();
                            String idnhanvien = userAccount.getIdNhanVien();
                            String pass = edtMatKhau.getText().toString().trim();
                            String hoten = userAccount.getHoVaTen();
                            String sdt = userAccount.getDienThoai();
                            String diachi = userAccount.getDiaChi();
                            String ngaysinh = userAccount.getNgaySinh();
                            String gioitinhs = userAccount.getGioiTinh();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            try {
                                Date date = inputFormat.parse(ngaysinh);
                                    String formattedNgaySinh = outputFormat.format(date);
                                    SharedPreferences preferences = getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("IdNhanvien", idnhanvien);
                                    editor.putString("Hoten", hoten);
                                    editor.putString("Vaitro", vaitro);
                                    editor.putString("Matkhau", pass);
                                    editor.putString("SDT", sdt);
                                    editor.putString("Diachi", diachi);
                                    editor.putString("ngay", formattedNgaySinh);
                                    editor.putString("gioitinh", gioitinhs);
                                    editor.apply();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("err: ",e.getMessage());
                            }

                            if (vaitro.equals("Quan li")) {
                                Toast.makeText(Login.this, "Đăng nhập thành công admin ",Toast.LENGTH_SHORT).show();
                                navigateToNextScreenAdmin();

                            } else {
                                Toast.makeText(Login.this, "Đăng nhập thành công nhân viên ", Toast.LENGTH_SHORT).show();
                                navigateToNextScreenEmployee();
                            }
                        } else {
                            Toast.makeText(Login.this, "Không có thông tin tài khoản", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Tên đăng nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("API Error", "Call failed: " + t.getMessage());
                Toast.makeText(Login.this, "Đăng nhập không thành công: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToNextScreenAdmin() {
        // Thực hiện chuyển màn hình admin
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToNextScreenEmployee() {
        // Thực hiện chuyển màn hình nhân viên
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

}
