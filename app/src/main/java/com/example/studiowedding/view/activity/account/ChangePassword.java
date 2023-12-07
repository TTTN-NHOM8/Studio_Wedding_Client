package com.example.studiowedding.view.activity.account;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studiowedding.R;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;
import javax.security.auth.callback.Callback;



public class ChangePassword extends AppCompatActivity {
    private ApiService apiService;

    ImageView imgback, imghien, imghien1, imghien2;
    Button btnThem;
    EditText edMkcu, edMkmoi, edMkmoi1;
    private boolean isPasswordVisible = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        imgback = findViewById(R.id.imgBack);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        apiService = ApiClient.getApiService();

        edMkcu = findViewById(R.id.edPasswork);
        edMkmoi = findViewById(R.id.edPassnew);
        edMkmoi1 = findViewById(R.id.edPassnew1);
        btnThem = findViewById(R.id.btnCapnhat);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cu = edMkcu.getText().toString();
                String moi = edMkmoi.getText().toString();
                String moi1 = edMkmoi1.getText().toString();

                if (cu.isEmpty() || moi.isEmpty() || moi1.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("LuuIdNhanvien", MODE_PRIVATE);
                    String nhanvien = preferences.getString("IdNhanvien", "");
                    String mk = preferences.getString("Matkhau", "");

                    if (cu.equals(mk)) {
                        if (moi.equals(moi1)) {
                            // Update the stored password with the new password
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Matkhau", moi);
                            editor.apply();

                            // Perform the password change on the server
                            changePassword(nhanvien, cu, moi);

                            Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePassword.this, "Mật khẩu mới không giống nhau", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChangePassword.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        imghien = findViewById(R.id.imgXem);
        imghien.setOnClickListener(PasswordUtils.createEyeIconClickListener(edMkcu, imghien));

        imghien1 = findViewById(R.id.ImgXem1);
        imghien1.setOnClickListener(PasswordUtils.createEyeIconClickListener(edMkmoi, imghien1));

        imghien2 = findViewById(R.id.imgXem2);
        imghien2.setOnClickListener(PasswordUtils.createEyeIconClickListener(edMkmoi1, imghien2));

    }

        private void changePassword(String idNhanVien, String matKhauCu, String matKhauMoi) {
        Call<AccountResponse> call = apiService.changePassword(idNhanVien, matKhauCu, matKhauMoi);
        call.enqueue(new retrofit2.Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse result = response.body();
                    if ("success".equals(result.getStatus())) {
                        String message = result.getMessage();
                        Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String errorMessage = result.getMessage();
                        Toast.makeText(ChangePassword.this, "Đổi mật khẩu thất bại: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePassword.this, "Lỗi từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static class PasswordUtils {
        public static void togglePasswordVisibility(EditText editText, ImageView eyeImageView) {
            boolean isPasswordVisible = !(editText.getTransformationMethod() instanceof PasswordTransformationMethod);

            // Toggle password visibility in the EditText
            editText.setTransformationMethod(
                    isPasswordVisible ? PasswordTransformationMethod.getInstance() :
                            HideReturnsTransformationMethod.getInstance());

            // Toggle the eye icon
            eyeImageView.setImageResource(
                    isPasswordVisible ? R.drawable.baseline_visibility_24 : R.drawable.baseline_visibility_off_24);
        }

        public static View.OnClickListener createEyeIconClickListener(final EditText editText, final ImageView eyeImageView) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    togglePasswordVisibility(editText, eyeImageView);
                }
            };
        }
    }

}