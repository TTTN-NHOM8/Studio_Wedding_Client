package com.example.studiowedding.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studiowedding.R;
import com.example.studiowedding.view.activity.account.ChangePassword;
import com.example.studiowedding.view.activity.account.EditInformation;


public class SettingFragment extends Fragment {
    Button btntaikhoan, btndoimatkhau;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btndoimatkhau = view.findViewById(R.id.btnDoimatkhau);
        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        btntaikhoan = view.findViewById(R.id.btnTaikhoan);
        btntaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditInformation.class);
                startActivity(intent);
            }
        });
        return view;
    }
}