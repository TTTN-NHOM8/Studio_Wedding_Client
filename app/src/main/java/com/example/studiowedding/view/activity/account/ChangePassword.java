package com.example.studiowedding.view.activity.account;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studiowedding.R;

public class ChangePassword extends AppCompatActivity {

    ImageView imgback;
    Button btnThem;
    EditText edMkcu, edMkmoi, edMkmoi1;

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
                if(cu.isEmpty() || moi.isEmpty() || moi1.isEmpty()){
                    Toast.makeText(ChangePassword.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                if(!moi.equals(moi1)){
                    Toast.makeText(ChangePassword.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}