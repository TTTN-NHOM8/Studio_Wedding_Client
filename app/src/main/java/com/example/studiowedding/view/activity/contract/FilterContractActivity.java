package com.example.studiowedding.view.activity.contract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studiowedding.R;

public class FilterContractActivity extends Dialog {

    private TextView tvCancel;
    private Context context;

    public FilterContractActivity(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_filter_contract);
        tvCancel=findViewById(R.id.tvCancelFilterContract);


        tvCancel.setOnClickListener(view -> {
            dismiss();
        });

    }
}