package com.example.studiowedding.view.activity.contract;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.studiowedding.R;

public class PickClientActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private TextView tvCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_client);
        imgBack=findViewById(R.id.imgBackFromPickClient);
        tvCreate=findViewById(R.id.tvCreateClient);

        imgBack.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBackFromPickClient:
                finish();
                break;
            case R.id.tvCreateClient:
                break;
        }
    }
}